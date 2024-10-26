package com.online.automobile.repository;

import com.online.automobile.model.Company;
import com.online.automobile.model.Location;
import com.online.automobile.model.ServiceType;
import com.online.automobile.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CompanyRepository extends PagingAndSortingRepository<Company, Integer> {
    List<Company> findAllByCompanyLocation(Location location);

    Company findById(int id);

    Company findByCompanyUser(User user);

    List<Company> findAllByCompanyLocationAndServiceTypes(Location location, ServiceType type);

//    @Query("SELECT COALESCE((SELECT AVG(rating)\n" +
//            "FROM CompanyRating AS r WHERE r.company=c.id\n" +
//            "GROUP BY ratingCompanyUser),0) AS rating,c.companyName AS name,l.locationName AS location,c.startTime AS start,\n" +
//            "c.endTime AS endtime,t.type AS type\n" +
//            "FROM Company AS c \n" +
//            "JOIN Location AS l ON c.companyLocation=l.id\n" +
//            "JOIN CompanyType AS t ON c.companyType=t.id\n" +
//            "WHERE c.companyLocation=:location")
//    public Double findAllByCompanyLocationAndServiceTypesAndRating(@Param("location") Location location);
}
