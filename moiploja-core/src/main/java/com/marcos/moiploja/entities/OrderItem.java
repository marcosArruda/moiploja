/**
 *
 */
package com.marcos.moiploja.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Marcos
 */
@Entity
@Table(name = "order_items")
public class OrderItem implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    private BigDecimal price;
    private int quantity;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    public OrderItem(){}

    public OrderItem(Integer id, Product product, int quantity){
        this.id = id;
        this.product = product;
        this.price = product.getPrice();
        this.quantity = quantity;
    }

    public OrderItem(Integer id, Product product, BigDecimal price, int quantity){
        this.id = id;
        this.product = product;
        this.price = price;
        this.quantity = quantity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getSubTotal() {
        return product.getPrice().multiply(new BigDecimal(quantity));
    }

}
