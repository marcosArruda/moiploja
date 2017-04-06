package com.marcos.moiploja.site.web.moip;

import br.com.moip.API;
import br.com.moip.Client;
import br.com.moip.authentication.Authentication;
import br.com.moip.authentication.BasicAuth;
import br.com.moip.request.*;
import br.com.moip.resource.CreditCard;
import br.com.moip.resource.Order;
import br.com.moip.resource.Payment;
import com.marcos.moiploja.site.config.WebConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

/**
 * Created by marcos on 02/04/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class MoipServiceTest {


    private static String TOKEN = "01010101010101010101010101010101";

    private static String KEY = "ABABABABABABABABABABABABABABABABABABABAB";

    @Test
    public void testBasicConnection(){
        //Auth
        //"QAG9U4UHVDAXUOXZJCO4KJBAFXEVMOCI", "KPLDZBDD9I6AO3YE3E1RBO5MGAUSJY46UDCKQEE7"
        Authentication auth = new BasicAuth(TOKEN, KEY);
        System.out.println(String.format("current token and key: %s %s", TOKEN, KEY));

        //Client
        Client client = new Client(Client.SANDBOX, auth);
        //API
        API api = new API(client);
        System.out.println(api.account().get().getEmail().getAddress());

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


//
//        //CreditCard Hash
        String CC_HASH = "GN5MyANMpZp/9QtBfryo6zyjc8wrtnb/gONfoMsSd" +
                "Qye3iBwOHhC4XkhFCELZaBvHVfNms79ud2zUvicznZnD8ux3+W" +
                "U1XqCj4jewB9K0ZdBP6IBDiomwaSDDPf6H4yBwK6vn8C6iIP6P" +
                "EPPtgFO5ySeLiEA531ySHF36bSs26S/gBpbTr0s5qzFpWBSDNE" +
                "ijIRG5RGeQF2nMrrw3bbKlknASbMg9IRlZ7r0UYCkPhs35DtnL" +
                "V6pGamqVq4Gjf0jddtpX1d+yPW9ZqmhMaB/7ga+tIWo0jQf6K8" +
                "3XcutoH2XXGHujFXO+kQavt6dFMzS33N3Ghvp7WRaEL0Yx7UhNw==";

//        //Payment
        Payment createdPayment = api.payment().create(
                new PaymentRequest()
                        .orderId(createdOrder.getId())
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
