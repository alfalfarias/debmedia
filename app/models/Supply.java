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
import play.data.validation.Constraints.*;

import javax.persistence.*;

public class Supply extends Model {
    @Id
    public String email;
    public String name;
    public String password;
    
    public Supply(String email, String name, String password) {
      this.email = email;
      this.name = name;
      this.password = password;
    }

    public static Finder<String,Supply> find = new Finder<String,Supply>(
        String.class, Supply.class
    ); 
}
