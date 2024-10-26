package com.online.automobile.service.priceList;

import com.online.automobile.model.PriceList;
import com.online.automobile.model.ServiceType;
import com.online.automobile.repository.PriceListRepository;
import com.online.automobile.util.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriceListServiceImpl implements PriceListService {

    @Autowired
    private PriceListRepository priceListRepository;

    @Override
    public PriceList getPriceById(PriceList id) {
        return priceListRepository.findAllById(id);
    }

    @Override
    public PriceList getPriceByServiceType(ServiceType type) {
        return priceListRepository.findAllByServiceType(type);
    }

    @Override
    public PriceList getPriceByServiceTypeType(int type) {
        return priceListRepository.findByServiceTypeType(type);
    }
}
