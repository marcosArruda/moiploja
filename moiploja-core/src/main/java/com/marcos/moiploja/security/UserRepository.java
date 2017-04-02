/**
 *
 */
package com.marcos.moiploja.security;

import com.marcos.moiploja.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Marcos
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);

}
