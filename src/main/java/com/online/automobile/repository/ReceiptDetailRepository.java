package com.online.automobile.repository;

import com.online.automobile.model.Booking;
import com.online.automobile.model.ReceiptDetail;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReceiptDetailRepository extends PagingAndSortingRepository<ReceiptDetail,Integer> {

    List<ReceiptDetail>findAllByBooking(Booking booking);

}
