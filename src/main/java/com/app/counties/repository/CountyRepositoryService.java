package com.app.counties.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.counties.model.County;

public interface CountyRepositoryService extends JpaRepository<County, String>{

	List<County> findByNameStartingWithIgnoreCase(String queryString);

	List<County> findByStateStartingWithIgnoreCase(String queryString);

	List<County> findByStateAndName(String state, String name);

}
