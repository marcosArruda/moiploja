/**
 *
 */
package com.marcos.moiploja.catalog;

import com.marcos.moiploja.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Marcos
 */
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Category getByName(String name);

}
