package com.example.countries.controller;

import com.example.countries.entities.Country;
import com.example.countries.entities.CountryDTO;
import com.example.countries.repositories.CountryRepository;
import com.example.countries.services.CountryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVWriter;
import com.opencsv.bean.*;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by wenli on 2019/7/5.
 */
@Controller
public class CountryController {
    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private CountryService countryService;

    public CountryController(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @GetMapping("/")
    public String index(Model model,@RequestParam(defaultValue = "1") int page){
        PageRequest pageRequest = PageRequest.of(page - 1,4);
        Page<CountryDTO> countryPage = countryService.getPaginatedCountry(pageRequest);
        int totalPages = countryPage.getTotalPages();
        if(totalPages > 0){
            List<Integer> pageNumbers = IntStream.rangeClosed(1,totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("CountryList",countryPage.getContent());
        model.addAttribute("ActiveCountryList",true);
        model.addAttribute("CurrentPage",page);
        return "index";
    }

    @PostMapping("/save")
    public String save(CountryDTO county){
        countryRepository.save(county);
        return "redirect:/";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam String id){
        countryRepository.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/findOne")
    @ResponseBody
    public CountryDTO findOne(@RequestParam String id){
        return  countryRepository.findById(id).get();
//        return countryRepository.getOne(id);
    }

    @PostMapping(value = "/upload")
    public String uploadCsvFile(@RequestParam("file") MultipartFile file,
                                RedirectAttributes redirectAttributes){
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:uploadStatus";
        }
        //convert MultipartFile to file
        File convFile = new File(file.getOriginalFilename());
        Reader reader = null;
        try {
            convFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
            fos.close();
            reader = Files.newBufferedReader(convFile.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        ColumnPositionMappingStrategy strategy = new ColumnPositionMappingStrategy();
        strategy.setType(CountryDTO.class);
        String[] memberFieldsToBindTo = {"name", "capital"};
        strategy.setColumnMapping(memberFieldsToBindTo);

        CsvToBean<CountryDTO> csvToBean = new CsvToBeanBuilder(reader)
                .withMappingStrategy(strategy)
                .withSkipLines(1)
                .withIgnoreLeadingWhiteSpace(true)
                .build();

        Iterator<CountryDTO> CountryIterator = csvToBean.iterator();

        List<CountryDTO> listCountry = new ArrayList<>();
        while (CountryIterator.hasNext()){
            CountryDTO countryDTO = CountryIterator.next();
            listCountry.add(countryDTO);
        }
        countryRepository.saveAll(listCountry);
        return "redirect:/";
    }


    @GetMapping("/export")
    public ResponseEntity<Resource> exportDataToCSV(HttpServletResponse response) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        /**
         * 生成json文件
         */
/*        List<CountryDTO> countryDTOList = (List<CountryDTO>) countryRepository.findAll();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(countryDTOList);
        byte[] isr = json.getBytes();
        String fileName = "country.json";
        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.setContentLength(isr.length);
        respHeaders.setContentType(new MediaType("text", "json"));
        respHeaders.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        respHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
        return new ResponseEntity<byte[]>(isr, respHeaders, HttpStatus.OK);*/


        //生成csv文件(任意文件)
        List<CountryDTO> countryDTOList = (List<CountryDTO>) countryRepository.findAll();
        String filePath = "country.csv";
        File file = new File(filePath);
        try(
                Writer writer = Files.newBufferedWriter(Paths.get(file.toString()));
        ){
            ColumnPositionMappingStrategy mappingStrategy = new ColumnPositionMappingStrategy();
            mappingStrategy.setType(CountryDTO.class);
            String[] memberFieldsToBindTo = {"name", "capital"};
            mappingStrategy.setColumnMapping(memberFieldsToBindTo);
            StatefulBeanToCsv<CountryDTO> beanToCsv = new StatefulBeanToCsvBuilder(writer)
                    .withMappingStrategy(mappingStrategy)
                    .build();
            beanToCsv.write(countryDTOList);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filePath);

        Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }
}
