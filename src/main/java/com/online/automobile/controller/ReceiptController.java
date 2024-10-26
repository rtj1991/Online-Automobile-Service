package com.online.automobile.controller;

import com.online.automobile.model.*;
import com.online.automobile.service.booking.BookingDetailService;
import com.online.automobile.service.booking.BookingService;
import com.online.automobile.service.company.CompanyService;
import com.online.automobile.service.receipt.ReceiptDetailService;
import com.online.automobile.service.receipt.ReceiptService;
import com.online.automobile.util.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/receipt")
public class ReceiptController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private ReceiptService receiptService;

    @Autowired
    private BookingDetailService bookingDetailService;

    @Autowired
    private ReceiptDetailService receiptDetailService;

    @RequestMapping("/list")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','OWNER')")
    public String receiptList(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Company byUser = companyService.findByUser(user);
        List<Booking> booking = bookingService.getAllBookingBy(byUser, Const.PROCESS_COMPLETE);

        model.addAttribute("booking", booking);
        return "system/receipt/receipt_list";
    }

    @RequestMapping("/view_receipt/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','OWNER')")
    public String viewReceipt(@PathVariable Integer id, Model model) {
        ReceiptHeader receipt = receiptService.findById(id);
        List<ReceiptDetail> allByBooking = receiptDetailService.findAllByBooking(receipt.getBookingId());
        model.addAttribute("receipt", receipt);
        model.addAttribute("detalis", allByBooking);
        return "system/receipt/receipt";
    }

    @RequestMapping("/invoice")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','OWNER')")
    public String AllInvoices(Model model,HttpServletRequest request) {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Company company = companyService.findByUser(user);

        List<Booking> booking = bookingService.getAllBookingBy(company, Const.PROCESSING);

        model.addAttribute("booking", booking);
        return "system/invoice/invoice_list";
    }

    @RequestMapping("/view_invoice/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','OWNER')")
    public String viewInvoice(@PathVariable Integer id, Model model) {
        Booking booking = bookingService.getBookingById(id);
        List<BookingDetails> detailByBooking = bookingDetailService.getDetailByBooking(booking);

        model.addAttribute("booking", booking);
        model.addAttribute("detalis", detailByBooking);
        return "system/invoice/invoice";
    }

}
