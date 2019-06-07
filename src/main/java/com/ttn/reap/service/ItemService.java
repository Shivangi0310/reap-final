package com.ttn.reap.service;

import com.ttn.reap.DTO.OrderDto;
import com.ttn.reap.entity.Employee;
import com.ttn.reap.entity.Item;
import com.ttn.reap.entity.OrderHistory;
import com.ttn.reap.repository.EmployeeRepository;
import com.ttn.reap.repository.ItemRepository;
import com.ttn.reap.repository.OrderHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private OrderHistoryRepository orderHistoryRepository;

    @Autowired
    private EmployeeRepository employeeRepository;


    public Optional<Employee> findById(Integer id){
        return employeeRepository.findById(id);
    }


    public void saveAllItems(List<Item> items){
        itemRepository.saveAll(items);
    }

    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    public Optional<Item> findItemById(Integer itemId) {
        return itemRepository.findById(itemId);
    }

    /*public boolean updateUserItemPurchased(OrderDto orderDto) {
        OrderHistory orders = new OrderHistory();
        List<Item> itemList=new ArrayList<>();
        List<Integer> itemId = orderDto.getItemList();
        Optional<Employee> employee = employeeRepository.findById(orderDto.getEmployeeId());
        int cartTotal = orderDto.getTotalPointsRedeemed();
        if (employee.get().getPoints() < cartTotal) {
            return false;
        } else {
            for (Integer item : itemId) {
                Optional<Item> itemDetails = itemRepository.findById(item);
                if (itemDetails.isPresent()) {
                    itemList.add(itemDetails.get());
                }
            }
            employee.get().setPoints(employee.get().getPoints() - cartTotal);
            orders.setItemList(itemList);
            orders.setTotalPointsRedeemed(cartTotal);
            orders.setEmployeeId(employee.get());
            employeeRepository.save(employee.get());
            orderHistoryRepository.save(orders);
            return true;
        }

    }*/

}
