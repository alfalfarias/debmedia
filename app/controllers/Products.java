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

import com.avaje.ebean.Ebean;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import javax.persistence.OptimisticLockException;
import models.Product;
import play.libs.Json;
import play.mvc.Controller;
import static play.mvc.Controller.request;
import play.mvc.Result;
import static play.mvc.Results.badRequest;
import static play.mvc.Results.ok;

public class Products extends Controller {
    public static Result list() {
        ObjectNode result = Json.newObject();
        
        result.put("message", "OK");
        result.put("products", Json.toJson(Product.all()));
        return ok(result);
    }
    
    /**
     * Handle the 'new product form' submission 
     * @return 
     */
    public static Result save() {
        JsonNode json = request().body().asJson();
        ObjectNode result = Json.newObject();
        Product product = null;
        Ebean.beginTransaction();  
        if (json == null){
            result.put("message", "Expecting Json data");
            return badRequest(result);
        }
        try {  
            product = (Product) Json.fromJson(json, Product.class);
            Ebean.save(product); 
            Ebean.commitTransaction();  
        } catch(OptimisticLockException e){
            Ebean.rollbackTransaction();
            Ebean.endTransaction(); 
            result.put("message", e.getMessage());
            return badRequest(result);
        } finally {
            Ebean.endTransaction();  
        }
        result.put("message", "OK");
        result.put("product", Json.toJson(product));
        return ok(result);
        
    }
    
    /**
     * Handle the 'edit form' submission 
     *
     * @param id Id of the product to edit
     * @return 
     */
    public static Result retrieve(Long id) {
        ObjectNode result = Json.newObject();
        Product product = Product.find.byId(id);
        if (product == null){
            result.put("message", "product not found");
            return badRequest(result);
        }
        result.put("product", Json.toJson(product));
        result.put("message", "OK");
        return ok(result);
    }
    
    /**
     * Handle the 'edit form' submission 
     *
     * @param id Id of the product to edit
     * @return 
     */
    public static Result update(Long id) {
        JsonNode json = request().body().asJson();
        ObjectNode result = Json.newObject();
        Product product = Product.find.byId(id);
        if (json == null){
            result.put("message", "Expecting Json data");
            return badRequest(result);
        }
        if (product == null){
            result.put("message", "product not found");
            return badRequest(result);
        }
        product.name = json.findPath("name").textValue();
        product.price = json.findPath("price").doubleValue();
        result.put("message", "OK");
        result.put("product", Json.toJson(product));
        return ok(result);
    }
    
    /**
     * Handle product deletion
     * @param id
     * @return 
     */
    public static Result delete(Long id) {
        ObjectNode result = Json.newObject();
        Product product = Product.find.byId(id);
        if (product == null){
            result.put("message", "product not found");
            return badRequest(result);
        }
        product.delete();
        result.put("message", "OK");
        result.put("product", Json.toJson(product));
        return ok(result);
    }
}