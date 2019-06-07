package com.ttn.reap.controller;

import com.ttn.reap.DTO.LoggedInUserDetails;
import com.ttn.reap.entity.Employee;
import com.ttn.reap.entity.Item;
import com.ttn.reap.entity.OrderHistory;
import com.ttn.reap.exception.EmployeeException;
import com.ttn.reap.service.EmployeeService;
import com.ttn.reap.service.ItemService;
import com.ttn.reap.service.OrderHistoryService;
import com.ttn.reap.service.RecognitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping("/reap")
public class CartOrderController {

    @Autowired
    EmployeeService userService;

    @Autowired
    RecognitionService recognitionService;

    @Autowired
    OrderHistoryService orderSummaryService;

    @Autowired
    ItemService itemService;


    @GetMapping("/cart/{id}")
    public ModelAndView getUserCart(@PathVariable("id") Integer id,
                                    HttpServletRequest httpServletRequest,
                                    RedirectAttributes redirectAttributes) throws Exception{
        HttpSession httpSession = httpServletRequest.getSession();
        LoggedInUserDetails loggedInUser = (LoggedInUserDetails) httpSession.getAttribute("loggedInUser");
      
        try {
            if (id != loggedInUser.getId()) {
                ModelAndView modelAndView = new ModelAndView("redirect:/reap/login");
                redirectAttributes.addFlashAttribute("error", "Please log in to view your cart");
                return modelAndView;
            }
        } catch (NullPointerException ne) {
            ModelAndView modelAndView = new ModelAndView("redirect:/reap/login");
            redirectAttributes.addFlashAttribute("error", "Please log in to view your cart");
            return modelAndView;
        }
        Optional<Employee> optionalUser = userService.findEmployeeById(id);
        if (!optionalUser.isPresent()) {
            throw new EmployeeException("No user with id " + id);
        }
        ModelAndView modelAndView = new ModelAndView("cart");
        modelAndView.addObject("loggedInUser", optionalUser.get());
        List<Item> itemList = (List<Item>) httpSession.getAttribute("itemList");
        modelAndView.addObject("itemList", itemList);
        Integer currentCartTotal = (Integer) httpSession.getAttribute("currentCartTotal");
        modelAndView.addObject("currentCartTotal", currentCartTotal);
        return modelAndView;
    }
    // Show user order history
    @GetMapping("/orders/{id}")
    public ModelAndView getOrderHistory(@PathVariable("id") Integer id,
                                        HttpServletRequest httpServletRequest,
                                        RedirectAttributes redirectAttributes) throws EmployeeException {
        HttpSession httpSession = httpServletRequest.getSession();
        LoggedInUserDetails activeUser = (LoggedInUserDetails) httpSession.getAttribute("loggedInUser");
        try {
            if (id != activeUser.getId()) {
                ModelAndView modelAndView = new ModelAndView("redirect:/");
                redirectAttributes.addFlashAttribute("error", "Please log in to view your order history");
                return modelAndView;
            }
        } catch (NullPointerException ne) {
            ModelAndView modelAndView = new ModelAndView("redirect:/");
            redirectAttributes.addFlashAttribute("error", "Please log in to view your order history");
            return modelAndView;
        }
        Optional<Employee> optionalUser = userService.findEmployeeById(id);
        if (!optionalUser.isPresent()) {
            throw new EmployeeException("No user with id " + id);
        }
        ModelAndView modelAndView = new ModelAndView("order-history");
        modelAndView.addObject("loggedInUser", activeUser);

        List<OrderHistory> orderSummaryList = orderSummaryService.getAllOrdersByUserId(activeUser.getId());
        modelAndView.addObject("orderSummaryList", orderSummaryList);
        List<Integer> itemIdList = new ArrayList<>();
        Set<Item> itemSetInOrderSummaryList = new LinkedHashSet<>();
        for (OrderHistory orderSummary : orderSummaryList) {
            itemIdList = orderSummary.getItemIdsInOrderSummary();
            for (Integer itemId : itemIdList) {
                itemSetInOrderSummaryList.add(itemService.findItemById(itemId).get());
            }
        }
        modelAndView.addObject("itemSetInOrderSummaryList", itemSetInOrderSummaryList);
        return modelAndView;
    }

}
