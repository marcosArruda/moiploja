package com.marcos.moiploja.services;

import br.com.moip.API;
import br.com.moip.Client;
import br.com.moip.authentication.Authentication;
import br.com.moip.authentication.BasicAuth;
import br.com.moip.request.*;
import br.com.moip.resource.Order;
import br.com.moip.resource.Payment;
import com.marcos.moiploja.entities.Address;
import com.marcos.moiploja.entities.Customer;
import com.marcos.moiploja.entities.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

/**
 * Created by marcos on 02/04/17.
 */
@Service
public class MoipService {
    //:01010101010101010101010101010101
    @Autowired
    @Value("${moip.token}")
    private String TOKEN;

    //:ABABABABABABABABABABABABABABABABABABABAB
    @Autowired
    @Value("${moip.key}")
    private String KEY;

    private API api;

    public MoipService(){
    }

    public Order createOrder(com.marcos.moiploja.entities.Order lojaOrder){
        if(api == null)
            createAPI();
        Customer cust = lojaOrder.getCustomer();
        OrderRequest orderRequest = new OrderRequest().ownId(lojaOrder.getOrderNumber());
        Address deliveryAddress = lojaOrder.getDeliveryAddress();
        Set<OrderItem> itens = lojaOrder.getItems();

        if(lojaOrder.isCupom()){//with cupom
            itens.forEach((it)->orderRequest.addItem(it.getProduct().getName(),
                    it.getQuantity(), "Descricao", it.getPrice().multiply(BigDecimal.valueOf(0.95)).setScale(2).movePointRight(2).intValue()));
        }else{//without cupom
            itens.forEach((it)->orderRequest.addItem(it.getProduct().getName(),
                    it.getQuantity(), "Descricao", it.getPrice().movePointRight(2).intValue()));
        }


        return api.order().create(orderRequest.customer(new CustomerRequest()
                .ownId(cust.getFirstName())
                .fullname(cust.getFullname())
                .email(cust.getEmail())
                .birthdate(new ApiDateRequest().date(new Date()))
                .taxDocument(TaxDocumentRequest.cpf("22222222222"))
                .phone(new PhoneRequest().setAreaCode("11").setNumber(cust.getPhone()))
                .shippingAddressRequest(new ShippingAddressRequest()
                        .street(deliveryAddress.getAddressLine1())
                        .streetNumber("11")
                        .complement(deliveryAddress.getAddressLine2())
                        .city(deliveryAddress.getCity())
                        .state(deliveryAddress.getState())
                        .district(deliveryAddress.getState())
                        .country(deliveryAddress.getCountry())
                        .zipCode(deliveryAddress.getZipCode())
                )
        ));
    }

    public Payment createPayment(Order moipOrder, com.marcos.moiploja.entities.Order lojaOrder){
        if(api == null)
            createAPI();

        com.marcos.moiploja.entities.Payment lojaPayment = lojaOrder.getPayment();
        Customer cust = lojaOrder.getCustomer();
        return api.payment().create(
                new PaymentRequest()
                        .orderId(moipOrder.getId())
                        .installmentCount(1)
                        .fundingInstrument(
                                new FundingInstrumentRequest()
                                        .creditCard(
                                                new CreditCardRequest()
                                                        .hash(lojaPayment.getCcHash())
                                                        .holder(
                                                                new HolderRequest()
                                                                        .fullname(cust.getFullname())
                                                                        .birthdate("1988-10-10")
                                                                        .phone(new PhoneRequest().setAreaCode("11").setNumber(cust.getPhone()))
                                                                        .taxDocument(TaxDocumentRequest.cpf("22222222222"))
                                                        )
                                        )
                        )
        );
    }

    private void createAPI(){
        //TODO: Extract to Bean
        Authentication auth = new BasicAuth(TOKEN, KEY);
        Client client = new Client(Client.SANDBOX, auth);
        api = new API(client);
    }
}
