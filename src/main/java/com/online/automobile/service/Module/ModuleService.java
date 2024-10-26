package com.online.automobile.service.Module;

import com.online.automobile.model.Module;
import com.online.automobile.model.Role;

import java.util.List;

public interface ModuleService {
    List<Module> userHasModules(List<Role> roles, Module module, int visibility) throws Exception;

    Module create(String faIcon, String module, String route, Integer statusActive,
                  Integer statusRedirect, Integer statusVisibility, Module parent) throws Exception;
}
