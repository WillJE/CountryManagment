package com.example.countries;

import com.example.countries.entities.Country;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.junit.Test;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.util.Iterator;

/**
 * Created by wenli on 2019/7/10.
 */
public class MainTest {


    public static void testOpenCSV() throws IOException {
//        File file = new File(getClass().getResource("country.csv").getFile());

        File file = ResourceUtils.getFile("classpath:country.csv");
        Reader reader = Files.newBufferedReader(file.toPath());
        ColumnPositionMappingStrategy strategy = new ColumnPositionMappingStrategy();
        strategy.setType(Country.class);
        String[] memberFieldsToBindTo = {"name", "capital"};
        strategy.setColumnMapping(memberFieldsToBindTo);

        CsvToBean<Country> csvToBean = new CsvToBeanBuilder(reader)
                .withMappingStrategy(strategy)
                .withSkipLines(1)
                .withIgnoreLeadingWhiteSpace(true)
                .build();

        Iterator<Country> CountryIterator = csvToBean.iterator();

        while (CountryIterator.hasNext()){
            Country country = CountryIterator.next();
            System.out.println(country.toString());
        }

    }

    public static void main(String[] args) throws IOException {
        testOpenCSV();
    }
}
