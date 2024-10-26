package com.online.automobile.service.serviceType;

import com.online.automobile.model.Company;
import com.online.automobile.model.ServiceType;
import com.online.automobile.model.User;
import com.online.automobile.repository.CompanyRepository;
import com.online.automobile.repository.ServiceTypeRepository;
import com.online.automobile.util.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Service
public class ServiceTypeServiceImpl implements ServiceTypeService{

    @Autowired
    private ServiceTypeRepository typeRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public List<ServiceType> getAllServiceTypes(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        Company companyUser = companyRepository.findByCompanyUser(user);

        return typeRepository.findAllByCompaniesIsNotContaining(companyUser);
    }

    @Override
    public List<ServiceType> getCompanyServiceType(Company company) {
        return typeRepository.findAllByCompanies(company);
    }


    @Override
    public List<ServiceType> getServiceTypeByStatus(Map<String,String> company, int type1) {
        int id = Integer.parseInt(company.get("company"));
        Company company1 = new Company();
        company1.setId(id);
        return typeRepository.findAllByCompaniesAndType(company1,type1);
    }

//    @Override
//    public List<ServiceType> getAllServiceType(Company company) {
//
//        Company companyUser = companyRepository.findByCompanyUser();
//
//        return typeRepository.findAllByCompanies(companyUser);
//    }
}
