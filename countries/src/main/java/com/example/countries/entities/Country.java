package com.example.countries.entities;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by wenli on 2019/7/10.
 * This representing a csv modal
 */
public class Country {
    private String id;
    private String name;
    private String capital;

    public Country() {
    }

    public Country(String name, String capital) {
        this.name = name;
        this.capital = capital;
    }

    public Country(String id, String name, String capital) {
        this.id = id;
        this.name = name;
        this.capital = capital;
    }

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

    @Override
    public String toString() {
        return new ToStringBuilder(this).
                append("name", name).
                append("capital", capital).
                toString();
    }
}
