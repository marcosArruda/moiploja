package com.marcos.moiploja;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created by marcos on 02/04/17.
 */
public class SimpleCryptTest {

    @Test
    public void testPasswordCrypt(){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String pass = "pass";
        String encodedPass = passwordEncoder.encode(pass);
        System.out.println(encodedPass);
    }
}
