package com.ttn.reap.controller;

import com.ttn.reap.DTO.LoggedInUserDetails;
import com.ttn.reap.entity.Employee;
import com.ttn.reap.entity.Item;
import com.ttn.reap.exception.EmployeeException;
import com.ttn.reap.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/reap")
public class SignupController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private HttpServletRequest request;

    @RequestMapping("/register")
    public String registerEmployee(Model model,HttpSession session) {
        LoggedInUserDetails loggedInUserDetails = (LoggedInUserDetails) session.getAttribute("loggedInUser");
        if (loggedInUserDetails != null)
            return "redirect:/reap/dashboard";
        model.addAttribute("employee", new Employee());
        return "signup";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String processRegistration(@Valid @ModelAttribute("employee") Employee employee, @RequestParam("profilePic") MultipartFile profilePic,
                                      RedirectAttributes redirectAttributes, HttpSession session, BindingResult bindingResult) throws IOException {

        try {
            if (bindingResult.hasErrors()) {
                System.out.println("ERROR ERROR ERROR");
            } else if (employeeService.saveEmployee(employee, profilePic) == null) {
                redirectAttributes.addFlashAttribute("isRegistered", false);
            }
        } catch (EmployeeException e) {
            redirectAttributes.addFlashAttribute("isRegistered", false);
            redirectAttributes.addFlashAttribute("exception", e);
            return "redirect:register";
        }
        List<Item> itemList = new ArrayList<>();
        session.setAttribute("itemList", itemList);
        session.setAttribute("currentCartTotal", 0);


        return "successfulsignup";
    }
}
