package com.example.countries.services.impl;

import com.example.countries.dao.CountryDao;
import com.example.countries.entities.CountryDTO;
import com.example.countries.services.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Created by wenli on 2019/7/9.
 */
@Service
public class CountryServiceImpl implements CountryService {
    @Autowired
    CountryDao countryDao;

    @Override
    public Page<CountryDTO> getPaginatedCountry(Pageable pageable) {
        return countryDao.findAll(pageable);
    }
}
