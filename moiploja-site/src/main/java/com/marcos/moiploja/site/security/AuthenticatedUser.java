/**
 *
 */
package com.marcos.moiploja.site.security;

import com.marcos.moiploja.entities.Customer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Collection;

public class AuthenticatedUser extends org.springframework.security.core.userdetails.User {

    private static final long serialVersionUID = 1L;
    private Customer customer;

    public AuthenticatedUser(Customer customer) {
        super(customer.getEmail(), customer.getPassword(), getAuthorities(customer));
        this.customer = customer;
    }

    private static Collection<? extends GrantedAuthority> getAuthorities(Customer customer) {
        Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_USER");
        return authorities;
    }

    public Customer getCustomer() {
        return customer;
    }
}
