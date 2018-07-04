/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author simon
 */

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.*;

import play.db.ebean.*;


import javax.persistence.*;

@Entity
@Table(name="products", uniqueConstraints = {@UniqueConstraint(columnNames = {"id", "name"})})
public class Product extends Model {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;
    @Column(nullable=false)
    public String name;
    @Column(nullable=false)
    public double price;
    @JsonIgnoreProperties("product")
    @OneToMany(mappedBy = "product", cascade=CascadeType.ALL)
    public List<ProductSupply> productSupplies;

    public static List<Product> all() {
      return find.all();
    }
    
    public static void create(Product product) {
      product.save();
    }
    
    public static Model.Finder<Long,Product> find = new Model.Finder(
        Long.class, Product.class
    );
}
