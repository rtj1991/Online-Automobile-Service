package com.online.automobile.service.user;

import com.online.automobile.dto.Information;
import com.online.automobile.model.Location;
import com.online.automobile.model.User;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserService {

    boolean hasAdmin();

    User createUser(Information info,MultipartFile attachment);

    void isloggedIn(boolean b, User userSession, HttpServletRequest request);

    User saveUser(User user);

    User findByUsername();

    List<User> findByLocation(Location location);

    User createRootUser(Information info);

    User findUserByNic(String nic);

    User findUserById(Integer user);

    User findByUsername(String username);

    User updateUser(Information info,User user);

}
