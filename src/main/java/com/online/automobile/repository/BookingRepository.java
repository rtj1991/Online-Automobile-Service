package com.online.automobile.repository;

import com.online.automobile.model.Booking;
import com.online.automobile.model.Company;
import com.online.automobile.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends PagingAndSortingRepository<Booking, Integer> {

    List<Booking> findAllByCreatedByAndBookingTypeAndProcessStatusNot(User user,int type,int status);

    List<Booking> findAllByBookedCompanyAndBookingTypeAndProcessStatus(Company company,int type,int status);

    List<Booking>findAllByBookedCompanyAndAppointedDateGreaterThanEqualAndAppointedDateLessThanEqualAndCancelStatus(Company company, Date fdate, Date tdate,int cancel);

    List<Booking>findAll();

    Booking findOne(int id);

    List<Booking>findAllByBookedCompanyAndBookingType(Company company,int type);

    List<Booking> findAllByBookedCompanyAndProcessStatus(Company company,int status);

    List<Booking>findAllByCreatedBy(User user);

    @Query("SELECT id FROM Booking WHERE bookedCompany=:company AND createdBy=:user AND paidStatus IN(1)")
    public List<Booking> findAllByBookedCompanyAndcAndCreatedBy(@Param("company") Company company, @Param("user") User user);

}
