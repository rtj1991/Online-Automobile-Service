package com.online.automobile.service.receipt;

import com.online.automobile.model.Booking;
import com.online.automobile.model.ReceiptDetail;
import com.online.automobile.repository.ReceiptDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReceiptDetailServiceImpl implements ReceiptDetailService {

    @Autowired
    private ReceiptDetailRepository receiptDetailRepository;

    @Override
    public List<ReceiptDetail> findAllByBooking(Booking booking) {
        return receiptDetailRepository.findAllByBooking(booking);
    }
}
