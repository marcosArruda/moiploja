package com.marcos.moiploja.site.web.controllers;

import com.marcos.moiploja.entities.Order;
import com.marcos.moiploja.entities.OrderStatus;
import com.marcos.moiploja.entities.dto.PaymentDTO;
import com.marcos.moiploja.entities.dto.ResponseDTO;
import com.marcos.moiploja.orders.OrderRepository;
import com.marcos.moiploja.orders.OrderService;
import com.marcos.moiploja.site.web.service.ConfirmationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ive_marruda on 07/04/17.
 */

@RestController
@RequestMapping("/moip")
public class MoipController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private ConfirmationService confirmationService;

    @RequestMapping(value = "/response", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void response(@RequestBody ResponseDTO response) throws InterruptedException {
        System.out.println("Got Response from MOIP: "+response.getResource().getPayment().getStatus());
        PaymentDTO payment = response.getResource().getPayment();

        Order order = orderService.findOrder(payment.getId());
        if(order != null && "AUTHORIZED".equals(payment.getStatus())){
            order.setStatus(OrderStatus.COMPLETED);
            orderService.updateOrder(order);
        }

        confirmationService.send(payment);
    }
}
