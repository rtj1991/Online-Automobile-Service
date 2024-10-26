package com.online.automobile.service.rating;

import com.online.automobile.model.Company;
import com.online.automobile.model.CompanyRating;
import com.online.automobile.model.User;

import java.util.List;
import java.util.Map;

public interface CompanyRatingService {

    CompanyRating saveCompanyRating(Map<String, String> map,User user);

    List<CompanyRating> getAllByCompanyAndUser(Company company,User user);
}
