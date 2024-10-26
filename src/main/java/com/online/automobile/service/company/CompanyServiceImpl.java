package com.online.automobile.service.company;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.online.automobile.model.*;
import com.online.automobile.repository.CompanyRepository;
import com.online.automobile.repository.PriceListRepository;
import com.online.automobile.repository.ServiceTypeRepository;
import com.online.automobile.util.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ServiceTypeRepository typeRepository;

    @Autowired
    private PriceListRepository priceListRepository;

    @Override
    public List<Company> findCompanyByLocation(Location location) {

        return companyRepository.findAllByCompanyLocation(location);
    }

    @Override
    public Company findByCompanyId(int id) {
        return companyRepository.findById(id);
    }

    @Override
    public Company findByUser(User user) {
        return companyRepository.findByCompanyUser(user);
    }

    @Override
    public Company saveServiceType(Company company, Map<String, String> map) {

        String carts = map.get("carts");
        JsonParser jsonParser = new JsonParser();

        JsonArray parse = (JsonArray) jsonParser.parse(carts);
        ArrayList<ServiceType> serviceTypes = new ArrayList<>();

        for (JsonElement cart : parse) {

            int id = cart.getAsJsonObject().get("id").getAsInt();
            double price = cart.getAsJsonObject().get("price").getAsDouble();
            List<ServiceType> allById = typeRepository.findAllById(id);
            int size = priceListRepository.findAllByIdIsNotNull().size();
            serviceTypes.addAll(allById);

            PriceList priceList = new PriceList();
            priceList.setAmount(price);
            priceList.setPrl("PRL" + (size + 1));
            priceList.setCompanys(company);
            priceList.setServiceType(typeRepository.findById(id));
            priceList.setStatus(Const.STATUS_ACTIVE);

            priceListRepository.save(priceList);
        }
        company.setServiceTypes(serviceTypes);
        companyRepository.save(company);
        return company;
    }

    @Override
    public Company saveServiceTypes(Company company, Map<String, String> map) {

        int id = Integer.parseInt(map.get("id"));
        double price = Double.parseDouble(map.get("price"));

        ServiceType allById = typeRepository.findById(id);
        int size = priceListRepository.findAllByIdIsNotNull().size();

        PriceList priceList = new PriceList();
        priceList.setAmount(price);
        priceList.setPrl("PRL" + (size + 1));
        priceList.setCompanys(company);
        priceList.setServiceType(typeRepository.findById(id));
        priceList.setStatus(Const.STATUS_ACTIVE);

        company.getServiceTypes().add(allById);
        priceListRepository.save(priceList);
        companyRepository.save(company);
        return company;
    }

    @Override
    public Company saveNewServiceTypes(Company company, Map<String, String> map) {

        String type = map.get("type");
        String description = map.get("description");
        double price = Double.parseDouble(map.get("price"));

        int size = priceListRepository.findAllByIdIsNotNull().size();

        if (map != null) {
            ServiceType serviceType = new ServiceType();
            serviceType.setServiceType(type);
            serviceType.setDescription(description);
            serviceType.setType(Const.SECONDARY_TYPE);
            serviceType.setStatus(Const.STATUS_ACTIVE);

            typeRepository.save(serviceType);
            ServiceType byId = typeRepository.findById(serviceType.getId());
            PriceList priceList = new PriceList();
            priceList.setAmount(price);
            priceList.setPrl("PRL" + (size + 1));
            priceList.setCompanys(company);
            priceList.setServiceType(byId);
            priceList.setStatus(Const.STATUS_ACTIVE);

            priceListRepository.save(priceList);

            company.getServiceTypes().add(byId);
            companyRepository.save(company);

        }
        return company;
    }

    @Override
    public List<Company> findAllByCompanyLocationAndServiceTypes(Location location, int carrierService) {
        ServiceType status = typeRepository.findByType(carrierService);
        return companyRepository.findAllByCompanyLocationAndServiceTypes(location,status);
    }
}
