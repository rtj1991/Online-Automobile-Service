package com.online.automobile.service.priceList;

import com.online.automobile.model.PriceList;
import com.online.automobile.model.ServiceType;

import java.util.List;

public interface PriceListService {

    PriceList getPriceById(PriceList id);

    PriceList getPriceByServiceType(ServiceType type);

    PriceList getPriceByServiceTypeType(int type);
}
