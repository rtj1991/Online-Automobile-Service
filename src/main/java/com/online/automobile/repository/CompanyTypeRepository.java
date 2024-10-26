package com.online.automobile.repository;

import com.online.automobile.model.CompanyType;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyTypeRepository extends PagingAndSortingRepository<CompanyType, Integer> {
}
