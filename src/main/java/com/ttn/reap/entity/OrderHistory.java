package com.ttn.reap.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Entity
public class OrderHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //   key=itemId value=itemQuantity
    @ElementCollection(fetch = FetchType.EAGER)
    private Map<Integer, Integer> itemQuantity = new LinkedHashMap<>();

    private Integer employeeId;

    private Integer totalPointsRedeemed;

    @CreationTimestamp
    private LocalDateTime purchaseDate;


    public Integer getId() {
        return id;
    }

    public Map<Integer, Integer> getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(Map<Integer, Integer> itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public Integer getTotalPointsRedeemed() {
        return totalPointsRedeemed;
    }

    public void setTotalPointsRedeemed(Integer totalPointsRedeemed) {
        this.totalPointsRedeemed = totalPointsRedeemed;
    }

    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
    public List<Integer> getItemIdsInOrderSummary() {
        List<Integer> itemIds = new ArrayList<>();
        for (Integer key : this.getItemQuantity().keySet()) {
            itemIds.add(key);
        }
        return itemIds;
    }

}
