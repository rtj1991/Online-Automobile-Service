package com.online.automobile.service.rating;

import com.online.automobile.model.Company;
import com.online.automobile.model.CompanyRating;
import com.online.automobile.model.User;
import com.online.automobile.repository.CompanyRatingRepository;
import com.online.automobile.repository.CompanyRepository;
import com.online.automobile.util.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CompanyRatingServiceImpl implements CompanyRatingService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CompanyRatingRepository ratingRepository;

    @Override
    public CompanyRating saveCompanyRating(Map<String, String> map, User user) {
        String company = map.get("company");
        int rate = Integer.parseInt(map.get("rate"));

        Company company1 = companyRepository.findById(Integer.parseInt(company));

        CompanyRating rating = new CompanyRating();
        rating.setCompany(company1);
        rating.setUser(user);
        rating.setRating(rate);
        rating.setStatus(Const.STATUS_ACTIVE);

        return ratingRepository.save(rating);
    }

    @Override
    public List<CompanyRating> getAllByCompanyAndUser(Company company, User user) {
        return ratingRepository.findAllByBookedCompanyAndcAndCreatedBy(company,user);
    }
}
