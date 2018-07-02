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
import play.db.ebean.*;
import play.data.*;
import play.*;
import play.mvc.*;

import views.html.*;

public class Supplies extends Controller {

    public static Result index() {
        //return redirect(routes.Application.Supplies());
        //return TODO;
        return ok(("INDEX"));
    }
    
    public static Result list() {
        return ok(
          views.html.supplies.render()
          //views.html.index.render(Supply.all(), supplyForm)
        );
    }

    public static Result listProducts() {
        return ok(
          views.html.products.render()
          //views.html.index.render(Supply.all(), supplyForm)
        );
    }
    
    public static Result newSupplies() {
        Form<Supply> filledForm = supplyForm.bindFromRequest();
        if(filledForm.hasErrors()) {
          return badRequest(
            views.html.index.render(Supply.all(), filledForm)
          );
        } else {
          Supply.create(filledForm.get());
          return redirect(routes.Supplies.list());  
        }
    }
    
    static Form<Supply> supplyForm = Form.form(Supply.class);

    
    //public static Result update(Long id) {
      //  return ok(("UPDATE: "+id));
    //}
    
    //public static Result delete(Long id) {
      //  return ok(("DELETE: "+id));
    //}
}
