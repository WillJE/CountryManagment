package com.example.countries.repositories;

import com.example.countries.entities.CountryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by wenli on 2019/7/5.
 */
public interface CountryRepository extends CrudRepository<CountryDTO,String> {
    Page<CountryDTO> findAll(Pageable pageable);
}
