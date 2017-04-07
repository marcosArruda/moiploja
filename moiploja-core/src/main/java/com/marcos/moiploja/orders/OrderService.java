/**
 *
 */
package com.marcos.moiploja.orders;

import com.marcos.moiploja.MoiplojaException;
import com.marcos.moiploja.common.services.EmailService;
import com.marcos.moiploja.common.services.MLLogger;
import com.marcos.moiploja.customers.CustomerService;
import com.marcos.moiploja.entities.*;
import com.marcos.moiploja.entities.dto.Cart;
import com.marcos.moiploja.entities.dto.LineItem;
import com.marcos.moiploja.entities.dto.OrderDTO;
import com.marcos.moiploja.services.MoipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Marcos
 */
@Service
@Transactional
public class OrderService {
    private static final MLLogger logger = MLLogger.getLogger(OrderService.class);

    @Autowired
    MoipService moipService;

    @Autowired
    CustomerService customerService;

    @Autowired
    EmailService emailService;
    @Autowired
    OrderRepository orderRepository;

    public Order processOrder(OrderDTO order, Cart cart) {
        Order newOrder = new Order();
        String email = cart.getCustomer().getEmail();
        Customer customer = customerService.getCustomerByEmail(email);
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

        Set<OrderItem> orderItems = new HashSet<OrderItem>();
        List<LineItem> lineItems = cart.getItems();
        for (LineItem lineItem : lineItems) {
            OrderItem item = new OrderItem();
            item.setProduct(lineItem.getProduct());
            item.setQuantity(lineItem.getQuantity());
            item.setPrice(lineItem.getProduct().getPrice());
            item.setOrder(newOrder);
            orderItems.add(item);
        }

        newOrder.setItems(orderItems);

        Payment payment = new Payment();
        payment.setCcNumber(order.getCcNumber());
        payment.setCvv(order.getCvv());
        payment.setCcHash(order.getCcHash());
        newOrder.setCcHash(order.getCcHash());
        System.out.println("A HASH É: "+ order.getCcHash());

        newOrder.setPayment(payment);
        newOrder = createOrder(newOrder);

        br.com.moip.resource.Order moipOrder = moipService.createOrder(newOrder);
        br.com.moip.resource.Payment moipPayment = moipService.createPayment(moipOrder, newOrder);

        this.sendOrderConfirmationEmail(newOrder);
        return newOrder;
    }

    public Order createOrder(Order order) {
        //order.setOrderNumber(UUID.randomUUID().toString());
        order.setOrderNumber(String.valueOf(System.currentTimeMillis()));
        Order savedOrder = orderRepository.save(order);
        logger.info("New order created. Order Number : {}", savedOrder.getOrderNumber());
        return savedOrder;
    }

    public Order getOrder(String orderNumber) {
        return orderRepository.findByOrderNumber(orderNumber);
    }

    public List<Order> getAllOrders() {
        Sort sort = new Sort(Direction.DESC, "createdOn");
        return orderRepository.findAll(sort);
    }

    public Order updateOrder(Order order) {
        Order o = getOrder(order.getOrderNumber());
        o.setStatus(order.getStatus());
        Order savedOrder = orderRepository.save(o);
        return savedOrder;
    }

    protected void sendOrderConfirmationEmail(Order order) {
        try {
            emailService.sendEmail(order.getCustomer().getEmail(),
                    "QuilCartCart - Order Confirmation",
                    "Your order has been placed successfully.\n"
                            + "Order Number : " + order.getOrderNumber());
        } catch (MoiplojaException e) {
            logger.error(e);
        }
    }


}
