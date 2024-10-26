package com.online.automobile.service.receipt;

import com.online.automobile.model.ReceiptHeader;
import com.online.automobile.model.User;

import java.util.Date;
import java.util.List;

public interface ReceiptService {
    ReceiptHeader CreateReceipt(int id);

    ReceiptHeader findById(int id);

    List<ReceiptHeader>findAllByCurrentDate(User user, int status, Date fdate, Date tdate);

    Double findByPaidStatusAndCreatedByWithSum(Date formtDate1, Date toDate1, User user);

    List<Object[]>findChartData(Date formtDate1, Date toDate1, User user);
}
