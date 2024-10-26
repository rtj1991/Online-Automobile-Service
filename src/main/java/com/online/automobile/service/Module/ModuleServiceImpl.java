package com.online.automobile.service.Module;

import com.online.automobile.model.Module;
import com.online.automobile.model.Role;
import com.online.automobile.repository.ModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModuleServiceImpl implements ModuleService {

    @Autowired
    private ModuleRepository moduleRepository;

    @Override
    public List<Module> userHasModules(List<Role> roles, Module module, int visibility) throws Exception {
        return moduleRepository.findAllByRolesIsInAndParentNotNullAndParentAndStatusVisibility(roles, module, visibility);
    }

    @Override
    public Module create(String faIcon, String moduleName, String route, Integer statusActive, Integer statusRedirect, Integer statusVisibility, Module parent) throws Exception {
        Module module = new Module();
        module.setFaIcon(faIcon);
        module.setModule(moduleName);
        module.setRoute(route);
        module.setStatusActive(statusActive);
        module.setStatusRedirect(statusRedirect);
        module.setStatusVisibility(statusVisibility);
        module.setParent(parent);
        return moduleRepository.save(module);
    }
}
