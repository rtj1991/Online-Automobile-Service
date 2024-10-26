package com.online.automobile.controller;

import com.online.automobile.config.EmailConfig;
import com.online.automobile.config.FeedBack;
import com.online.automobile.model.*;
import com.online.automobile.repository.*;
import com.online.automobile.service.booking.BookingService;
import com.online.automobile.service.company.CompanyService;
import com.online.automobile.service.location.LocationService;
import com.online.automobile.service.priceList.PriceListService;
import com.online.automobile.service.rating.CompanyRatingService;
import com.online.automobile.service.receipt.ReceiptService;
import com.online.automobile.service.serviceType.ServiceTypeService;
import com.online.automobile.service.user.UserService;
import com.online.automobile.service.vehicle.VehicleModelService;
import com.online.automobile.service.vehicle.VehicleTypeService;
import com.online.automobile.util.Const;
import com.online.automobile.util.UtilManager;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.bind.ValidationException;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private UserService userService;

    @Autowired
    private ServiceTypeService serviceTypeService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private PriceListService priceListService;

    @Autowired
    private ServiceTypeRepository typeRepository;

    @Autowired
    private BookingDetailRepository detailRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ReceiptService receiptService;

    @Autowired
    private DataRepository dataRepository;

    @Autowired
    private CompanyRatingService ratingService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private VehicleModelService modelService;

    @Autowired
    private VehicleTypeService typeService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    UserRepository userRepository;

    @RequestMapping("/sidebar")
    public boolean toggleSidebar(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        boolean menuCollapse = user.isMenuCollapse();
        user.setMenuCollapse(!menuCollapse);
        User saveUser = userService.saveUser(user);
        return saveUser.isMenuCollapse() != menuCollapse;
    }


    @PostMapping("/getService_type")
    public List<JSONObject> getAllServiceTypes(HttpServletRequest request) {
        ArrayList<JSONObject> list = new ArrayList<>();
        List<ServiceType> serviceTypes = serviceTypeService.getAllServiceTypes(request);

        for (ServiceType types : serviceTypes) {
            JSONObject object = new JSONObject();
            object.put("id", types.getId());
            object.put("description", types.getDescription());

            list.add(object);
        }
        return list;
    }

    @RequestMapping("/getCompany_service_type")
    public List<JSONObject> getCompanyServiceTypes(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        Company company = companyService.findByUser(user);
        ArrayList<JSONObject> list = new ArrayList<>();
        List<ServiceType> serviceTypes = serviceTypeService.getCompanyServiceType(company);

        for (ServiceType types : serviceTypes) {

            ServiceType type = new ServiceType();
            type.setId(types.getId());
            PriceList price = priceListService.getPriceByServiceType(type);

            JSONObject object = new JSONObject();
            object.put("id", types.getId());
            object.put("description", types.getDescription());
            object.put("price", price.getAmount());

            list.add(object);
        }
        return list;
    }

    @RequestMapping("/company_service")
    public List<JSONObject> getAll(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        Company company = companyService.findByUser(user);

        ArrayList<JSONObject> list = new ArrayList<>();
        List<ServiceType> serviceTypes = typeRepository.findAllByCompaniesIsNotContaining(company);
        for (ServiceType type : serviceTypes) {
            JSONObject object = new JSONObject();
            object.put("id", type.getId());
            object.put("description", type.getDescription());
            list.add(object);
        }

        return list;
    }

    @RequestMapping("/save_Service_type")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','OWNER')")
    public Company saveServiveTypes(@RequestParam Map<String, String> map, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        Company company = companyService.findByUser(user);
        Company serviceTypes = companyService.saveServiceTypes(company, map);
        return serviceTypes;
    }

    @RequestMapping("/save_new_Service_type")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','OWNER')")
    public Company saveNewServiveTypes(@RequestParam Map<String, String> map, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        Company company = companyService.findByUser(user);
        Company companyServiceType = companyService.saveNewServiceTypes(company, map);

        return companyServiceType;
    }

    @RequestMapping("/getMainService")
    public List<JSONObject> getMainService(@RequestParam Map<String, String> company) {

        ArrayList<JSONObject> list = new ArrayList<>();
        List<ServiceType> serviceTypes = serviceTypeService.getServiceTypeByStatus(company, Const.PRIMARY_TYPE);

        for (ServiceType types : serviceTypes) {
            ServiceType type = new ServiceType();
            type.setId(types.getId());
            PriceList price = priceListService.getPriceByServiceType(type);

            JSONObject object = new JSONObject();
            object.put("id", types.getId());
            object.put("description", types.getDescription());
            object.put("price", price.getAmount());
            object.put("status", types.getType());


            list.add(object);
        }
        return list;
    }

    @RequestMapping("/getSecondaryService")
    public List<JSONObject> getSecondaryService(@RequestParam Map<String, String> company) {

        ArrayList<JSONObject> list = new ArrayList<>();
        List<ServiceType> serviceTypes = serviceTypeService.getServiceTypeByStatus(company, Const.SECONDARY_TYPE);
        for (ServiceType types : serviceTypes) {
            ServiceType type = new ServiceType();
            type.setId(types.getId());
            PriceList price = priceListService.getPriceByServiceType(type);

            JSONObject object = new JSONObject();
            object.put("id", types.getId());
            object.put("description", types.getDescription());
            object.put("price", price.getAmount());
            object.put("status", types.getType());

            list.add(object);
        }
        return list;
    }

    @Autowired
    private EmailConfig emailConfig;

    @RequestMapping("/feedback")
    public void sendFeedback(@RequestBody FeedBack feedBack, BindingResult result) throws Exception {
        if (result.hasErrors()) {
            throw new ValidationException("FeedBack is Not Valid");
        }

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(emailConfig.getHost());
        mailSender.setPort(emailConfig.getPort());
        mailSender.setUsername(emailConfig.getUsername());
        mailSender.setPassword(emailConfig.getPassword());

//        Create an email instanse
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(feedBack.getEmail());
        mailMessage.setTo(feedBack.getRecieveremail());
        mailMessage.setSubject("New Feedback From " + feedBack.getName());
        mailMessage.setText(feedBack.getFeedback());

        mailSender.send(mailMessage);
    }

    @RequestMapping("/viewBooking")
    public Map<String, ArrayList<JSONObject>> viewBooking(@RequestParam Map<String, String> map) {

        Booking booking = new Booking();
        booking.setId(Integer.parseInt(map.get("id")));

        List<BookingDetails> allByBooking = detailRepository.findAllByBookingAndDeleted(booking, Const.DELETED_FALSE);
        Booking bookingId = bookingRepository.findOne(Integer.parseInt(map.get("id")));

        Map<String, ArrayList<JSONObject>> listMap = new HashMap<>();
        ArrayList<JSONObject> list = new ArrayList<>();
        ArrayList<JSONObject> arr = new ArrayList<>();

        JSONObject objec = new JSONObject();
        JSONObject obj = new JSONObject();
        for (BookingDetails types : allByBooking) {
            JSONObject object = new JSONObject();

            object.put("id", types.getId());
            object.put("service", types.getServiceType().getId());
            object.put("amount", types.getAmount());
            object.put("type", types.getServiceType().getDescription());

            list.add(object);
        }

        obj.put("booking", bookingId.getDescription());
        obj.put("total", bookingId.getTotalAmount());
        obj.put("bookingId", bookingId.getId());
        objec.put("bookid,", list);
        arr.add(obj);
        listMap.put("list", list);
        listMap.put("booking", arr);

        return listMap;
    }

    @RequestMapping("/cancelBooking")
    public Booking cancelBooking(@RequestParam Map<String, String> map) {

        Booking bookingId = bookingRepository.findOne(Integer.parseInt(map.get("id")));
        bookingId.setCancelStatus(Const.DELETED_TRUE);
        bookingId.setProcessStatus(Const.PROCESSING_CANCEL);
        Booking booking = bookingRepository.save(bookingId);

        return booking;
    }

    @RequestMapping("/booking_progress")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','OWNER')")
    public Booking progressBooking(@RequestParam Map<String, String> map, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        Booking bookingId = bookingRepository.findOne(Integer.parseInt(map.get("id")));
        bookingId.setProcessStatus(Const.PROCESSING);
        Booking booking = bookingRepository.save(bookingId);
        if (booking != null) {

            JSONObject object = new JSONObject();
            object.put("name", "thara");
            object.put("email", "test@gmail.com");
            object.put("recieveremail", user.getRecoveryEmail());
            object.put("feedback", "Dear Customer, Your Booking Is Progressing.\n We Will Send You Message After The Progress Was Completed !");

            String url = "http://localhost:8080/api/feedback";
            HttpPost httpPost = new HttpPost(url);
            HttpClient client = HttpClients.createDefault();
            httpPost.addHeader("content-type", "application/json");
            try {
                httpPost.setEntity(new StringEntity(object.toString()));
                HttpResponse execute = client.execute(httpPost);
            } catch (Exception e) {
                e.printStackTrace();
            }

            String sms = "Your Reference is <INV" + 105 + ">.\n" ;
            sms += "Pay AUD. " + UtilManager.formatDouble(1000.00) + " \n";

            sms +=   "Deposit to Best Life Acc 005710002195 (Sampath Bank).\n" +
                    "Thank you again for your business.";

            CloseableHttpClient httpclient = HttpClients.createDefault();
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("username", Const.SMS_GATEWAY_USERNAME));
            params.add(new BasicNameValuePair("password", Const.SMS_GATEWAY_PASSWORD));
            params.add(new BasicNameValuePair("src", Const.SMS_GATEWAY_SRC));
            params.add(new BasicNameValuePair("dst", "0714281772"));
            params.add(new BasicNameValuePair("msg", sms));
            params.add(new BasicNameValuePair("dr", "0"));

            String paramString = URLEncodedUtils.format(params,"utf-8");
            HttpGet get = new HttpGet("http://sms.airtel.lk:5000/sms/send_sms.php?" + paramString);
            try {
                HttpResponse execute = httpclient.execute(get);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return booking;
    }

    @RequestMapping("/complete_progress")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','OWNER')")
    public ReceiptHeader completeProgressBooking(@RequestParam Map<String, String> map, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        ReceiptHeader receipt = receiptService.CreateReceipt(Integer.parseInt(map.get("id")));

        if (receipt != null) {

            JSONObject object = new JSONObject();
            object.put("name", "thara");
            object.put("email", "test@gmail.com");
            object.put("recieveremail", user.getRecoveryEmail());
            object.put("feedback", "Dear Customer, Your Booking Was Completed.\n Your Receipt Number is " + receipt.getId() + "\n And Your Service Fee: " + receipt.getPaidAmount());

            String url = "http://localhost:8080/api/feedback";
            HttpPost httpPost = new HttpPost(url);
            HttpClient client = HttpClients.createDefault();
            httpPost.addHeader("content-type", "application/json");
            try {
                httpPost.setEntity(new StringEntity(object.toString()));
                HttpResponse execute = client.execute(httpPost);
            } catch (Exception e) {
                e.printStackTrace();
            }

            String sms = "Your Reference is <INV" + 105 + ">.\n" ;
            sms += "Pay AUD. " + UtilManager.formatDouble(1000.00) + " \n";

            sms +=   "Deposit to Best Life Acc 005710002195 (Sampath Bank).\n" +
                    "Thank you again for your business.";

            CloseableHttpClient httpclient = HttpClients.createDefault();
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("username", Const.SMS_GATEWAY_USERNAME));
            params.add(new BasicNameValuePair("password", Const.SMS_GATEWAY_PASSWORD));
            params.add(new BasicNameValuePair("src", Const.SMS_GATEWAY_SRC));
            params.add(new BasicNameValuePair("dst", "0714281772"));
            params.add(new BasicNameValuePair("msg", sms));
            params.add(new BasicNameValuePair("dr", "0"));

            String paramString = URLEncodedUtils.format(params,"utf-8");
            HttpGet get = new HttpGet("http://sms.airtel.lk:5000/sms/send_sms.php?" + paramString);
            try {
                HttpResponse execute = httpclient.execute(get);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return receipt;
    }

    @RequestMapping("/chart")
    public Object getExpenses(@RequestParam Map<String, String> maps, HttpServletRequest request) {

        User user = (User) request.getSession().getAttribute("user");
        String fdate = maps.get("fdate");
        String todate = maps.get("todate");
        String fDate;
        String tDate;
        if (!fdate.isEmpty() && !todate.isEmpty()) {
            fDate = fdate;
            tDate = todate;
        } else {
            Date formatDate = UtilManager.formatDate(UtilManager.formatDate(new Date()));
            fDate = UtilManager.formatTimestampDate(formatDate);
            tDate = (UtilManager.formatTimestampDate(formatDate));
        }

        List<Map<String, Object>> chartData = dataRepository.findAllByChart(fDate, tDate, user);
        List<Object> list = new ArrayList<>();

        for (Map<String, Object> map : chartData) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("days", map.get("days"));
            jsonObject.put("sum", map.get("summ"));

            list.add(jsonObject);
        }

        return list;
    }

    @RequestMapping("/save_rating")
    public Object saveRatinf(@RequestParam Map<String, String> map, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        CompanyRating companyRating = ratingService.saveCompanyRating(map, user);
        JSONObject object = new JSONObject();
        if (companyRating != null) {
            object.put("status", Const.STATUS_ACTIVE);
        } else {
            object.put("status", Const.STATUS_DEACTIVE);
        }

        return object;
    }


    @RequestMapping(value = "/user_login/{username}/{password}")
    public Object login(@PathVariable String username, @PathVariable String password) {

        User user = userService.findByUsername(username);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean matches;
        if (user != null) {
            matches = passwordEncoder.matches(password, user.getPassword());
        } else {
            matches = false;
        }


        JSONObject object = new JSONObject();
        if (matches == true) {
            object.put("message", "true");
        } else {
            object.put("message", "false");
        }
        return object;
    }


    @RequestMapping(value = "/location")
    public Object locations() {
        List<Object> arrayList = new ArrayList<>();
        List<Location> location = locationService.findAllLocation();

        for (Location list : location) {
            JSONObject object = new JSONObject();
            object.put("id", list.getId());
            object.put("name", list.getLocationName());
            arrayList.add(object);
        }


        return arrayList;
    }


    @RequestMapping(value = "/all_company/{id}")
    public Object company(@PathVariable String id) {
        Location location = new Location();
        location.setId(Integer.parseInt(id));
        List<Object> arrayList = new ArrayList<>();
        List<Company> companyList = companyService.findCompanyByLocation(location);

        for (Company list : companyList) {
            JSONObject object = new JSONObject();
            object.put("id", list.getId());
            object.put("name", list.getCompanyName());
            object.put("limit", list.getBookingLimit());
            object.put("location", list.getCompanyLocation().getLocationName());
            object.put("start", list.getStartTime());
            object.put("end", list.getEndTime());
            object.put("type", list.getCompanyType().getType());
            arrayList.add(object);
        }


        return arrayList;
    }

    @RequestMapping(value = "/v_model")
    public Object vehicleModel() {
        List<Object> arrayList = new ArrayList<>();
        List<VehicleModel> vehicleModels = modelService.getAllVehicleModels();

        for (VehicleModel list : vehicleModels) {
            JSONObject object = new JSONObject();
            object.put("id", list.getId());
            object.put("name", list.getModelName());
            arrayList.add(object);
        }


        return arrayList;
    }

    @RequestMapping(value = "/v_type")
    public Object vehicleType() {
        List<Object> arrayList = new ArrayList<>();
        List<VehicleType> vehicleTypes = typeService.getAllVehicleTypes();

        for (VehicleType list : vehicleTypes) {
            JSONObject object = new JSONObject();
            object.put("id", list.getId());
            object.put("name", list.getName());
            arrayList.add(object);
        }


        return arrayList;
    }

    @RequestMapping(value = "/v_primary/{company}")
    public Object vehiclePrimary(@PathVariable String company) {
        Company company1 = new Company();
        company1.setId(Integer.parseInt(company));
        List<Object> arrayList = new ArrayList<>();
        List<ServiceType> primaryServiceTypes = typeRepository.findAllByCompaniesAndType(company1, Const.PRIMARY_TYPE);

        for (ServiceType list : primaryServiceTypes) {
            JSONObject object = new JSONObject();
            object.put("id", list.getId());
            object.put("name", list.getDescription());
            arrayList.add(object);
        }


        return arrayList;
    }

    @RequestMapping(value = "/v_second/{company}")
    public Object vehicleSecondary(@PathVariable String company) {
        Company company1 = new Company();
        company1.setId(Integer.parseInt(company));
        List<Object> arrayList = new ArrayList<>();
        List<ServiceType> secondServiceTypes = typeRepository.findAllByCompaniesAndType(company1, Const.SECONDARY_TYPE);

        for (ServiceType list : secondServiceTypes) {
            JSONObject object = new JSONObject();
            object.put("id", list.getId());
            object.put("name", list.getDescription());
            arrayList.add(object);
        }


        return arrayList;
    }

    @RequestMapping(value = "/booking")
    public Object saveBookingApi(@RequestBody Map<String, String> map) {


        Booking saveBooking = new Booking();
        Company company = new Company();
        company.setId(Integer.parseInt(map.get("company_id")));
        VehicleModel vehicleModel = new VehicleModel();
        vehicleModel.setId(Integer.parseInt(map.get("v_model")));
        VehicleType type = new VehicleType();
        type.setId(Integer.parseInt(map.get("v_type")));
        String primary = map.get("primary");
        String secondary = map.get("secondary");

        ServiceType primaryId = typeRepository.findById(Integer.parseInt(primary));
        ServiceType secondaryId = typeRepository.findById(Integer.parseInt(secondary));
        PriceList primaryPrice = priceListService.getPriceByServiceType(primaryId);
        PriceList secondaryPrice = priceListService.getPriceByServiceType(secondaryId);

        double samount;
        if (secondaryPrice!=null){
            samount=secondaryPrice.getAmount();
        }else {
            samount=0.0;
        }
        String user = map.get("user");
        User username = userService.findByUsername(user);

        Date date = UtilManager.formatDate(map.get("apinted_date"));

        saveBooking.setAppointedDate(date);
        saveBooking.setBookingType(Const.PRIMARY_TYPE);
        saveBooking.setCancelStatus(Const.DELETED_FALSE);
        saveBooking.setDescription(map.get("description"));
        saveBooking.setPaidAmount(0.0);
        saveBooking.setPaidStatus(Const.STATUS_DEACTIVE);
        saveBooking.setProcessStatus(Const.PROCESSING_PENDING);
        saveBooking.setTotalAmount((primaryPrice.getAmount() + samount));
        saveBooking.setVehicleNo(map.get("v_number"));
        saveBooking.setBooked_company(company);
        saveBooking.setVehicle_model(vehicleModel);
        saveBooking.setVehicle_type(type);
        saveBooking.setCreatedBy(username);
        Booking save = bookingRepository.save(saveBooking);

        if (primaryId != null) {
            BookingDetails bookingDetail = new BookingDetails();
            bookingDetail.setAmount(primaryPrice.getAmount());
            bookingDetail.setServiceType(primaryId);
            bookingDetail.setBooking(save);
            bookingDetail.setDeleted(0);
            detailRepository.save(bookingDetail);
        }
        if (secondary != null) {
            BookingDetails bookingDetail = new BookingDetails();
            bookingDetail.setAmount(samount);
            bookingDetail.setServiceType(secondaryId);
            bookingDetail.setBooking(save);
            bookingDetail.setDeleted(0);
            detailRepository.save(bookingDetail);
        }
        if (save !=null){
            //        email configuration start *send

            JSONObject object = new JSONObject();
            object.put("name", "thara");
            object.put("email", "test@gmail.com");
            object.put("recieveremail", "test1@gmail.com");
            object.put("feedback", "Successfully Add The Booking. Your Booking No is "+save.getId()+"\n"+"Appointed Date "+UtilManager.formatTimestampDate(save.getAppointedDate()));

            String url = "http://localhost:8080/api/feedback";
            HttpPost httpPost = new HttpPost(url);
            HttpClient client = HttpClients.createDefault();
            httpPost.addHeader("content-type", "application/json");
            try {
                httpPost.setEntity(new StringEntity(object.toString()));
                HttpResponse execute = client.execute(httpPost);
            } catch (Exception e) {
                e.printStackTrace();
            }
//        email configuration end
//
//        sms configuration start

        if (save != null) {
            String sms = "Your Reference is <INV" + 100 + ">.\n" ;
            sms += "Pay AUD. " + UtilManager.formatDouble(1000.00) + " \n";

            sms +=   "Deposit to Best Life Acc 005710002195 (Sampath Bank).\n" +
                    "Thank you again for your business.";

            CloseableHttpClient httpclient = HttpClients.createDefault();
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("username", Const.SMS_GATEWAY_USERNAME));
            params.add(new BasicNameValuePair("password", Const.SMS_GATEWAY_PASSWORD));
            params.add(new BasicNameValuePair("src", Const.SMS_GATEWAY_SRC));
            params.add(new BasicNameValuePair("dst", "0714281772"));
            params.add(new BasicNameValuePair("msg", sms));
            params.add(new BasicNameValuePair("dr", "0"));

            String paramString = URLEncodedUtils.format(params,"utf-8");
            HttpGet get = new HttpGet("http://sms.airtel.lk:5000/sms/send_sms.php?" + paramString);
            try {
                HttpResponse execute = httpclient.execute(get);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//        email configuration end
        }

        boolean matches;
        if (save != null) {
            matches = true;
        } else {
            matches = false;
        }


        JSONObject object = new JSONObject();
        if (matches == true) {
            object.put("message", "true");
        } else {
            object.put("message", "false");
        }
        return object;
    }

    @RequestMapping(value = "/view_booking/{user}/")
    public Object viewBooking(@PathVariable String user) {
        System.out.println("user---> "+user);
        User username = userService.findByUsername(user);
        List<Object> arrayList = new ArrayList<>();
        System.out.println("username----------> "+username);
        List<Booking> bookingList = bookingRepository.findAllByCreatedBy(username);
        System.out.println("bookingList--------> "+bookingList.size());
        for (Booking list : bookingList) {
            JSONObject object = new JSONObject();
            object.put("id", list.getId());
            object.put("appointed_date", list.getAppointedDate());
            object.put("booking_type", list.getBookingType());
            object.put("description", list.getDescription());
            object.put("process_status", list.getProcessStatus());
            object.put("total_amount", list.getTotalAmount());
            object.put("vehicle_number", list.getVehicleNo());
            arrayList.add(object);
        }

        return arrayList;
    }

    @RequestMapping(value = "/cancel_booking/{user}/{id}")
    public Object viewBooking(@PathVariable String user, @PathVariable int id) {

        Booking bookingId = bookingRepository.findOne(id);
        bookingId.setCancelStatus(Const.DELETED_TRUE);
        bookingId.setProcessStatus(Const.PROCESSING_CANCEL);
        Booking booking = bookingRepository.save(bookingId);

        User username = userService.findByUsername(user);
        List<Object> arrayList = new ArrayList<>();

        List<Booking> bookingList = bookingRepository.findAllByCreatedBy(username);

        for (Booking list : bookingList) {
            JSONObject object = new JSONObject();
            object.put("id", list.getId());
            object.put("appointed_date", list.getAppointedDate());
            object.put("booking_type", list.getBookingType());
            object.put("description", list.getDescription());
            object.put("process_status", list.getProcessStatus());
            object.put("total_amount", list.getTotalAmount());
            object.put("vehicle_number", list.getVehicleNo());
            arrayList.add(object);
        }

        return arrayList;
    }

    @RequestMapping(value = "/all_carrier_company/{id}")
    public Object carierCompany(@PathVariable String id) {
        Location location = new Location();
        location.setId(Integer.parseInt(id));
        List<Object> arrayList = new ArrayList<>();
        List<Company> companyList = companyService.findAllByCompanyLocationAndServiceTypes(location, Const.CARRIER_SERVICE);

        for (Company list : companyList) {
            JSONObject object = new JSONObject();
            object.put("id", list.getId());
            object.put("name", list.getCompanyName());
            object.put("limit", list.getBookingLimit());
            object.put("location", list.getCompanyLocation().getLocationName());
            object.put("start", list.getStartTime());
            object.put("end", list.getEndTime());
            object.put("type", list.getCompanyType().getType());
            arrayList.add(object);
        }

        return arrayList;
    }

    @RequestMapping(value = "/carrier_booking")
    public Object saveCarrierBookingApi(@RequestBody Map<String, String> map) {


        Booking saveBooking = new Booking();


        VehicleModel vehicleModel = new VehicleModel();
        vehicleModel.setId(Integer.parseInt(map.get("v_model")));
        VehicleType type = new VehicleType();
        type.setId(Integer.parseInt(map.get("v_type")));
        Company company_id = companyService.findByCompanyId(Integer.parseInt(map.get("company_id")));

        ServiceType carrier = typeRepository.findByType(Const.CARRIER_SERVICE);

        PriceList carrierPrice = priceListService.getPriceByServiceType(carrier);

        String user = map.get("user");
        User username = userService.findByUsername(user);

        saveBooking.setAppointedDate(new Date());
        saveBooking.setBookingType(Const.CARRIER_SERVICE);
        saveBooking.setCancelStatus(Const.DELETED_FALSE);
        saveBooking.setDescription(map.get("description"));
        saveBooking.setPaidAmount(0.0);
        saveBooking.setPaidStatus(Const.STATUS_DEACTIVE);
        saveBooking.setProcessStatus(Const.PROCESSING_PENDING);
        saveBooking.setTotalAmount(carrierPrice.getAmount());
        saveBooking.setVehicleNo(map.get("v_number"));
        saveBooking.setBooked_company(company_id);
        saveBooking.setVehicle_model(vehicleModel);
        saveBooking.setVehicle_type(type);
        saveBooking.setCreatedBy(username);
        Booking save = bookingRepository.save(saveBooking);
        boolean matches;
        if (save != null) {
            //        email configuration start *send

            JSONObject object = new JSONObject();
            object.put("name", "thara");
            object.put("email", "test@gmail.com");
            object.put("recieveremail", username.getRecoveryEmail());
            object.put("feedback", "Successfully Apply The Carrier Service. Soon As Possible They Will Call You !");

            String url = "http://localhost:8080/api/feedback";
            HttpPost httpPost = new HttpPost(url);
            HttpClient client = HttpClients.createDefault();
            httpPost.addHeader("content-type", "application/json");
            try {
                httpPost.setEntity(new StringEntity(object.toString()));
                HttpResponse execute = client.execute(httpPost);
            } catch (Exception e) {
                e.printStackTrace();
            }


            JSONObject obj = new JSONObject();
            obj.put("name", "thara");
            obj.put("email", "test@gmail.com");
            obj.put("recieveremail", company_id.getCompanyUser().getRecoveryEmail());
            obj.put("feedback", "You have Carrier Service Booking Immediately !");

            String urlc = "http://localhost:8080/api/feedback";
            HttpPost httpPostc = new HttpPost(urlc);
            HttpClient comp = HttpClients.createDefault();
            httpPostc.addHeader("content-type", "application/json");
            try {
                httpPostc.setEntity(new StringEntity(obj.toString()));
                HttpResponse execute = comp.execute(httpPostc);
            } catch (Exception e) {
                e.printStackTrace();
            }

//        email configuration end
//
//        sms configuration start

            if (save != null) {
                String sms = "Your Reference is <INV" + 105 + ">.\n" ;
                sms += "Pay AUD. " + UtilManager.formatDouble(1000.00) + " \n";

                sms +=   "Deposit to Best Life Acc 005710002195 (Sampath Bank).\n" +
                        "Thank you again for your business.";

                CloseableHttpClient httpclient = HttpClients.createDefault();
                List<NameValuePair> params = new ArrayList<>();
                params.add(new BasicNameValuePair("username", Const.SMS_GATEWAY_USERNAME));
                params.add(new BasicNameValuePair("password", Const.SMS_GATEWAY_PASSWORD));
                params.add(new BasicNameValuePair("src", Const.SMS_GATEWAY_SRC));
                params.add(new BasicNameValuePair("dst", "0714281772"));
                params.add(new BasicNameValuePair("msg", sms));
                params.add(new BasicNameValuePair("dr", "0"));

                String paramString = URLEncodedUtils.format(params,"utf-8");
                HttpGet get = new HttpGet("http://sms.airtel.lk:5000/sms/send_sms.php?" + paramString);
                try {
                    HttpResponse execute = httpclient.execute(get);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
//        sms configuration end

            matches = true;
        } else {
            matches = false;
        }


        JSONObject object = new JSONObject();
        if (matches == true) {
            object.put("message", "true");
        } else {
            object.put("message", "false");
        }
        return object;
    }

    @RequestMapping(value = "/all_field_company/{id}")
    public Object fieldCompany(@PathVariable String id) {
        Location location = new Location();
        location.setId(Integer.parseInt(id));
        List<Object> arrayList = new ArrayList<>();
        List<Company> companyList = companyService.findAllByCompanyLocationAndServiceTypes(location, Const.FIELD_OFFICER);

        for (Company list : companyList) {
            JSONObject object = new JSONObject();
            object.put("id", list.getId());
            object.put("name", list.getCompanyName());
            object.put("limit", list.getBookingLimit());
            object.put("location", list.getCompanyLocation().getLocationName());
            object.put("start", list.getStartTime());
            object.put("end", list.getEndTime());
            object.put("type", list.getCompanyType().getType());
            arrayList.add(object);
        }

        return arrayList;
    }

    @RequestMapping(value = "/field_booking")
    public Object saveFieldBookingApi(@RequestBody Map<String, String> map) {


        Booking saveBooking = new Booking();


        VehicleModel vehicleModel = new VehicleModel();
        vehicleModel.setId(Integer.parseInt(map.get("v_model")));
        VehicleType type = new VehicleType();
        type.setId(Integer.parseInt(map.get("v_type")));
        Company company_id = companyService.findByCompanyId(Integer.parseInt(map.get("company_id")));

        ServiceType carrier = typeRepository.findByType(Const.CARRIER_SERVICE);

        PriceList carrierPrice = priceListService.getPriceByServiceType(carrier);

        String user = map.get("user");
        User username = userService.findByUsername(user);

        saveBooking.setAppointedDate(new Date());
        saveBooking.setBookingType(Const.CARRIER_SERVICE);
        saveBooking.setCancelStatus(Const.DELETED_FALSE);
        saveBooking.setDescription(map.get("description"));
        saveBooking.setPaidAmount(0.0);
        saveBooking.setPaidStatus(Const.STATUS_DEACTIVE);
        saveBooking.setProcessStatus(Const.PROCESSING_PENDING);
        saveBooking.setTotalAmount(carrierPrice.getAmount());
        saveBooking.setVehicleNo(map.get("v_number"));
        saveBooking.setBooked_company(company_id);
        saveBooking.setVehicle_model(vehicleModel);
        saveBooking.setVehicle_type(type);
        saveBooking.setCreatedBy(username);
        Booking save = bookingRepository.save(saveBooking);
        boolean matches;
        if (save != null) {
            //        email configuration start *send

            JSONObject object = new JSONObject();
            object.put("name", "thara");
            object.put("email", "test@gmail.com");
            object.put("recieveremail", username.getRecoveryEmail());
            object.put("feedback", "Successfully Apply The Carrier Service. Soon As Possible They Will Call You !");

            String url = "http://localhost:8080/api/feedback";
            HttpPost httpPost = new HttpPost(url);
            HttpClient client = HttpClients.createDefault();
            httpPost.addHeader("content-type", "application/json");
            try {
                httpPost.setEntity(new StringEntity(object.toString()));
                HttpResponse execute = client.execute(httpPost);
            } catch (Exception e) {
                e.printStackTrace();
            }


            JSONObject obj = new JSONObject();
            obj.put("name", "thara");
            obj.put("email", "test@gmail.com");
            obj.put("recieveremail", company_id.getCompanyUser().getRecoveryEmail());
            obj.put("feedback", "You have Carrier Service Booking Immediately !");

            String urlc = "http://localhost:8080/api/feedback";
            HttpPost httpPostc = new HttpPost(urlc);
            HttpClient comp = HttpClients.createDefault();
            httpPostc.addHeader("content-type", "application/json");
            try {
                httpPostc.setEntity(new StringEntity(obj.toString()));
                HttpResponse execute = comp.execute(httpPostc);
            } catch (Exception e) {
                e.printStackTrace();
            }

//        email configuration end
//
//        sms configuration start

            if (save != null) {
                String sms = "Your Reference is <INV" + 105 + ">.\n" ;
                sms += "Pay AUD. " + UtilManager.formatDouble(1000.00) + " \n";

                sms +=   "Deposit to Best Life Acc 005710002195 (Sampath Bank).\n" +
                        "Thank you again for your business.";

                CloseableHttpClient httpclient = HttpClients.createDefault();
                List<NameValuePair> params = new ArrayList<>();
                params.add(new BasicNameValuePair("username", Const.SMS_GATEWAY_USERNAME));
                params.add(new BasicNameValuePair("password", Const.SMS_GATEWAY_PASSWORD));
                params.add(new BasicNameValuePair("src", Const.SMS_GATEWAY_SRC));
                params.add(new BasicNameValuePair("dst", "0714281772"));
                params.add(new BasicNameValuePair("msg", sms));
                params.add(new BasicNameValuePair("dr", "0"));

                String paramString = URLEncodedUtils.format(params,"utf-8");
                HttpGet get = new HttpGet("http://sms.airtel.lk:5000/sms/send_sms.php?" + paramString);
                try {
                    HttpResponse execute = httpclient.execute(get);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
//        sms configuration end

            matches = true;
        } else {
            matches = false;
        }


        JSONObject object = new JSONObject();
        if (matches == true) {
            object.put("message", "true");
        } else {
            object.put("message", "false");
        }
        return object;
    }


}
