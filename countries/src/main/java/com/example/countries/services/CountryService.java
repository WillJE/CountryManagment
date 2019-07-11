package com.example.countries.services;

import com.example.countries.entities.CountryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by wenli on 2019/7/9.
 */
public interface CountryService {
    public Page<CountryDTO> getPaginatedCountry(Pageable pageable);
}
