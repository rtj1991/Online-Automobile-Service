package com.online.automobile.controller;

import com.online.automobile.dto.Information;
import com.online.automobile.model.User;
import com.online.automobile.service.user.UserService;
import com.online.automobile.util.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/profile")
    public String loadCreateUser(HttpServletRequest request, Model model) {
        Information info = new Information();
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");

        User username = userService.findUserById(user.getId());

        info.setEmail(username.getUserName());
        info.setFullName(username.getFullName());
        info.setLastName(username.getLastName());
        info.setNic(username.getNic());
        info.setUsermobile(username.getRecoveryPhone());
        info.setCompanyEmail(username.getRecoveryEmail());
        info.setImageUrl(username.getImgUrl());
        System.out.println("info---------> "+info.getImageUrl());
        model.addAttribute("info", info);

//        model.addAttribute("user",user);
        return "system/user/user_profile";
    }

    @PostMapping("/updateUser")
    public String updateUser(@Valid @ModelAttribute Information info, BindingResult result, RedirectAttributes attributes,HttpServletRequest request) throws Exception {
        User user = (User)request.getSession().getAttribute("user");
        try {
            User username = userService.findByUsername(user.getUserName());
            if (info != null) {

                if (result.hasErrors()) {
                    attributes.addFlashAttribute("org.springframework.validation.BindingResult.info", result);
                    attributes.addFlashAttribute("info", info);
                    attributes.addFlashAttribute("username", username);
                    return "redirect:/profile";
                } else {
                    userService.updateUser(info,user);
                    attributes.addFlashAttribute("info", info);
                    return "redirect:/dashboard/"+Const.PROCESS_COMPLETE;
                }
            } else {
                if (result.hasErrors()) {
                    attributes.addFlashAttribute("org.springframework.validation.BindingResult.info", result);
                    attributes.addFlashAttribute("info", info);
                    attributes.addFlashAttribute("username", username);
                    return "redirect:/profile";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }
}
