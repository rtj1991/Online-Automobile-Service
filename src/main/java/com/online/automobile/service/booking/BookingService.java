package com.online.automobile.service.booking;

import com.online.automobile.dto.BookingDetail;
import com.online.automobile.model.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface BookingService {

    Booking CreateBooking(BookingDetail detail, Map<String,String>map,HttpServletRequest request);

    List<Booking> getAllBookingBy(User user,int type,int status);

    List<Booking> getAllBookingByDate(Company company, Date currentFDate, Date currentTDate);

    List<Booking>getAllBookingByCompany(Company company,int type,int status);

    List<Booking>getAllBooking();

    Booking getBookingById(int id);

    Booking updateBooking(BookingDetail detail,Map<String,String>map);

    Booking CreateCarrierServiceBooking(BookingDetail detail,HttpServletRequest request);

    Booking CreateFieldOfficerBooking(BookingDetail detail,HttpServletRequest request);

    List<Booking> getAllBookingBy(Company company,int status);

    List<Booking>getAllByUser(User user);

    List<Booking> getAllByCompanyAndUser(Company company,User user);


}
