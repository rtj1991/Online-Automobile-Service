package com.online.automobile.repository;

import com.online.automobile.model.VehicleType;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleTypeRepository extends PagingAndSortingRepository<VehicleType,Integer> {

    List<VehicleType>findAllByStatus(int status);
}
