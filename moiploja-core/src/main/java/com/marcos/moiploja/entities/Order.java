/**
 *
 */
package com.marcos.moiploja.entities;

import com.marcos.moiploja.entities.dto.Cart;
import com.marcos.moiploja.entities.dto.LineItem;
import com.marcos.moiploja.entities.dto.OrderDTO;
import com.sun.org.apache.xpath.internal.operations.Bool;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * @author Marcos
 */
@Entity
@Table(name = "orders")
public class Order implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, unique = true)
    private String orderNumber;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private Set<OrderItem> items;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "cust_id")
    private Customer customer;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "delivery_addr_id")
    private Address deliveryAddress;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "billing_addr_id")
    private Address billingAddress;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "payment_id")
    private Payment payment;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_on")
    private Date createdOn;

    @Column(nullable = true, unique = false, length = 2000)
    private String ccHash;

    @Column(nullable = true, unique = false)
    private Boolean cupom;

    public Order() {
        this.items = new HashSet<OrderItem>();
        this.status = OrderStatus.NEW;
        this.createdOn = new Date();
        this.cupom = false;
    }

    public static Order buildOrder(OrderDTO order, Cart cart){
        final Order newOrder = new Order();
        final Customer customer = new Customer();

        newOrder.setCupom(cart.isCupom());

        customer.setEmail(order.getEmailId());
        customer.setFirstName(order.getFirstName());
        customer.setLastName(order.getLastName());
        customer.setPhone(order.getPhone());
        customer.setPassword("");

        newOrder.setCustomer(customer);
        Address address = new Address();
        address.setAddressLine1(order.getAddressLine1());
        address.setAddressLine2(order.getAddressLine2());
        address.setCity(order.getCity());
        address.setState(order.getState());
        address.setZipCode(order.getZipCode());
        address.setCountry(order.getCountry());

        newOrder.setDeliveryAddress(address);

        Address billingAddress = new Address();
        billingAddress.setAddressLine1(order.getAddressLine1());
        billingAddress.setAddressLine2(order.getAddressLine2());
        billingAddress.setCity(order.getCity());
        billingAddress.setState(order.getState());
        billingAddress.setZipCode(order.getZipCode());
        billingAddress.setCountry(order.getCountry());

        newOrder.setBillingAddress(billingAddress);

        final Set<OrderItem> orderItems = new HashSet<OrderItem>();
        List<LineItem> lineItems = cart.getItems();
        lineItems.forEach((lineItem) -> {
            OrderItem item = new OrderItem();
            item.setProduct(lineItem.getProduct());
            item.setQuantity(lineItem.getQuantity());
            item.setPrice(lineItem.getProduct().getPrice());
            item.setOrder(newOrder);
            orderItems.add(item);
        });

        newOrder.setItems(orderItems);

        Payment payment = new Payment();
        payment.setCcNumber(order.getCcNumber());
        payment.setCvv(order.getCvv());
        payment.setCcHash(order.getCcHash());
        newOrder.setCcHash(order.getCcHash());
        System.out.println("A HASH É: "+ order.getCcHash());
        newOrder.setPayment(payment);
        newOrder.getTotalAmount();
        return newOrder;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Set<OrderItem> getItems() {
        return items;
    }

    public void setItems(Set<OrderItem> items) {
        this.items = items;
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

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Address getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
    }


    public BigDecimal getTotalAmount() {
        BigDecimal amount = BigDecimal.ZERO;
        for (OrderItem item : items) {
            amount = amount.add(item.getSubTotal());
        }

        if(cupom) {
            amount = amount.multiply(BigDecimal.valueOf(0.95)).setScale(2);
        }
        return amount;
    }

    public String getCcHash() {
        return ccHash;
    }

    public void setCcHash(String ccHash) {
        this.ccHash = ccHash;
    }

    public Boolean isCupom() {
        return cupom;
    }

    public void setCupom(Boolean cupom) {
        this.cupom = cupom;
    }
}
