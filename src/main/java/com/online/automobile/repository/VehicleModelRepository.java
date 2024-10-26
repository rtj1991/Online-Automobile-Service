package com.online.automobile.repository;

import com.online.automobile.model.VehicleModel;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleModelRepository extends PagingAndSortingRepository<VehicleModel,Integer> {

    List<VehicleModel> findAllByStatus(int status);
}
