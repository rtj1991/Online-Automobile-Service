package com.online.automobile.service.company;

import com.online.automobile.model.Company;
import com.online.automobile.model.Location;
import com.online.automobile.model.User;

import java.util.List;
import java.util.Map;

public interface CompanyService {
    List<Company> findCompanyByLocation(Location location);

    Company findByCompanyId(int id);

    Company findByUser(User user);

    Company saveServiceType(Company company,Map<String, String> map);

    Company saveServiceTypes(Company company,Map<String,String>map);

    Company saveNewServiceTypes(Company company,Map<String,String>map);

    List<Company> findAllByCompanyLocationAndServiceTypes(Location location, int carrierService);
}
