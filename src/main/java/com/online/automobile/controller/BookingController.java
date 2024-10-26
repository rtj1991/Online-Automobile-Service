package com.online.automobile.controller;

import com.online.automobile.dto.BookingDetail;
import com.online.automobile.dto.Information;
import com.online.automobile.dto.ReportSearch;
import com.online.automobile.model.*;
import com.online.automobile.repository.DataRepository;
import com.online.automobile.service.booking.BookingService;
import com.online.automobile.service.company.CompanyService;
import com.online.automobile.service.location.LocationService;
import com.online.automobile.service.priceList.PriceListService;
import com.online.automobile.service.rating.CompanyRatingService;
import com.online.automobile.service.vehicle.VehicleModelService;
import com.online.automobile.service.vehicle.VehicleTypeService;
import com.online.automobile.util.Const;
import com.online.automobile.util.UtilManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.text.DecimalFormat;
import java.util.*;


@Controller
@RequestMapping("/book")
public class BookingController {

    @Autowired
    private LocationService locationService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private VehicleModelService modelService;

    @Autowired
    private VehicleTypeService typeService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private PriceListService priceListService;

    @Autowired
    private CompanyRatingService ratingService;

    @Autowired
    private DataRepository dataRepository;

    @RequestMapping("/findService")
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN','CUSTOMER')")
    public String findService(Model model, @ModelAttribute ReportSearch reportSearch) {

        List<Location> location = locationService.findAllLocation();
        if (reportSearch.getLocation() != null) {
            List<Company> companyList = companyService.findCompanyByLocation(reportSearch.getLocation());
            model.addAttribute("companyList", companyList);
        }
        model.addAttribute("location", location);
        model.addAttribute("reportSearch", reportSearch);

        Information info = new Information();
        model.addAttribute("info", info);
        return "system/booking/find_service";

    }

    @RequestMapping("/booking/")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','CUSTOMER')")
    public String booking(Model model, @ModelAttribute ReportSearch reportSearch) {

        List<Location> location = locationService.findAllLocation();
        if (reportSearch.getLocation() != null) {

            List<Map<String, Object>> rating = dataRepository.findAllByCompanyLocationAndServiceTypesAndRating(reportSearch.getLocation());

            DecimalFormat df2 = new DecimalFormat("#.##");
            List<Map<String, Object>> maps = new ArrayList<>();
            for (Map<String, Object> rate:rating) {
                Map<String, Object> s = new HashMap<>();
                s.put("id",rate.get("id"));
                s.put("name",rate.get("name"));
                s.put("address",rate.get("address"));
                s.put("rating",df2.format(rate.get("rating")));
                s.put("location",rate.get("location"));
                s.put("start",rate.get("start"));
                s.put("endt",rate.get("endt"));
                s.put("type",rate.get("type"));
                s.put("servicetype",rate.get("servicetype"));
                maps.add(s);
            }

            model.addAttribute("companyList", maps);

        }
        model.addAttribute("location", location);
        model.addAttribute("reportSearch", reportSearch);

        Information info = new Information();
        model.addAttribute("info", info);
        return "system/booking/booking";
    }

    @RequestMapping("/companybooking/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','CUSTOMER')")
    public String companyBooking(Model model, @ModelAttribute BookingDetail detail, @PathVariable Integer id, HttpServletRequest request) {

        User user = (User) request.getSession().getAttribute("user");

        List<Booking> allBookingByDate;
        Company company = companyService.findByCompanyId(id);
        List<VehicleModel> vehicleModels = modelService.getAllVehicleModels();
        List<VehicleType> vehicleTypes = typeService.getAllVehicleTypes();
        String fistTime = " 00:00:01";
        String endTime = " 23:59:59";
        if (detail.getSearchDate() != null) {
            String fDate = detail.getSearchDate() + fistTime;
            String tDate = detail.getSearchDate() + endTime;
            Date formtDate1 = UtilManager.formatDate1(fDate);
            Date toDate1 = UtilManager.formatDate1(tDate);
            allBookingByDate = bookingService.getAllBookingByDate(company, formtDate1, toDate1);
        } else {
            Date formatDate = UtilManager.formatDate(UtilManager.formatDate(new Date()));
            String fDate = (UtilManager.formatTimestampDate(formatDate) + fistTime);
            String tDate = (UtilManager.formatTimestampDate(formatDate) + endTime);
            Date formtDate1 = UtilManager.formatDate1(fDate);
            Date toDate1 = UtilManager.formatDate1(tDate);
            allBookingByDate = bookingService.getAllBookingByDate(company, formtDate1, toDate1);
        }
        List<Booking> rating = bookingService.getAllByCompanyAndUser(company, user);
        List<CompanyRating> companyRatings = ratingService.getAllByCompanyAndUser(company, user);
        model.addAttribute("company", company);
        model.addAttribute("models", vehicleModels);
        model.addAttribute("types", vehicleTypes);
        model.addAttribute("booking", (company.getBookingLimit()) - (allBookingByDate.size()));
        model.addAttribute("detail", detail);
        if (rating.size() > 0) {
            if (companyRatings.size() > 0) {
                model.addAttribute("status", Const.STATUS_DEACTIVE);
            } else {
                model.addAttribute("status", Const.STATUS_ACTIVE);
            }
        } else {
            model.addAttribute("status", Const.STATUS_DEACTIVE);
        }

        return "system/booking/company_booking";
    }

    //    save booking
    @RequestMapping("/saveBooking/")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','CUSTOMER')")
    public String saveBooking(@Valid @ModelAttribute BookingDetail detail, BindingResult result, RedirectAttributes attributes, @RequestParam Map<String, String> map, HttpServletRequest request) {
        if (result.hasErrors()) {
            attributes.addFlashAttribute("org.springframework.validation.BindingResult.detail", result);
            attributes.addFlashAttribute("detail", detail);
        }

        bookingService.CreateBooking(detail, map, request);

        attributes.addFlashAttribute("status", Const.STATUS_ACTIVE);
        return "redirect:/book/list/";
    }

    //    list Booking
    @RequestMapping("/list/")
    public String BookingList(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Company byUser = companyService.findByUser(user);

        int id = (Integer) user.getId();
        if (user.getUserType() == Const.GARAGE || user.getUserType() == Const.VEHICLE_SERVICE) {
            List<Booking> allBookingByCompany = bookingService.getAllBookingByCompany(byUser, Const.PRIMARY_TYPE, Const.PROCESSING_PENDING);
            model.addAttribute("bookingList", allBookingByCompany);

        } else if (user.getUserType() == Const.ADMIN) {
            List<Booking> bookingList = bookingService.getAllBooking();
            model.addAttribute("bookingList", bookingList);
        } else if (user.getUserType() == Const.VEHICLE_OWNER) {
            List<Booking> bookingList = bookingService.getAllByUser(user);
            model.addAttribute("bookingList", bookingList);
        }
        model.addAttribute("user_type", user.getUserType());
        if (user.getUserType() == Const.GARAGE || user.getUserType() == Const.VEHICLE_SERVICE) {

            List<ServiceType> serviceTypes = byUser.getServiceTypes();
            if (serviceTypes.size() == Const.STATUS_DEACTIVE) {
                return "system/user/add_service_type";
            } else {
                return "system/booking/booking_list";
            }
        } else {
            return "system/booking/booking_list";
        }
    }

    //    edit booking start
    @RequestMapping("/edit_booking/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','CUSTOMER')")
    public String editBooking(@PathVariable Integer id, Model model, @ModelAttribute BookingDetail detail, HttpServletRequest request) {
        Booking booking = bookingService.getBookingById(id);

        String appointedDate = UtilManager.formatTimestampDate(booking.getAppointedDate());
        String fistTime = " 00:00:01";
        String endTime = " 23:59:59";

        String first = appointedDate + fistTime;
        String end = appointedDate + endTime;

        List<Booking> list = bookingService.getAllBookingByDate(booking.getBookedCompany(), UtilManager.formatDate1(first), UtilManager.formatDate1(end));

        BookingDetail bookingDetail = new BookingDetail();
        bookingDetail.setDescription(booking.getDescription());
        bookingDetail.setVehicleNo(booking.getVehicleNo());
        bookingDetail.setvType(booking.getVehicle_type());
        bookingDetail.setModel(booking.getVehicle_model());

        model.addAttribute("company", booking.getBooked_company());
        model.addAttribute("models", booking.getVehicle_model());
        model.addAttribute("types", booking.getVehicle_type());
        model.addAttribute("booking", (booking.getBooked_company().getBookingLimit()) - (list.size()));
        model.addAttribute("detail", bookingDetail);

        return "system/booking/edit_booking";
    }

    @RequestMapping("/updateBooking/")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','CUSTOMER')")
    public String updateBooking(@Valid @ModelAttribute BookingDetail detail, BindingResult result, RedirectAttributes attributes, @RequestParam Map<String, String> map, Model model) {

        if (result.hasErrors()) {
            attributes.addFlashAttribute("org.springframework.validation.BindingResult.detail", result);
            attributes.addFlashAttribute("detail", detail);
        }
        bookingService.updateBooking(detail, map);
        attributes.addFlashAttribute("status", Const.STATUS_ACTIVE);
        return "redirect:/book/list/";
    }
//    edit booking end


    //    Carrier service start

    @RequestMapping("/carrier_service/")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','CUSTOMER')")
    public String carrierService(Model model, @ModelAttribute ReportSearch reportSearch) {

        List<Location> location = locationService.findAllLocation();
        if (reportSearch.getLocation() != null) {
            List<Company> companyList = companyService.findAllByCompanyLocationAndServiceTypes(reportSearch.getLocation(), Const.CARRIER_SERVICE);
            model.addAttribute("companyList", companyList);
        }
        model.addAttribute("location", location);
        model.addAttribute("reportSearch", reportSearch);

        Information info = new Information();
        model.addAttribute("info", info);
        return "system/booking/carrier_service";
    }

    @RequestMapping("/carrier_service_booking/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','CUSTOMER')")
    public String carrierServiceBooking(Model model, @ModelAttribute BookingDetail detail, @PathVariable Integer id, HttpServletRequest request) {

        Company company = companyService.findByCompanyId(id);
        List<VehicleModel> vehicleModels = modelService.getAllVehicleModels();
        List<VehicleType> vehicleTypes = typeService.getAllVehicleTypes();
        PriceList price = priceListService.getPriceByServiceTypeType(Const.CARRIER_SERVICE);

        model.addAttribute("company", company);
        model.addAttribute("models", vehicleModels);
        model.addAttribute("types", vehicleTypes);
        model.addAttribute("detail", detail);
        model.addAttribute("price", price.getAmount());

        return "system/booking/carrier_service_booking";
    }

    @RequestMapping("/save_carrier_service/")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','CUSTOMER')")
    public String saveCarrierBooking(@Valid @ModelAttribute BookingDetail detail, BindingResult result, RedirectAttributes attributes, HttpServletRequest request) {
        if (result.hasErrors()) {
            attributes.addFlashAttribute("org.springframework.validation.BindingResult.detail", result);
            attributes.addFlashAttribute("detail", detail);
        }

        bookingService.CreateCarrierServiceBooking(detail, request);

        attributes.addFlashAttribute("status", Const.STATUS_ACTIVE);
        return "redirect:/book/carrier_list/";
    }

    @RequestMapping("/carrier_list/")
    public String BookingCarrierList(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Company byUser = companyService.findByUser(user);

        int id = (Integer) user.getId();
        if (user.getLocation() != null) {
            List<Booking> allBookingByCompany = bookingService.getAllBookingByCompany(byUser, Const.CARRIER_SERVICE, Const.PROCESSING_PENDING);
            model.addAttribute("bookingList", allBookingByCompany);

        } else if (id == 1) {
            List<Booking> bookingList = bookingService.getAllBooking();
            model.addAttribute("bookingList", bookingList);
        } else {
            List<Booking> bookingList = bookingService.getAllBookingBy(user, Const.CARRIER_SERVICE, Const.PROCESS_COMPLETE);
            model.addAttribute("bookingList", bookingList);
        }
        model.addAttribute("user_type", user.getUserType());

        return "system/booking/carrier_service_list";
    }

    //    Carrier service end

    //    Field Officer Start

    @RequestMapping("/field_officer/")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','CUSTOMER')")
    public String fieldOfficer(Model model, @ModelAttribute ReportSearch reportSearch) {

        List<Location> location = locationService.findAllLocation();
        if (reportSearch.getLocation() != null) {
            List<Company> companyList = companyService.findAllByCompanyLocationAndServiceTypes(reportSearch.getLocation(), Const.FIELD_OFFICER);
            model.addAttribute("companyList", companyList);
        }
        model.addAttribute("location", location);
        model.addAttribute("reportSearch", reportSearch);

        Information info = new Information();
        model.addAttribute("info", info);
        return "system/booking/field_officer";
    }


    @RequestMapping("/field_officer_booking/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','CUSTOMER')")
    public String fieldOfficerBooking(Model model, @ModelAttribute BookingDetail detail, @PathVariable Integer id, HttpServletRequest request) {

        Company company = companyService.findByCompanyId(id);
        List<VehicleModel> vehicleModels = modelService.getAllVehicleModels();
        List<VehicleType> vehicleTypes = typeService.getAllVehicleTypes();
        PriceList price = priceListService.getPriceByServiceTypeType(Const.FIELD_OFFICER);

        model.addAttribute("company", company);
        model.addAttribute("models", vehicleModels);
        model.addAttribute("types", vehicleTypes);
        model.addAttribute("detail", detail);
        model.addAttribute("price", price.getAmount());

        return "system/booking/field_officer_booking";
    }

    @RequestMapping("/save_field_officer/")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','CUSTOMER')")
    public String saveFieldOfficer(@Valid @ModelAttribute BookingDetail detail, BindingResult result, RedirectAttributes attributes, HttpServletRequest request) {
        if (result.hasErrors()) {
            attributes.addFlashAttribute("org.springframework.validation.BindingResult.detail", result);
            attributes.addFlashAttribute("detail", detail);
        }
        bookingService.CreateFieldOfficerBooking(detail, request);

        attributes.addFlashAttribute("status", Const.STATUS_ACTIVE);
        return "redirect:/book/field_list/";
    }

    @RequestMapping("/field_list/")
    public String BookingOfficerList(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Company byUser = companyService.findByUser(user);

        int id = (Integer) user.getId();
        if (user.getLocation() != null) {
            List<Booking> allBookingByCompany = bookingService.getAllBookingByCompany(byUser, Const.FIELD_OFFICER, Const.PROCESSING_PENDING);
            model.addAttribute("bookingList", allBookingByCompany);

        } else if (id == 1) {
            List<Booking> bookingList = bookingService.getAllBooking();
            System.out.println("bookingList--1--> "+bookingList);
            model.addAttribute("bookingList", bookingList);
        } else {
            List<Booking> bookingList = bookingService.getAllBookingBy(user, Const.FIELD_OFFICER, Const.PROCESS_COMPLETE);
            System.out.println("bookingList----> "+bookingList);
            model.addAttribute("bookingList", bookingList);
        }
        model.addAttribute("user_type", user.getUserType());

        return "system/booking/field_officer_list";
    }
    //    Field Officer end
}
