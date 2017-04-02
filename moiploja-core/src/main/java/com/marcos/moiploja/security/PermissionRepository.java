/**
 *
 */
package com.marcos.moiploja.security;

import com.marcos.moiploja.entities.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Marcos
 */
public interface PermissionRepository extends JpaRepository<Permission, Integer> {

}
