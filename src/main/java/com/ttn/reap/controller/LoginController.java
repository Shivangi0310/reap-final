package com.ttn.reap.controller;

import com.ttn.reap.DTO.LoggedInUserDetails;
import com.ttn.reap.entity.Employee;
import com.ttn.reap.entity.Item;
import com.ttn.reap.enums.ExceptionStatus;
import com.ttn.reap.enums.Role;
import com.ttn.reap.exception.EmployeeException;
import com.ttn.reap.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/reap")
public class LoginController {

    @Autowired
    private EmployeeService employeeService;

    @RequestMapping("/login")
    public String goToLogin(Model model, HttpSession session) {
        LoggedInUserDetails loggedInUserDetails = (LoggedInUserDetails) session.getAttribute("loggedInUser");
        if (loggedInUserDetails != null)
            return "redirect:/reap/dashboard";
        model.addAttribute("loggedInUser", new LoggedInUserDetails());
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String verifyCredentials(@Valid @ModelAttribute("loggedInUser") Employee loggedUser,
                                    HttpSession session, RedirectAttributes redirectAttributes, BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()) {
            System.out.println("ERROR ERROR ERROR");
            return "login";
        }
//        if (loggedUser.getActive().booleanValue() == false) {
//            redirectAttributes.addFlashAttribute("isActive", false);
//            return "redirect:login";
//        }
        try {
            Employee employee = employeeService.loginEmployee(loggedUser.getEmail(), loggedUser.getPassword());
            LoggedInUserDetails loggedInUser = new LoggedInUserDetails();
            loggedInUser.setEmail(employee.getEmail());
            loggedInUser.setId(employee.getId());
            loggedInUser.setFirstName(employee.getFirstName());
            loggedInUser.setLastName(employee.getLastName());
            loggedInUser.setProfilePhoto(employee.getProfilePhoto());
            loggedInUser.setGoldBadgeCount(employee.getGoldBadgeCount());
            loggedInUser.setSilverBadgeCount(employee.getSilverBadgeCount());
            loggedInUser.setBronzeBadgeCount(employee.getBronzeBadgeCount());
            loggedInUser.setNoOfGoldBadgeEarned(employee.getNoOfGoldBadgeEarned());
            loggedInUser.setNoOfSilverBadgeEarned(employee.getNoOfSilverBadgeEarned());
            loggedInUser.setNoOfBronzeBadgeEarned(employee.getNoOfBronzeBadgeEarned());
            loggedInUser.setPoints(employee.getPoints());
            loggedInUser.setPassword(employee.getPassword());
            List<Item> itemList = new ArrayList<>();
            session.setAttribute("itemList", itemList);
            session.setAttribute("currentCartTotal", 0);


            if (employee.getRoleSet().contains(Role.ADMIN) == true) {
                loggedInUser.setAdmin(true);
            } else
                loggedInUser.setAdmin(false);

            session.setAttribute("loggedInUser", loggedInUser);
            return "redirect:/reap/dashboard";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("isValidCredentilas", false);

            if (e instanceof EmployeeException) {
                redirectAttributes.addFlashAttribute("exception", e);
            } else {
                redirectAttributes.addFlashAttribute("exception", new Exception("Can Not login Try again"));
            }
            return "redirect:login";
        }
    }


// used interceptor so no need of this
//    @PostMapping("/logout")
//    public ModelAndView logUserOut(HttpServletRequest httpServletRequest) {
//        HttpSession httpSession = httpServletRequest.getSession();
//        httpSession.invalidate();
//        return new ModelAndView("redirect:login");
//    }

}
