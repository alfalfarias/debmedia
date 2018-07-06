/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import play.data.validation.Constraints;
import play.db.ebean.Model;

/**
 *
 * @author simon
 */


@Entity
@Table(name="supplies")
public class Supply extends Model {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;
    @Constraints.Required
    @Column(nullable=false)
    public String name;    
    @Constraints.Min(value=1, message = "El insumo debe contener al menos una unidad como mínimo")
    @Column(nullable=false)
    public int quantity;

    public static List<Supply> all() {
      return find.all();
    }
  
    public static void create(Supply supply) {
      supply.save();
    }
    
    public static Finder<Long,Supply> find = new Finder(
        Long.class, Supply.class
    );
}
