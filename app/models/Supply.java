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
@Table(name="supplies")
public class Supply extends Model {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;
    public String name;
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
