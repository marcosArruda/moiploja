/**
 *
 */
package com.marcos.moiploja;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.util.SocketUtils;

/**
 * @author Marcos
 */
@SpringBootApplication
public class MoiplojaSiteApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoiplojaSiteApplication.class, args);
    }

}
