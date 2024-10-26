package com.online.automobile.repository;

import com.online.automobile.model.Booking;
import com.online.automobile.model.ReceiptHeader;
import com.online.automobile.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ReceiptRepository extends PagingAndSortingRepository<ReceiptHeader,Integer> {

    ReceiptHeader findOne(int id);

    ReceiptHeader findByBookingId(Booking booking);

    List<ReceiptHeader>findAllByCreatedByAndPaidStatusAndTimestampCreatedGreaterThanEqualAndTimestampCreatedLessThanEqual(User user, int status, Date fdate,Date tdate);

    @Query("SELECT SUM(rs.paidAmount) FROM ReceiptHeader rs WHERE rs.timestampCreated >= :fdate AND rs.timestampCreated <= :tdate AND rs.createdBy=:user AND rs.paidStatus = 1")
    public Double findAllByPaidStatusAndSum(@Param("fdate") Date fdate,@Param("tdate") Date tdate,@Param("user") User user);

    @Query("SELECT timestampCreated AS day,SUM(paidAmount) AS summ FROM ReceiptHeader WHERE timestampCreated >=:fdate AND timestampCreated <= :tdate AND createdBy=:user AND paidStatus = 1 GROUP BY timestampCreated")
    public List<Object[]> findAllByChart(@Param("fdate") Date fdate,@Param("tdate") Date tdate,@Param("user") User user );
}
