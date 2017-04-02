/**
 *
 */
package com.marcos.moiploja.site.security;

import com.marcos.moiploja.customers.CustomerService;
import com.marcos.moiploja.entities.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author Marcos
 */
@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    CustomerService customerService;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        Customer customer = customerService.getCustomerByEmail(email);
        if (customer == null) {
            throw new UsernameNotFoundException("Email " + email + " not found");
        }
        return new AuthenticatedUser(customer);
    }

}
