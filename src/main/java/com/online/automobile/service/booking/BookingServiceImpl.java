package com.online.automobile.service.booking;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.online.automobile.dto.BookingDetail;
import com.online.automobile.model.*;
import com.online.automobile.repository.BookingDetailRepository;
import com.online.automobile.repository.BookingRepository;
import com.online.automobile.repository.CompanyRepository;
import com.online.automobile.repository.PriceListRepository;
import com.online.automobile.service.company.CompanyService;
import com.online.automobile.util.Const;
import com.online.automobile.util.UtilManager;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private BookingDetailRepository detailRepository;

    @Autowired
    private PriceListRepository priceListRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public Booking CreateBooking(BookingDetail detail, Map<String, String> map, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        System.out.println("user-------> "+user);
        double total = Double.parseDouble(map.get("total"));
        Company company = new Company();
        Booking booking = new Booking();
        if (detail.getSearchDate() != null && !detail.getSearchDate().isEmpty()) {
            String date = detail.getSearchDate();
            Date adate = UtilManager.formatDate1(date);
            booking.setAppointedDate(adate);
        } else {
            Date formatDate = UtilManager.formatDate1(UtilManager.formatDate(new Date()));
            booking.setAppointedDate(formatDate);
        }

        company.setId(detail.getCompany().getId());
        booking.setVehicleNo(detail.getVehicleNo());
        booking.setDescription(detail.getDescription());
        booking.setBooked_company(company);
        booking.setVehicle_model(detail.getModel());
        booking.setVehicle_type(detail.getvType());
        booking.setBookingType(Const.PRIMARY_TYPE);
        booking.setTotalAmount(total);
        booking.setCreatedBy(user);
        booking.setProcessStatus(Const.PROCESSING_PENDING);
        Booking savebooking = bookingRepository.save(booking);
        JsonParser parser = new JsonParser();
        String cart = map.get("carts");
        JsonArray parse = (JsonArray) parser.parse(cart);

        for (JsonElement item : parse) {
            ServiceType type = new ServiceType();
            type.setId(item.getAsJsonObject().get("id").getAsInt());
            BookingDetails bookingDetails = new BookingDetails();
            bookingDetails.setBooking(booking);
            bookingDetails.setServiceType(type);
            bookingDetails.setAmount(item.getAsJsonObject().get("price").getAsDouble());
            bookingDetails.setDeleted(Const.DELETED_FALSE);
            detailRepository.save(bookingDetails);
        }

//        email configuration start *send

        JSONObject object = new JSONObject();
        object.put("name", "thara");
        object.put("email", "test@gmail.com");
        object.put("recieveremail", "test1@gmail.com");
        object.put("feedback", "Successfully Add The Booking. Your Booking No is "+booking.getId()+"\n"+"Appointed Date "+UtilManager.formatTimestampDate(booking.getAppointedDate()));

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

        if (savebooking != null) {
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
//        sms configuration end
        return savebooking;
    }

    @Override
    public List<Booking> getAllBookingBy(User user, int type,int status) {
        return bookingRepository.findAllByCreatedByAndBookingTypeAndProcessStatusNot(user, type,status);
    }

    @Override
    public List<Booking> getAllBookingByDate(Company company, Date currentFDate, Date currentTDate) {

        return bookingRepository.findAllByBookedCompanyAndAppointedDateGreaterThanEqualAndAppointedDateLessThanEqualAndCancelStatus(company, currentFDate, currentTDate, Const.PROCESSING_CANCEL);
    }

    @Override
    public List<Booking> getAllBookingByCompany(Company company, int type,int status) {
        return bookingRepository.findAllByBookedCompanyAndBookingType(company, type);
    }

    @Override
    public List<Booking> getAllBooking() {
        return bookingRepository.findAll();
    }

    @Override
    public Booking getBookingById(int id) {
        return bookingRepository.findOne(id);
    }

    @Override
    public Booking updateBooking(BookingDetail detail, Map<String, String> map) {
        String id = map.get("bookId");

        Booking booking = bookingRepository.findOne(Integer.parseInt(id));
        booking.setVehicleNo(detail.getVehicleNo());
        booking.setDescription(detail.getDescription());
        booking.setVehicle_model(detail.getModel());
        booking.setVehicle_type(detail.getvType());
        booking.setTotalAmount(Double.parseDouble(map.get("total")));
        if (detail.getSearchDate() != null && !detail.getSearchDate().isEmpty()) {
            String date = detail.getSearchDate();
            Date adate = UtilManager.formatDate1(date);
            booking.setAppointedDate(adate);
        } else {
            Date formatDate = UtilManager.formatDate1(UtilManager.formatDate(new Date()));
            booking.setAppointedDate(formatDate);
        }

        Booking savebooking = bookingRepository.save(booking);

        JsonParser parser = new JsonParser();
        String cart = map.get("carts");
        JsonArray parse = (JsonArray) parser.parse(cart);
        List<BookingDetails> allByBooking = detailRepository.findAllByBookingAndDeleted(booking, Const.DELETED_FALSE);
        if (cart.length() != 0) {
            for (BookingDetails list : allByBooking) {
                list.setDeleted(Const.DELETED_TRUE);
                detailRepository.save(list);
            }

            for (JsonElement item : parse) {
                ServiceType type = new ServiceType();
                type.setId(item.getAsJsonObject().get("id").getAsInt());
                BookingDetails bookingDetails = new BookingDetails();
                bookingDetails.setBooking(booking);
                bookingDetails.setServiceType(type);
                bookingDetails.setAmount(item.getAsJsonObject().get("price").getAsDouble());
                detailRepository.save(bookingDetails);
            }
        }


        return savebooking;
    }

    @Override
    public Booking CreateCarrierServiceBooking(BookingDetail detail, HttpServletRequest request) {

        User user = (User) request.getSession().getAttribute("user");

        Company company = new Company();
        PriceList price = priceListRepository.findByServiceTypeType(Const.CARRIER_SERVICE);
        Date formatDate = UtilManager.formatDate1(UtilManager.formatDate(new Date()));

        Booking booking = new Booking();
        company.setId(detail.getCompany().getId());
        booking.setAppointedDate(formatDate);
        booking.setVehicleNo(detail.getVehicleNo());
        booking.setDescription(detail.getDescription());
        booking.setBooked_company(company);
        booking.setVehicle_model(detail.getModel());
        booking.setVehicle_type(detail.getvType());
        booking.setCreatedBy(user);
        booking.setBookingType(Const.CARRIER_SERVICE);
        booking.setTotalAmount(price.getAmount());
        booking.setProcessStatus(Const.PROCESSING_PENDING);
        Booking save = bookingRepository.save(booking);

        Company byId = companyRepository.findById(detail.getCompany().getId());
        if (save != null) {
//        email configuration start *send

//            customer
            JSONObject object = new JSONObject();
            object.put("name", "thara");
            object.put("email", "test@gmail.com");
            object.put("recieveremail", user.getRecoveryEmail());
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


//            service owner
            JSONObject obj = new JSONObject();
            obj.put("name", "thara");
            obj.put("email", "test@gmail.com");
            obj.put("recieveremail", byId.getCompanyUser().getRecoveryEmail());
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
        }

        return booking;
    }

    @Override
    public Booking CreateFieldOfficerBooking(BookingDetail detail, HttpServletRequest request) {

        User user = (User) request.getSession().getAttribute("user");

        Company company = new Company();
        PriceList price = priceListRepository.findByServiceTypeType(Const.CARRIER_SERVICE);
        Date formatDate = UtilManager.formatDate1(UtilManager.formatDate(new Date()));

        Booking booking = new Booking();
        company.setId(detail.getCompany().getId());
        booking.setAppointedDate(formatDate);
        booking.setVehicleNo(detail.getVehicleNo());
        booking.setDescription(detail.getDescription());
        booking.setBooked_company(company);
        booking.setVehicle_model(detail.getModel());
        booking.setVehicle_type(detail.getvType());
        booking.setCreatedBy(user);
        booking.setBookingType(Const.FIELD_OFFICER);
        booking.setTotalAmount(price.getAmount());
        booking.setProcessStatus(Const.PROCESSING_PENDING);
        Booking save = bookingRepository.save(booking);

        Company byId = companyRepository.findById(detail.getCompany().getId());

        if (save != null) {

//            email configuration start

            JSONObject object = new JSONObject();
            object.put("name", "thara");
            object.put("email", "test@gmail.com");
            object.put("recieveremail", user.getRecoveryEmail());
            object.put("feedback", "Successfully Apply The Field Officer. Soon As Possible They Will Call You !");

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
//
//
            JSONObject obj = new JSONObject();
            obj.put("name", "thara");
            obj.put("email", "test@gmail.com");
            obj.put("recieveremail", byId.getCompanyUser().getRecoveryEmail());
            obj.put("feedback", "You have Field Officer Booking Immediately !");

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
            //       email configuration end

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
        }

        return booking;
    }

    @Override
    public List<Booking> getAllBookingBy(Company company, int status) {
        return bookingRepository.findAllByBookedCompanyAndProcessStatus(company,status);
    }

    @Override
    public List<Booking> getAllByUser(User user) {
        return bookingRepository.findAllByCreatedBy(user);
    }

    @Override
    public List<Booking> getAllByCompanyAndUser(Company company, User user) {
        return bookingRepository.findAllByBookedCompanyAndcAndCreatedBy(company,user);
    }
}
