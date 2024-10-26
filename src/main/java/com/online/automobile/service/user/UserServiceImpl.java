package com.online.automobile.service.user;

import com.online.automobile.dto.Information;
import com.online.automobile.model.*;
import com.online.automobile.repository.*;
import com.online.automobile.service.common.CommonService;
import com.online.automobile.service.location.LocationService;
import com.online.automobile.util.Const;
import com.online.automobile.util.UtilManager;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ModuleRepository moduleRepository;

    @Override
    public boolean hasAdmin() {
        return userRepository.findByUserName("root") != null;
    }

    @Override
    public User createUser(Information info, MultipartFile attachment) {
        try {
            Company company = new Company();

            User user = new User();
            user.setFullName(info.getFullName());
            user.setLastName(info.getLastName());
            user.setNic(info.getNic());
            if (hasAdmin()) {
                user.setUserName(info.getEmail());
            } else {
                user.setUserName("root");
            }
            user.setEnabled(true);
            user.setRecoveryPhone(info.getUsermobile());
            user.setRecoveryEmail(info.getEmail());

            user.setPassword(passwordEncoder.encode(info.getPassword()));
            User users = userRepository.findByUserName("root");

            if (users != null && (info.getBusinessModel() == Const.VEHICLE_SERVICE || info.getBusinessModel() == Const.GARAGE)) {

                CompanyType companyType = new CompanyType();
                companyType.setId(info.getBusinessModel());

                company.setCompanyName(info.getCompanyName());
                company.setAddress(info.getAddress());
                company.setCompanyType(companyType);
                company.setStartTime(UtilManager.formatTime(info.getStartTime()));
                company.setEndTime(UtilManager.formatTime(info.getEndTime()));
                company.setEmail(info.getCompanyEmail());
                company.setPhone(info.getTelephone());
                company.setCompanyUser(user);
                company.setBookingLimit(Const.BOOKING_LIMIT);
                if (info.getLocation() != null) {
                    Location location = locationRepository.findByIdAndStatus(Integer.parseInt(info.getLocation()), Const.STATUS_ACTIVE);
                    user.setLocation(location);
                    company.setCompanyLocation(location);
                }
                user.setUserType(info.getBusinessModel());
                List<Module> modules = moduleRepository.findAllByStatusRedirect(Const.STATUS_DEACTIVE);
                Role role_admin = roleRepository.findByRole("ROLE_OWNER");
                user.setRoles(Arrays.asList(role_admin));
                role_admin.setModules(modules);
                String saveFile = saveFile(attachment, user);
                user.setImgUrl("/images/upload/profile/"+saveFile);

                userRepository.save(user);
                companyRepository.save(company);


                Supplier supplier = new Supplier();
                supplier.setName(info.getFullName());
                supplier.setAddress(info.getAddress());
                supplier.setCompanyId(company);
                supplier.setContactNumber(info.getTelephone());
                supplier.setNic(info.getNic());
                supplier.setEmail(info.getEmail());
                supplier.setuId(user);
                supplier.setStatus(Const.STATUS_ACTIVE);
                supplierRepository.save(supplier);
            } else if (users != null && info.getBusinessModel() == Const.VEHICLE_OWNER) {

                List<Module> module = moduleRepository.findByModule("Booking");
                Role owner = roleRepository.findByRole("ROLE_CUSTOMER");
                user.setRoles(Arrays.asList(owner));
                owner.setModules(module);

                String saveFile = saveFile(attachment, user);
                user.setImgUrl("/images/upload/profile/"+saveFile);

                user.setUserType(Const.VEHICLE_OWNER);
                userRepository.save(user);
                Customer customer = new Customer();
                customer.setName(info.getFullName());
                customer.setContactNumber(info.getUsermobile());
                customer.setNic(info.getNic());
                customer.setEmail(info.getEmail());
                customer.setuId(user);
                customer.setStatus(Const.STATUS_ACTIVE);
                customerRepository.save(customer);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void isloggedIn(boolean b, User userSession, HttpServletRequest request) {

    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    private String saveFile(MultipartFile file, User user) throws IOException {
        String fileName = "";
        String fileExtension = "";
        String home = System.getProperty("user.home");
        String PRODUCT_PROFILE = home + "/Dropbox/OnlineAutomobileService/my_bit_project/src/main/resources/static/images/upload/profile";
//        /Dropbox/OnlineAutomobileService/my_bit_project/src/main/resources/static/images
        if (!file.isEmpty()) {

            File folder = new File(PRODUCT_PROFILE);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            fileName = file.getOriginalFilename();
            fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
//            fileName = UtilManager.md5(user.getNic()) + "." + fileExtension;
            fileName = user.getNic() + "." + fileExtension;
            byte[] bytes = file.getBytes();
            Path path = Paths.get(PRODUCT_PROFILE + "/" + fileName);
            Files.write(path, bytes);
            return fileName;
        }
        return null;
    }

    @Override
    public User findByUsername() {
        return userRepository.findByUserName("root");
    }

    @Override
    public List<User> findByLocation(Location location) {
        return userRepository.findAllByLocation(location);
    }

    @Override
    public User createRootUser(Information info) {
        try {
            User user = new User();
            user.setFullName("root");
            user.setLastName("root");
            user.setNic("root");
            user.setUserName("root");
            user.setEnabled(true);
            user.setRecoveryPhone("root");
            user.setUserType(Const.ADMIN);
            user.setRecoveryEmail(info.getEmail());

            user.setPassword(passwordEncoder.encode(info.getPassword()));
            List<Module> allByStatusActive = moduleRepository.findAllByStatusActive(Const.STATUS_ACTIVE);
            Role role_admin = roleRepository.findByRole("ROLE_ADMIN");
            user.setRoles(Arrays.asList(role_admin));

            role_admin.setModules(allByStatusActive);
            userRepository.save(user);
            roleRepository.save(role_admin);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User findUserByNic(String nic) {
        return userRepository.findByNic(nic);
    }

    @Override
    public User findUserById(Integer user) {
        return userRepository.findById(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUserNameAndUserType(username,Const.VEHICLE_OWNER);
    }

    @Override
    public User updateUser(Information info,User user) {
        User updateUser = userRepository.findById(user.getId());
        String userName = updateUser.getUserName();
        if (!userName.equals(info.getEmail())){
            updateUser.setUserName(info.getEmail());


            JSONObject object = new JSONObject();
            object.put("name", "thara");
            object.put("email", "test@gmail.com");
            object.put("recieveremail", user.getRecoveryEmail());
            object.put("feedback", "Dear Customer, Your Have successfully Change Your Username !\n YOur Username id :"+info.getEmail());

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

        }
        updateUser.setFullName(info.getFullName());
        updateUser.setLastName(info.getLastName());
        updateUser.setNic(info.getNic());
        updateUser.setRecoveryPhone(info.getUsermobile());
        updateUser.setRecoveryEmail(info.getCompanyEmail());
        User save = userRepository.save(updateUser);
        System.out.println("user--------->"+save);
        return save;
    }
}
