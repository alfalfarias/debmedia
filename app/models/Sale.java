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
@Table(name="sales")
public class Sale extends Model {
    @Id
    public Long id;
    public String client;

    @OneToOne(cascade=CascadeType.ALL)
    public SaleProduct saleProduct;
    
    public static List<Sale> all() {
      return find.all();
    }
    
    //public static List<Supply> all() {
      //  return new ArrayList<Supply>();
    //}
  
    public static void create(Sale sale) {
      sale.save();
    }
    
    public static Model.Finder<Long,Sale> find = new Model.Finder(
        Long.class, Sale.class
    );
}
