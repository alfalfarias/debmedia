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
@Table(name="supplies", uniqueConstraints = {@UniqueConstraint(columnNames = {"id", "name"})})
public class Supply extends Model {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;
    @Column(nullable=false)
    public String name;    
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
