package com.example.countries.dao;

import com.example.countries.entities.CountryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by wenli on 2019/7/9.
 */
public interface  CountryDao extends CrudRepository<CountryDTO,String> {
    Page<CountryDTO> findAll(Pageable pageable);
}
