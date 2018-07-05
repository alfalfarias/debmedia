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
@Table(name="sales")
public class Sale extends Model {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;
    @Column(nullable=false)
    public String client;

    @Column(nullable=false)
    @OneToOne(cascade=CascadeType.ALL)
    public SaleProduct saleProduct;
    
    public static List<Sale> all() {
      return find.all();
    }
  
    public static void create(Sale sale) {
      sale.save();
    }
    
    public static Model.Finder<Long,Sale> find = new Model.Finder(
        Long.class, Sale.class
    );
}
