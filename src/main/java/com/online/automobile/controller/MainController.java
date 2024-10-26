package com.online.automobile.controller;

import com.online.automobile.dto.Information;
import com.online.automobile.model.*;
import com.online.automobile.service.Module.ModuleService;
import com.online.automobile.service.common.CommonService;
import com.online.automobile.service.company.CompanyService;
import com.online.automobile.service.location.LocationService;
import com.online.automobile.service.user.UserService;
import com.online.automobile.util.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class MainController {
    @Autowired
    private CommonService commonService;

    @Autowired
    private UserService userService;

    @Autowired
    private ModuleService moduleService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private CompanyService companyService;

    @PostMapping("/createRootUser")
    public String createRootUser(@Valid @ModelAttribute Information info, RedirectAttributes attributes) throws Exception {

        if (!userService.hasAdmin()) {
            commonService.init(info);
        }
        userService.createRootUser(info);
        attributes.addFlashAttribute("info", info);
        return "redirect:/login";
    }

    @PostMapping("/createUser")
    public String createUser(@Valid @ModelAttribute Information info, BindingResult result, RedirectAttributes attributes, Model model, @RequestParam("profilePicture") MultipartFile attachment) throws Exception {

        try {
            User username = userService.findByUsername();
            if (info.getBusinessModel() != null) {
                if (result.hasErrors()) {
                    attributes.addFlashAttribute("org.springframework.validation.BindingResult.info", result);
                    attributes.addFlashAttribute("info", info);
                    attributes.addFlashAttribute("username", username);
                    return "redirect:/register";
                } else {
                    userService.createUser(info, attachment);
                    attributes.addFlashAttribute("info", info);
                    return "redirect:/login";
                }
            } else {
                if (result.hasErrors()) {
                    attributes.addFlashAttribute("org.springframework.validation.BindingResult.info", result);
                    attributes.addFlashAttribute("info", info);
                    attributes.addFlashAttribute("username", username);
                    return "redirect:/register";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }

    @RequestMapping("/register")
    public String register(Model model) {
        User username = userService.findByUsername();
        if (username != null) {
            List<Location> location = locationService.findAllLocation();
            if (location != null) {
                model.addAttribute("location", location);
            } else {
                model.addAttribute("location", "");
            }
            if (!model.containsAttribute("info")) {
                Information info = new Information();
                model.addAttribute("info", info);
            }
            return "admin/config";
        } else {
            if (!model.containsAttribute("info")) {
                Information info = new Information();
                model.addAttribute("info", info);
            }
            return "admin/root_user";
        }

    }

    @RequestMapping("/")
    public String mainPage() {

        return "main_page";
    }

    @RequestMapping(value = {"/dashboard/{id}", "/dashboard"})
    @PreAuthorize("isAuthenticated()")
    public String index(HttpServletRequest servletRequest, Model model, @PathVariable Map<String, String> pathVars) throws Exception {
        User user = (User) servletRequest.getSession().getAttribute("user");
        Module module = new Module();

        int status;

        if (pathVars.containsKey("id") && pathVars.get("id").equals("1")) {
            status = Const.STATUS_ACTIVE;
        } else if (pathVars.containsKey("id") && pathVars.get("id").equals("3")) {
            status = Const.PROCESS_COMPLETE;

        } else {
            status = Const.STATUS_ERROR;
        }


        module.setId(Const.MAIN_PARENT);
        model.addAttribute("status", status);
        model.addAttribute("modules", moduleService.userHasModules(user.getRoles(), module, Const.STATUS_VISIBILITY));
        model.addAttribute("user", user.getUserType());
        if (user.getUserType() == Const.GARAGE || user.getUserType() == Const.VEHICLE_SERVICE) {
            Company byUser = companyService.findByUser(user);
            List<ServiceType> serviceTypes = byUser.getServiceTypes();
            if (serviceTypes.size() == Const.STATUS_DEACTIVE) {
                return "system/user/add_service_type";
            } else {
                return "system/dashboard";
            }
        } else {
            return "system/dashboard";
        }

    }

    @RequestMapping(value = "/login")
    public String loginPage(@ModelAttribute Information info, Model model) {
        User username = userService.findByUsername();
        if (username != null) {
            if (!model.containsAttribute("info")) {
                model.addAttribute("info", info);
            }
            return "login";
        } else {
            if (!model.containsAttribute("info")) {
                Information infor = new Information();
                model.addAttribute("info", infor);
            }
            return "admin/root_user";
        }

    }

    @PostMapping("/saveServiceTypes")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','OWNER')")
    public String saveServiceType(HttpServletRequest servletRequest, @RequestParam Map<String, String> map) {
        User user = (User) servletRequest.getSession().getAttribute("user");
        Company byUser = companyService.findByUser(user);
        Company company = companyService.saveServiceType(byUser, map);
        if (company != null) {
            return "redirect:/dashboard/" + 1;
        } else {
            return "redirect:/saveServiceTypes";
        }
    }

    @RequestMapping(value = "/addNewServiceTypes")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','OWNER')")
    public String addNewServiceTypes() {

        return "system/service/add_new_service_types";
    }

}
