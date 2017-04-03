package com.marcos.moiploja.site.web.moip;

import br.com.moip.API;
import br.com.moip.Client;
import br.com.moip.authentication.Authentication;
import br.com.moip.authentication.BasicAuth;
import br.com.moip.request.*;
import br.com.moip.resource.Order;
import br.com.moip.resource.Payment;
import org.junit.Test;

import java.util.Date;

/**
 * Created by marcos on 02/04/17.
 */
public class MoipServiceTest {

    @Test
    public void testBasicConnection(){
        //Auth
        Authentication auth = new BasicAuth("TOKEN", "SECRET");

        //Client
        Client client = new Client(Client.SANDBOX, auth);
        //API
        API api = new API(client);

        //order
        Order createdOrder = api.order().create(new OrderRequest()
                .ownId("order_own_id")
                .addItem("Nome do produto", 1, "Mais info...", 100)
                .customer(new CustomerRequest()
                        .ownId("customer_own_id")
                        .fullname("Jose da Silva")
                        .email("josedasilva@email.com")
                        .birthdate(new ApiDateRequest().date(new Date()))
                        .taxDocument(TaxDocumentRequest.cpf("22222222222"))
                        .phone(new PhoneRequest().setAreaCode("11").setNumber("55443322"))
                        .shippingAddressRequest(new ShippingAddressRequest().street("Avenida Faria Lima")
                                .streetNumber("3064")
                                .complement("12 andar")
                                .city("São Paulo")
                                .state("SP")
                                .district("Itaim")
                                .country("BRA")
                                .zipCode("01452-000")
                        )
                )
        );

        //CreditCard Hash
        String CC_HASH = "";

        //Payment
        Payment createdPayment = api.payment().create(
                new PaymentRequest()
                        .orderId("ORD-HPMZSOM611M2")
                        .installmentCount(1)
                        .fundingInstrument(
                                new FundingInstrumentRequest()
                                        .creditCard(
                                                new CreditCardRequest()
                                                        .hash(CC_HASH)
                                                        .holder(
                                                                new HolderRequest()
                                                                        .fullname("Jose Portador da Silva")
                                                                        .birthdate("1988-10-10")
                                                                        .phone(
                                                                                new PhoneRequest()
                                                                                        .setAreaCode("11")
                                                                                        .setNumber("55667788")
                                                                        )
                                                                        .taxDocument(TaxDocumentRequest.cpf("22222222222"))
                                                        )
                                        )
                        )
        );
    }
}
