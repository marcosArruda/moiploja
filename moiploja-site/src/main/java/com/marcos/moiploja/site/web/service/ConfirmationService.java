package com.marcos.moiploja.site.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.marcos.moiploja.entities.OrderStatus;
import com.marcos.moiploja.entities.Subscriber;
import com.marcos.moiploja.entities.WebSocketEvent;
import com.marcos.moiploja.entities.dto.PaymentDTO;
import com.marcos.moiploja.repositories.SubscriberRepository;
import com.marcos.moiploja.repositories.WebsocketEventRepository;

/**
 * Created by ive_marruda on 07/04/17.
 */
@Service
public class ConfirmationService {

    @Autowired
    private SubscriberRepository subscriberRepository;

    @Autowired
    private WebsocketEventRepository websocketEventRepository;

    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    public ConfirmationService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void send(PaymentDTO payment) {
        System.out.println("Sending message to customer: "+payment.getStatus());
        WebSocketEvent event = new WebSocketEvent();
        event.setPaymentId(payment.getId());
        event.setStatus(getStatus(payment.getStatus()));
        websocketEventRepository.save(event);

        send(payment.getId());
    }

    private OrderStatus getStatus(String status) {
        OrderStatus updatedStatus;
        switch (status) {
            case "AUTHORIZED":
                updatedStatus = OrderStatus.COMPLETED;
                break;
            default:
                updatedStatus = OrderStatus.PROCESSING;
        }
        return updatedStatus;
    }

    public void addSubs(String paymentId) {
        subscriberRepository.saveAndFlush(new Subscriber(paymentId));
        send(paymentId);
    }

    private void send(String paymentId) {
        if (subscriberRepository.findOne(paymentId) != null) {
            List<WebSocketEvent> byPaymentId = websocketEventRepository.findByPaymentId(paymentId);
            byPaymentId.stream()
                    .filter((ev) -> !ev.isSent())
                    .forEach((ev) -> {
                        messagingTemplate.convertAndSend("/topic/pay/" + paymentId, ev.getStatus().name());
                        ev.setSent(true);
                        websocketEventRepository.save(ev);
                    });
        }
    }
}
