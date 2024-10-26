package com.online.automobile.repository;

import com.online.automobile.model.Company;
import com.online.automobile.model.ServiceType;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceTypeRepository extends PagingAndSortingRepository<ServiceType,Integer> {
    List<ServiceType>findAllByStatus(int status);

    List<ServiceType> findAllById(int id);

    ServiceType findById(int id);

    List<ServiceType>findAllByCompanies(Company company);

    List<ServiceType>findAllByCompaniesIsNotContaining(Company company);

    List<ServiceType>findAllByCompaniesAndTypeNot(Company company,int type);

    List<ServiceType>findAllByCompaniesAndType(Company company,int type1);

    ServiceType findByType(int status);
}
