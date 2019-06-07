package com.ttn.reap.event;

import com.ttn.reap.entity.Employee;
import com.ttn.reap.entity.Item;
import com.ttn.reap.enums.Role;
import com.ttn.reap.repository.EmployeeRepository;
import com.ttn.reap.repository.ItemRepository;
import com.ttn.reap.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.enterprise.inject.New;
import java.util.ArrayList;
import java.util.List;

@Component
public class Bootstrap {

    @Autowired
    private ItemService itemService;


    @EventListener(ContextRefreshedEvent.class)
    public void saveItems(){
    //      it should executed once only !!

     /*   Item item1= new Item("Sipper",5, 250, "/imgs/newSipper.jpg");
        Item item2= new Item("Bag",5,500,"/imgs/bag.jpg");
        Item item3= new Item("Card-Holder",5,50,"/imgs/cardholder.jpeg");
        Item item4= new Item("Hoody",5,800,"/imgs/hoody.jpeg");
        Item item5= new Item("Notepad",5,70,"/imgs/diary.jpg");
        Item item6= new Item("Laptop Cover",5,250,"/imgs/laptopcover.jpg");
        Item item7= new Item("KeyChain",5,20,"/imgs/keychain.jpg");
        Item item8= new Item("Mouse pad",5,60,"/imgs/mousepad.jpg");
        Item item9= new Item("Pen",5,30,"/imgs/pen.jpg");
        Item item10= new Item("Pendrive",5,500,"/imgs/pendrive.jpg");
        Item item11= new Item("Mug",5,120,"/imgs/mug.jpg");
        Item item12= new Item("Paper Weight",5,50,"/imgs/paperweight.jpg");
        Item item13= new Item("Watch",5,1250,"/imgs/watch.jpg");
        Item item14= new Item("Wallet",5,750,"/imgs/wallet.jpg");
        Item item15= new Item("Tshirt",5,450,"/imgs/tshirt.jpg");

        List<Item> items= new ArrayList<>();
        items.add(item1);
        items.add(item2);
        items.add(item3);
        items.add(item4);
        items.add(item5);
        items.add(item6);
        items.add(item7);
        items.add(item8);
        items.add(item9);
        items.add(item10);
        items.add(item11);
        items.add(item12);
        items.add(item13);
        items.add(item14);
        items.add(item15);
        itemService.saveAllItems(items);*/
    }


}