package com.online.automobile.service.receipt;

import com.online.automobile.model.Booking;
import com.online.automobile.model.ReceiptDetail;

import java.util.List;

public interface ReceiptDetailService {

    List<ReceiptDetail>findAllByBooking(Booking booking);
}
