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
import java.util.*;

import play.db.ebean.*;


import javax.persistence.*;

@Entity
@Table(name="products")
public class Product extends Model {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;
    public String name;
    public double price;
    
    @OneToMany(mappedBy = "product", cascade=CascadeType.ALL)// @JsonBackReference
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
