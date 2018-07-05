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

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import play.db.ebean.Model;

@Entity
@Table(name="sale_product_supplies")
public class SaleProductSupply extends Model {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;
    @Column(nullable=false)
    public String name;
    @Column(nullable=false)
    public int quantity;
    
    public SaleProductSupply() {}
    public SaleProductSupply(Long id, String name, int quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }
    @OneToOne(cascade = CascadeType.ALL)
    public SaleProduct saleProduct; 

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
