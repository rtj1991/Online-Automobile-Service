package com.online.automobile.service.booking;

import com.online.automobile.dto.BookingDetail;
import com.online.automobile.model.Booking;
import com.online.automobile.model.BookingDetails;
import com.online.automobile.repository.BookingDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingDetailImpl implements BookingDetailService {

    @Autowired
    private BookingDetailRepository detailRepository;

    @Override
    public List<BookingDetails> getDetailByBooking(Booking booking) {
        List<BookingDetails> detailsList = detailRepository.findAllByBooking(booking);
        return detailsList;
    }
}
