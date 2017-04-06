package com.marcos.moiploja.site.web.moip;

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

    public void createOrder(){

    }

    public void createPayment(){

    }
}
