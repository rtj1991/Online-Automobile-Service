package com.online.automobile.controller;

import com.online.automobile.dto.ReportSearch;
import com.online.automobile.model.Company;
import com.online.automobile.model.ReceiptHeader;
import com.online.automobile.model.User;
import com.online.automobile.service.company.CompanyService;
import com.online.automobile.service.receipt.ReceiptService;
import com.online.automobile.util.Const;
import com.online.automobile.util.UtilManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    private ReceiptService receiptService;

    @Autowired
    private CompanyService companyService;

    @RequestMapping("/income")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','OWNER')")
    public String incomeReportByDate(HttpServletRequest request, Model model, @ModelAttribute ReportSearch reportSearch) {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Company company = companyService.findByUser(user);
        String fistTime = " 00:00:01";
        String endTime = " 23:59:59";
        List<ReceiptHeader> receiptHeaders;
        if (reportSearch.getDateFrom() != null && reportSearch.getDateTo() != null) {

            String fDate = UtilManager.formatTimestampDate(reportSearch.getDateFrom()) + fistTime;
            String tDate = UtilManager.formatTimestampDate(reportSearch.getDateTo()) + endTime;
            Date formtDate1 = UtilManager.formatDate1(fDate);
            Date toDate1 = UtilManager.formatDate1(tDate);
            Double sum = receiptService.findByPaidStatusAndCreatedByWithSum(formtDate1, toDate1, user);
            receiptHeaders = receiptService.findAllByCurrentDate(user, Const.FULL_PAID, formtDate1, toDate1);
            model.addAttribute("sum", sum);
            model.addAttribute("receipt", receiptHeaders);
        } else {
            Date formatDate = UtilManager.formatDate(UtilManager.formatDate(new Date()));
            String fDate = (UtilManager.formatTimestampDate(formatDate) + fistTime);
            String tDate = (UtilManager.formatTimestampDate(formatDate) + endTime);
            Date formtDate1 = UtilManager.formatDate1(fDate);
            Date toDate1 = UtilManager.formatDate1(tDate);
            Double sum = receiptService.findByPaidStatusAndCreatedByWithSum(formtDate1, toDate1, user);
            receiptHeaders = receiptService.findAllByCurrentDate(user, Const.FULL_PAID, formtDate1, toDate1);
            model.addAttribute("sum", sum);
            model.addAttribute("receipt", receiptHeaders);
        }

        model.addAttribute("user", company);


        return "system/report/income_report";

    }

}
