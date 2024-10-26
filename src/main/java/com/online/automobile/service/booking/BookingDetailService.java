package com.online.automobile.service.booking;

import com.online.automobile.model.Booking;
import com.online.automobile.model.BookingDetails;

import java.util.List;

public interface BookingDetailService {
    List<BookingDetails> getDetailByBooking(Booking booking);
}
