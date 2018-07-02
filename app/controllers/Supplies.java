/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

/**
 *
 * @author simon
 */

import models.Supply;
import play.*;
import play.mvc.*;

import views.html.*;

public class Supplies extends Controller {

    public static Result index() {
        return ok(index.render("INDEX"));
    }
    
    public static Result create() {
        return ok(index.render("CREATE"));
    }
    
    public static Result show(Long id) {
        return ok(index.render("SHOW: "+id));
    }
    
    public static Result update(Long id) {
        return ok(index.render("UPDATE: "+id));
    }
    
    public static Result delete(Long id) {
        return ok(index.render("DELETE: "+id));
    }
}
