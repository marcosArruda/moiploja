/**
 *
 */
package com.marcos.moiploja.security;

import com.marcos.moiploja.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Marcos
 */
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findByName(String name);

}
