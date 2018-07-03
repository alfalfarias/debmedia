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

import java.util.HashMap;
import models.Product;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public class Products extends Controller {
    public static Result list() {
        return ok(
            Json.toJson(Product.all())
        );
    }
        
    public static Result index() {
        HashMap<String, Object> result = new HashMap<String, Object>(){
            {
                put("str", "String");
                put("int", 123);
            }
        };
        return ok(Json.toJson(result));
    }
}