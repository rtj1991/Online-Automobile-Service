package com.online.automobile.repository;

import com.online.automobile.model.PriceList;
import com.online.automobile.model.ServiceType;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriceListRepository extends PagingAndSortingRepository<PriceList,Integer> {
    List<PriceList> findAllByIdIsNotNull();

    PriceList findAllById(PriceList id);

    PriceList findAllByServiceType(ServiceType type);

    PriceList findByServiceTypeType(int type);
}
