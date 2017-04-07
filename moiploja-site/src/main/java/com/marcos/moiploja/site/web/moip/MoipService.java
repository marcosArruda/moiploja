package com.marcos.moiploja.site.web.moip;

import java.util.Date;
import java.util.Set;

import br.com.moip.API;
import br.com.moip.Client;
import br.com.moip.authentication.Authentication;
import br.com.moip.authentication.BasicAuth;
import br.com.moip.request.ApiDateRequest;
import br.com.moip.request.CreditCardRequest;
import br.com.moip.request.CustomerRequest;
import br.com.moip.request.FundingInstrumentRequest;
import br.com.moip.request.HolderRequest;
import br.com.moip.request.OrderRequest;
import br.com.moip.request.PaymentRequest;
import br.com.moip.request.PhoneRequest;
import br.com.moip.request.ShippingAddressRequest;
import br.com.moip.request.TaxDocumentRequest;
import br.com.moip.resource.Order;
import br.com.moip.resource.Payment;
import com.marcos.moiploja.entities.Address;
import com.marcos.moiploja.entities.Customer;
import com.marcos.moiploja.entities.OrderItem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by marcos on 02/04/17.
 */
@Service
public class MoipService {
    @Value("${moip.token:01010101010101010101010101010101}")
    private static String TOKEN;

    @Value("${moip.token:ABABABABABABABABABABABABABABABABABABABAB}")
    private static String KEY;

    private API api;

    public MoipService(){
        createAPI();
    }

    public void printTokenAndKey(){
        StringBuilder builder = new StringBuilder();
        builder.append("TOKEN=").append(TOKEN)
                .append(" ")
                .append("KEY=").append(KEY);
        System.out.println(builder.toString());
    }

    public Order createOrder(com.marcos.moiploja.entities.Order order){
        OrderRequest orderRequest = new OrderRequest().ownId(order.getId().toString());
        Customer cust = order.getCustomer();
        Address deliveryAddress = order.getDeliveryAddress();
        Set<OrderItem> itens = order.getItems();

        itens.forEach((it)->orderRequest.addItem(it.getProduct().getName(),
                it.getQuantity(), "Descricao", it.getPrice().intValue()));

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
                                                                        .fullname(lojaOrder.getCustomer().getFullname())
                                                                        .birthdate("1988-10-10")
                                                                        .phone(new PhoneRequest().setAreaCode("11").setNumber(cust.getPhone()))
                                                                        .taxDocument(TaxDocumentRequest.cpf("22222222222"))
                                                        )
                                        )
                        )
        );
    }

    private void createAPI(){
        Authentication auth = new BasicAuth(TOKEN, KEY);
        Client client = new Client(Client.SANDBOX, auth);
        api = new API(client);
    }
}
