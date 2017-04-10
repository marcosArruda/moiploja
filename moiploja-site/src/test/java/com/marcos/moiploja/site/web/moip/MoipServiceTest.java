package com.marcos.moiploja.site.web.moip;

import br.com.moip.API;
import br.com.moip.Client;
import br.com.moip.authentication.Authentication;
import br.com.moip.authentication.BasicAuth;
import br.com.moip.request.*;
import br.com.moip.resource.CreditCard;
import br.com.moip.resource.Order;
import br.com.moip.resource.Payment;
import com.marcos.moiploja.MoiplojaSiteApplication;
import com.marcos.moiploja.entities.Address;
import com.marcos.moiploja.entities.Customer;
import com.marcos.moiploja.entities.OrderItem;
import com.marcos.moiploja.entities.Product;
import com.marcos.moiploja.services.MoipService;
import com.marcos.moiploja.site.config.WebConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.TransactionScoped;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by marcos on 02/04/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MoiplojaSiteApplication.class)
public class MoipServiceTest {


    //private static String TOKEN = "01010101010101010101010101010101";
    private static String TOKEN = "QAG9U4UHVDAXUOXZJCO4KJBAFXEVMOCI";

    //private static String KEY = "ABABABABABABABABABABABABABABABABABABABAB";
    private static String KEY = "KPLDZBDD9I6AO3YE3E1RBO5MGAUSJY46UDCKQEE7";


    private API api;
    private com.marcos.moiploja.entities.Order lojaOrder;
    private Customer custOrig;
    private Address address;
    private String CC_HASH = "U1M56WfX6LFBoda2a24Q9FANiV+Zh5zpOuHMvGaz8jokM2/qoe09b3LpSkWhcXX0vK6NWWtFKJg9j5X8aUxYttLrGhw27jqwVj/Zt2e2RLo78hs8v9tcyzexd/rbX1qSoZ7zx7pLJK6Po15qb+aP4Qyx/8Oc4N72Z6Dg1g5PZf7Hr71iuKVbfsETTDjKVsDeI/Es0TSbIsh2bX4/cYDp3kS6y5dSvSFZ9BFVJ3OU307liCTRL6uwjAUyqB/Fso3M/EzO25WlwhGmE743qo8WXUNBlZ2RDEfbObsk//+wInL6Ohcqh5Oywm3jtAeiSKvl05KnVOj9muqSigP1+gTJaw==";


    @Autowired
    private MoipService moipService;

    @Before
    public void initialize(){
        //api = moipService.createAPITest();
        lojaOrder = new com.marcos.moiploja.entities.Order();
        lojaOrder.setId(getRandomInt());

        custOrig = new Customer();
        custOrig.setFirstName("Jonas");
        custOrig.setLastName("Barboza");
        custOrig.setEmail("marcos.f.power@gmail.com");
        custOrig.setPhone("123456789");

        lojaOrder.setCustomer(custOrig);
        address = new Address();
        address.setAddressLine1("Rua dos Bobos");
        address.setAddressLine2("1212");
        address.setCity("São Paulo");
        address.setState("SP");
        address.setCountry("Brasil");
        address.setZipCode("01537001");

        lojaOrder.setDeliveryAddress(address);
        lojaOrder.setItems(new HashSet<>(Arrays.asList(
                new OrderItem(1, new Product("Produto1", BigDecimal.valueOf(163.36)), 1),
                new OrderItem(2, new Product("Produto2", BigDecimal.valueOf(136.64)), 1),
                new OrderItem(3, new Product("Produto3", BigDecimal.valueOf(126.74)), 1)
        )));


        lojaOrder.setPayment(new com.marcos.moiploja.entities.Payment(getRandomInt(),CC_HASH, lojaOrder.getTotalAmount()));
    }

    @Test
    public void testBasicFull(){
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
                        .fullname("Marcos Teste")
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

        System.out.println("ORDER_ID: "+createdOrder.getId());
//
//        //CreditCard Hash
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

    @Test
    public void testOrderAndPayment(){
        OrderRequest orderRequest = new OrderRequest().ownId(lojaOrder.getId().toString());
        lojaOrder.getItems().forEach((it)->orderRequest.addItem(it.getProduct().getName(),
                it.getQuantity(), "Descricao", it.getPrice().movePointRight(2).intValue()));

        Order moipOrder = moipService.createOrder(lojaOrder);
        Assert.assertNotNull(moipOrder.getId());
        Payment payment = moipService.createPayment(moipOrder, lojaOrder);

        Assert.assertNotNull(payment.getId());
        Assert.assertNotNull(payment.getAmount());
        Assert.assertEquals(BigDecimal.valueOf(payment.getAmount().getTotal()).movePointLeft(2), lojaOrder.getTotalAmount());
    }

    private int getRandomInt(){
        return ThreadLocalRandom.current().nextInt(10000, 100000 + 1);
    }
}
