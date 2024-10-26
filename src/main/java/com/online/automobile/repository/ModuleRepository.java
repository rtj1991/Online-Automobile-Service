package com.online.automobile.repository;

import com.online.automobile.model.Module;
import com.online.automobile.model.Role;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;

import java.util.List;

@Repository
public interface ModuleRepository extends PagingAndSortingRepository<Module, Integer> {
    List<Module> findAllByRolesIsInAndParentNotNullAndParentAndStatusVisibility(List<Role> roles, Module module, int visibility);

    List<Module>findAllByStatusActive(int status);

    List<Module> findById(int id);

    List<Module> findByModule(String module);

    List<Module> findAllByStatusRedirect(int status);
}
