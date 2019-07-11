package com.example.countries;

import com.example.countries.entities.CountryDTO;
import com.example.countries.repositories.CountryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests implements CommandLineRunner {

	@Autowired
	private CountryRepository countryRepository;
	@Test
	public void contextLoads() {
	}

	@Override
	public void run(String... strings) throws Exception {
//		countryRepository.save(new CountryDTO("Earth","World"));
//		countryRepository.save(new CountryDTO("China","Peaking"));
//		countryRepository.save(new CountryDTO("Germany","Berlin"));
//		countryRepository.save(new CountryDTO("USA","Washington DC"));
//		countryRepository.save(new CountryDTO("Russia","Moscow"));
//		countryRepository.save(new CountryDTO("Namibia","Windhoek"));
//		countryRepository.save(new CountryDTO("India","New Delhi"));
//		countryRepository.save(new CountryDTO("North Korea","Pyongyang"));
//		countryRepository.save(new CountryDTO("Kenya","Nairobi"));
//		countryRepository.save(new CountryDTO("Canada","Ottawa"));
//		countryRepository.save(new CountryDTO("Jamaica","Kingston"));
//		countryRepository.save(new CountryDTO("Brazil","Brasilia"));
//		countryRepository.save(new CountryDTO("Egypt","Cairo"));
//		countryRepository.save(new CountryDTO("Nigeria","Abuja"));
	}
}
