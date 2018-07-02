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
public class Supply extends Model {
    @Id
    public Long id;
    public String email;
    public String name;
    public String password;

    public static List<Supply> all() {
      return find.all();
    }
    
    //public static List<Supply> all() {
      //  return new ArrayList<Supply>();
    //}
  
    public static void create(Supply supply) {
      supply.save();
    }
    
    public static Finder<Long,Supply> find = new Finder(
        Long.class, Supply.class
    );
}
