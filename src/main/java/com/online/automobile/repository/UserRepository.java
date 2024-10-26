package com.online.automobile.repository;

import com.online.automobile.model.Location;
import com.online.automobile.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Integer> {

    User findByUserName(String username);

    List<User> findAllByLocation(Location location);

    User findByNic(String nic);

    User findById(Integer user);

    User findByUserNameAndUserType(String username,int type);
}
