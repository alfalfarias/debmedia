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
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import static play.mvc.Results.ok;

public class Supplies extends Controller {
    
    public static Result list() {
        return ok(
            Json.toJson(Supply.all())
        );
    }
    
    public static Result update(Long id) {
        return ok(("UPDATE: "+id));
    }
    
    public static Result delete(Long id) {
        return ok(("DELETE: "+id));
    }
}
