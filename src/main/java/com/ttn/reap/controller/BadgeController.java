package com.ttn.reap.controller;

import com.ttn.reap.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/reap")
public class BadgeController {
    @Autowired
    private EmployeeService employeeService;

    @RequestMapping("/badges")
    public String showBadgesOfEmployee(Model model, HttpSession session) {
        model.addAttribute("loggedInUser",session.getAttribute("loggedInUser"));
        return "Badges";
    }


}
