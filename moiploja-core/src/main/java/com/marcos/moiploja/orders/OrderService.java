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
    EmailService emailService;
    @Autowired
    OrderRepository orderRepository;

    public Order processOrder(OrderDTO order, Cart cart) {
        final Order newOrder = Order.buildOrder(order, cart);

        newOrder.setOrderNumber(String.valueOf(System.currentTimeMillis()));
        orderRepository.save(newOrder);

        br.com.moip.resource.Order moipOrder = moipService.createOrder(newOrder);
        br.com.moip.resource.Payment moipPayment = moipService.createPayment(moipOrder, newOrder);

        this.sendOrderConfirmationEmail(newOrder);
        return newOrder;
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
