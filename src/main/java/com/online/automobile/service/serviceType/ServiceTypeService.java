package com.online.automobile.service.serviceType;

import com.online.automobile.model.Company;
import com.online.automobile.model.ServiceType;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface ServiceTypeService {
    List<ServiceType> getAllServiceTypes(HttpServletRequest request);

    List<ServiceType> getCompanyServiceType(Company company);

    List<ServiceType>getServiceTypeByStatus(Map<String,String> company, int type1);

//    List<ServiceType> getAllServiceType(Company company);
}
