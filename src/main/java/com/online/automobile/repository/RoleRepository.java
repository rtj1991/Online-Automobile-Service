package com.online.automobile.repository;

import com.online.automobile.model.Role;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends PagingAndSortingRepository<Role, Integer> {
    public Role findByRole(String role);
}
