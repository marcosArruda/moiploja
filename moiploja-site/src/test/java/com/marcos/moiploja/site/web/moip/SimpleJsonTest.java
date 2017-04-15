package com.marcos.moiploja.site.web.moip;

import com.google.gson.Gson;
import com.marcos.moiploja.entities.dto.PaymentDTO;
import com.marcos.moiploja.entities.dto.ResourceDTO;
import com.marcos.moiploja.entities.dto.ResponseDTO;
import org.junit.Test;

/**
 * Created by marcos on 14/04/17.
 */
public class SimpleJsonTest {

    @Test
    public void testJson(){
        ResponseDTO response = new ResponseDTO();
        ResourceDTO resource = new ResourceDTO();

        PaymentDTO payment = new PaymentDTO();
        payment.setStatus("TEST!!!!!!");

        resource.setPayment(payment);
        response.setResource(resource);
        Gson gson = new Gson();
        String json = gson.toJson(response);
        System.out.println(json);
    }
}
