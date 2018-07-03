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

import java.util.*;

import play.db.ebean.*;


import javax.persistence.*;

@Entity
@Table(name="sale_products")
public class SaleProduct extends Model {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;
    public String name;
    public Double price;
    
    @OneToOne(cascade = CascadeType.ALL)
    public Sale sale; 
    
    @OneToMany(mappedBy = "saleProduct", cascade=CascadeType.ALL)
    public List<SaleProductSupply> saleProductSupplies = new ArrayList<SaleProductSupply>();

    public static List<Product> all() {
      return find.all();
    }
    
    //public static List<Supply> all() {
      //  return new ArrayList<Supply>();
    //}
  
    public static void create(SaleProduct saleProduct) {
      saleProduct.save();
    }
    
    public static Model.Finder<Long,Product> find = new Model.Finder(
        Long.class, SaleProduct.class
    );
}
