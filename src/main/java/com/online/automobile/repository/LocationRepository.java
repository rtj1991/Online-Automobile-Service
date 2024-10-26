package com.online.automobile.repository;

import com.online.automobile.model.Location;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends PagingAndSortingRepository<Location, Integer> {
    public List<Location> findAllByStatus(int status);

    public Location findByIdAndStatus(int id,int status);
}
