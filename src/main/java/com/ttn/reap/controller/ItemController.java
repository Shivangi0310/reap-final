package com.ttn.reap.controller;

import com.ttn.reap.DTO.LoggedInUserDetails;
import com.ttn.reap.entity.Employee;
import com.ttn.reap.entity.Item;
import com.ttn.reap.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/reap")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping("/items")
    public ModelAndView getItemsPage(RedirectAttributes redirectAttributes, HttpServletRequest request) {
        HttpSession httpSession = request.getSession();

        LoggedInUserDetails loggedInUser = (LoggedInUserDetails) httpSession.getAttribute("loggedInUser");


        if (loggedInUser == null) {
            ModelAndView modelAndView = new ModelAndView("redirect:/");
            redirectAttributes.addFlashAttribute("error", "Please log in to view redeemable items");
            return modelAndView;
        }
        ModelAndView modelAndView = new ModelAndView("Item");
        List<Item> itemList = itemService.findAll();
        modelAndView.addObject("itemList", itemList);
//        httpSession.setAttribute("itemList",itemList);
        modelAndView.addObject("loggedInUser", loggedInUser);
        return modelAndView;
    }

}
