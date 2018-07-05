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
import models.ProductSupply;
import models.Supply;
import play.data.Form;
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
        
        Form<Product> productForm = Form.form(Product.class).bind(request().body().asJson());
        if(productForm.hasErrors()){
            return badRequest(productForm.errorsAsJson());
        }
        Product product = (Product) Json.fromJson(json, Product.class);
        Product productTable = Product.find.where().eq("name", product.name).setMaxRows(1).findUnique();
        if (productTable != null){
            result.put("message", "The Product '"+product.name+"' already exists");
            return badRequest(result);
        }
        try {
            Ebean.beginTransaction(); 
            for (ProductSupply productSupply: product.productSupplies){
                Supply supply = Supply.find.byId(productSupply.id);
                if (supply == null){
                    result.put("message", "Supply '"+productSupply.name+"' does not exist in stock");
                    result.withArray("productSupplies").add("Supply '"+productSupply.name+"' does not exist in stock");
                    return badRequest(result);
                }
                productSupply.id = null;
                productSupply.name = supply.name;
                productSupply.quantity = 1;
                supply.save();
            }
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
        
        Form<Product> productForm = Form.form(Product.class).bind(request().body().asJson());
        if(productForm.hasErrors()){
            return badRequest(productForm.errorsAsJson());
        }
        Product product = (Product) Json.fromJson(json, Product.class);
        if (product == null){
            result.put("message", "Product not found");
            return badRequest(result);
        }
        Product productTable = Product.find.where().eq("name", product.name).ne("id",id).setMaxRows(1).findUnique();
        if (productTable != null){
            result.put("message", "The Product '"+product.name+"' already exists");
            return badRequest(result);
        }
        
        try {
            Ebean.beginTransaction(); 
            for (ProductSupply productSupply: product.productSupplies){
                Supply supplyTable = Supply.find.where().eq("name", productSupply.name).setMaxRows(1).findUnique();
                ProductSupply productSupplyTable = ProductSupply.find.where().eq("name", productSupply.name).setMaxRows(1).findUnique();
                if (supplyTable == null && productSupplyTable == null){
                    result.put("message", "The Supply '"+productSupply.name+"' does not exist");
                    result.withArray("productSupplies").add("The Supply '"+productSupply.name+"' does not exist");
                    return badRequest(result);
                }
                productSupply.id = null;
            }
            productTable = Product.find.byId(id);
            Ebean.delete(productTable);
            product.id = id;
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
        result.put("product", Json.toJson(product));
        result.put("message", "OK");
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
            result.put("message", "Product not found");
            return badRequest(result);
        }
        product.delete();
        result.put("message", "OK");
        result.put("product", Json.toJson(product));
        return ok(result);
    }
}