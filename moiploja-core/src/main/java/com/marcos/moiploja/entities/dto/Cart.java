/**
 *
 */
package com.marcos.moiploja.entities.dto;

import com.marcos.moiploja.entities.Address;
import com.marcos.moiploja.entities.Customer;
import com.marcos.moiploja.entities.Payment;
import com.marcos.moiploja.entities.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Marcos
 */
public class Cart {
    private List<LineItem> items;
    private Customer customer;
    private Address deliveryAddress;
    private Payment payment;

    private boolean cupom;

    public Cart() {
        items = new ArrayList<LineItem>();
        customer = new Customer();
        deliveryAddress = new Address();
        payment = new Payment();
    }

    public void setCupom(boolean cupom){
        this.cupom = cupom;
    }

    public boolean isCupom(){
        return this.cupom;
    }


    public void addItem(Product product) {
        for (LineItem lineItem : items) {
            if (lineItem.getProduct().getSku().equals(product.getSku())) {
                lineItem.setQuantity(lineItem.getQuantity() + 1);
                return;
            }
        }
        LineItem item = new LineItem(product, 1);
        this.items.add(item);
    }

    public void updateItemQuantity(Product product, int quantity) {
        for (LineItem lineItem : items) {
            if (lineItem.getProduct().getSku().equals(product.getSku())) {
                lineItem.setQuantity(quantity);
            }
        }
    }

    public void removeItem(String sku) {
        LineItem item = null;
        for (LineItem lineItem : items) {
            if (lineItem.getProduct().getSku().equals(sku)) {
                item = lineItem;
                break;
            }
        }
        if (item != null) {
            items.remove(item);
        }
    }

    public void clearItems() {
        items = new ArrayList<LineItem>();
    }

    public int getItemCount() {
        int count = 0;
        for (LineItem lineItem : items) {
            count += lineItem.getQuantity();
        }
        return count;
    }

    public List<LineItem> getItems() {
        return items;
    }

    public void setItems(List<LineItem> items) {
        this.items = items;
    }

    public BigDecimal getTotalAmount() {
        BigDecimal amount = BigDecimal.ZERO;
        for (LineItem lineItem : items) {
            amount = amount.add(lineItem.getSubTotal());
        }
        if(isCupom()){
            amount = amount.multiply(BigDecimal.valueOf(0.95));
        }

        return amount;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Address getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(Address deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

}
