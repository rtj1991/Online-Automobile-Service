package com.online.automobile.service.receipt;

import com.online.automobile.dto.BookingDetail;
import com.online.automobile.model.*;
import com.online.automobile.repository.*;
import com.online.automobile.util.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ReceiptServiceImpl implements ReceiptService{

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private BookingDetailRepository detailRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ReceiptRepository receiptRepository;

    @Autowired
    private ReceiptDetailRepository receiptDetailRepository;

    @Override
    public ReceiptHeader CreateReceipt(int id) {
        Booking booking = bookingRepository.findOne(id);
        booking.setPaidAmount(booking.getTotalAmount());
        booking.setPaidStatus(Const.FULL_PAID);
        booking.setProcessStatus(Const.PROCESS_COMPLETE);
        bookingRepository.save(booking);
        List<BookingDetails> detailsList = detailRepository.findAllByBooking(booking);

        Customer customer = customerRepository.findById(booking.getCreatedBy().getId());
        ReceiptHeader receiptHeader = new ReceiptHeader();
        receiptHeader.setBookingId(booking);
        receiptHeader.setCustomerId(customer);
        receiptHeader.setDescription(booking.getDescription());
        receiptHeader.setPaidAmount(booking.getPaidAmount());
        receiptHeader.setPaidStatus(Const.FULL_PAID);
        receiptHeader.setTotalAmount(booking.getTotalAmount());
        receiptHeader.setVehicleNo(booking.getVehicleNo());
        receiptRepository.save(receiptHeader);

        for (BookingDetails list:detailsList) {
            ReceiptDetail receiptDetail = new ReceiptDetail();
            receiptDetail.setBooking(list.getBooking());
            receiptDetail.setAmount(list.getAmount());
            receiptDetail.setServiceType(list.getServiceType());
            receiptDetailRepository.save(receiptDetail);
        }

        return receiptHeader;
    }

    @Override
    public ReceiptHeader findById(int id) {
        Booking booking = new Booking();
        booking.setId(id);
        return receiptRepository.findByBookingId(booking);
    }

    @Override
    public List<ReceiptHeader> findAllByCurrentDate(User user, int status, Date fdate, Date tdate) {
        return receiptRepository.findAllByCreatedByAndPaidStatusAndTimestampCreatedGreaterThanEqualAndTimestampCreatedLessThanEqual(user,status,fdate,tdate);
    }

    @Override
    public Double findByPaidStatusAndCreatedByWithSum(Date formtDate1, Date toDate1, User user) {
        return receiptRepository.findAllByPaidStatusAndSum(formtDate1,toDate1,user);
    }

    @Override
    public List<Object[]> findChartData(Date formtDate1, Date toDate1, User user) {
        return receiptRepository.findAllByChart(formtDate1,toDate1,user);
    }
}
