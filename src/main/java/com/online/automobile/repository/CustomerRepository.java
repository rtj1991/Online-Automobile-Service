package com.online.automobile.repository;

import com.online.automobile.model.Customer;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends PagingAndSortingRepository<Customer,String> {
    Customer findById(int id);
}
