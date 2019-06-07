package com.ttn.reap.controller;

import com.ttn.reap.DTO.LoggedInUserDetails;
import com.ttn.reap.entity.Employee;
import com.ttn.reap.entity.Item;
import com.ttn.reap.entity.OrderHistory;
import com.ttn.reap.service.EmployeeService;
import com.ttn.reap.service.ItemService;
import com.ttn.reap.service.OrderHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

@Controller
public class OrderHistoryController {
    @Autowired
    private ItemService itemService;

    @Autowired
    private OrderHistoryService orderHistoryService;

    @Autowired
    private EmployeeService employeeService;

    // Add an item to user's cart
    @PostMapping("/addToCart/{itemId}")
    public ResponseEntity<String> addItemToCart(@PathVariable("itemId") Integer itemId,
                                                HttpServletRequest httpServletRequest) {
        System.out.println("i m in post mapping1");

        Item itemToAdd = itemService.findItemById(itemId).get();
        System.out.println("i m in post mapping2");

        System.out.println(itemToAdd.getId());
        HttpSession httpSession = httpServletRequest.getSession();

        LoggedInUserDetails loggedInUser = (LoggedInUserDetails) httpSession.getAttribute("loggedInUser");
        System.out.println("i m in post mapping3");
        System.out.println("logged in user" + loggedInUser.getId());
        List<Item> itemList = (List<Item>) httpSession.getAttribute("itemList");
        Integer currentCartPoints = 0;

        for (Item item: itemList) {
            currentCartPoints += item.getPrice();
        }
        System.out.println("currentCartPoints" + currentCartPoints);
        System.out.println("mypointssss" + loggedInUser.getPoints());
        System.out.println("item price" + itemToAdd.getPrice());

        if (loggedInUser.getPoints() < itemToAdd.getPrice() + currentCartPoints) {
            System.out.println("Not enough points");
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("myResponseHeader", "insufficientPoints");
            return new ResponseEntity<String>("You do not have enough redeemable points for this item", httpHeaders, HttpStatus.OK);
        }
        itemList.add(itemToAdd);
        httpSession.setAttribute("currentCartTotal", currentCartPoints + itemToAdd.getPrice());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("myResponseHeader", "cartAddSuccessful");
        return new ResponseEntity<String>("Item added to cart", httpHeaders, HttpStatus.OK);
    }


    // Remove an item from user's cart
    @PutMapping("/removeFromCart/{itemId}")
    @ResponseBody
    public void removeItemFromCart(@PathVariable("itemId") Integer itemId,
                                   HttpServletRequest httpServletRequest) {
        HttpSession httpSession = httpServletRequest.getSession();
        List<Item> itemList = (List<Item>) httpSession.getAttribute("itemList");
        Item item = itemService.findItemById(itemId).get();
        ListIterator<Item> itemListIterator = itemList.listIterator();
        while (itemListIterator.hasNext()) {
            if (itemListIterator.next().getId() == item.getId()) {
                itemListIterator.remove();
                break;
            }
        }
        Integer currentCartPoints = 0;
        for (Item itemInCart : itemList) {
            currentCartPoints += itemInCart.getPrice();
        }
        httpSession.setAttribute("currentCartTotal", currentCartPoints);
        httpSession.setAttribute("itemList", itemList);
    }

    // Create a new order with items from the cart
    @GetMapping("/checkout")
    public ModelAndView createOrder(HttpServletRequest httpServletRequest) {
        System.out.println("Checking out");
        HttpSession httpSession = httpServletRequest.getSession();
        List<Item> itemList = (List<Item>) httpSession.getAttribute("itemList");
        LoggedInUserDetails activeUser = (LoggedInUserDetails) httpSession.getAttribute("loggedInUser");
        OrderHistory orderHistory = new OrderHistory();
        orderHistory.setEmployeeId(activeUser.getId());
        Map<Integer, Integer> itemQuantity = new LinkedHashMap<>();
        Integer totalPoints = 0;
        for (Item item : itemList) {
            if (!itemQuantity.containsKey(item.getId()))
                itemQuantity.put(item.getId(), 1);
            else
                itemQuantity.put(item.getId(), itemQuantity.get(item.getId()) + 1);
            totalPoints += item.getPrice();
        }
        orderHistory.setItemQuantity(itemQuantity);
        orderHistory.setTotalPointsRedeemed(totalPoints);
        orderHistoryService.save(orderHistory);
        employeeService.deductPointsOnCheckout(activeUser, totalPoints);
        System.out.println("Order summary: " + orderHistory);
        itemList.clear();
        httpSession.setAttribute("currentCartTotal", 0);
        return new ModelAndView("redirect:/reap/orders/" + activeUser.getId());
    }

}
