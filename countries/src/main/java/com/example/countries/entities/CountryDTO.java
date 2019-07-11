package com.example.countries.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * This representing a database table
 * Created by wenli on 2019/7/5.
 */
@Entity
public class CountryDTO {
    public CountryDTO() {
    }

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;

    private String name;
    private String capital;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public CountryDTO(String name, String capital) {
        this.name = name;
        this.capital = capital;
    }

    public String[] toStringArray(){
        return new String[]{this.getName(),this.getCapital()};
    }
}
