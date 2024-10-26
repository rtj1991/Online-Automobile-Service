package com.online.automobile.repository;

import com.online.automobile.model.Booking;
import com.online.automobile.model.BookingDetails;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingDetailRepository extends PagingAndSortingRepository<BookingDetails,Integer> {

    BookingDetails findById(int id);

    List<BookingDetails>findAllByBooking(Booking booking);

    List<BookingDetails>findAllByBookingAndDeleted(Booking booking,int deleted);
}
