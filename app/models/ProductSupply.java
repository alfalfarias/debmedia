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
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.*;

import play.db.ebean.*;


import javax.persistence.*;

@Entity
@Table(name="product_supplies")
public class ProductSupply extends Model {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;
    @Column(nullable=false)
    public String name;
    @Column(nullable=false)
    public int quantity;
    
    @ManyToOne
    public Product product; 
    
    public static List<ProductSupply> all() {
      return find.all();
    }
  
    public static void create(ProductSupply productSupply) {
      productSupply.save();
    }
    
    public static Model.Finder<Long,ProductSupply> find = new Model.Finder(
        Long.class, ProductSupply.class
    );
}
