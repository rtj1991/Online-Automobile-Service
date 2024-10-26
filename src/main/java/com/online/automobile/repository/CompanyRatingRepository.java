package com.online.automobile.repository;

import com.online.automobile.model.Company;
import com.online.automobile.model.CompanyRating;
import com.online.automobile.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRatingRepository extends PagingAndSortingRepository<CompanyRating,Integer> {

    @Query("SELECT id FROM CompanyRating WHERE company=:company AND ratingCompanyUser IN(:user)")
    public List<CompanyRating> findAllByBookedCompanyAndcAndCreatedBy(@Param("company") Company company, @Param("user") User user);
}
