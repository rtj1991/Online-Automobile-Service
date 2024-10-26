package com.online.automobile.repository;

import com.online.automobile.model.Supplier;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends PagingAndSortingRepository<Supplier,Integer> {
}
