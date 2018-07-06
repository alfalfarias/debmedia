/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.avaje.ebean.Ebean;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import javax.persistence.OptimisticLockException;
import models.Product;
import models.ProductSupply;
import models.Sale;
import models.SaleProductSupply;
import models.Supply;
import play.data.Form;
import play.libs.Json;
import static play.mvc.Controller.request;
import play.mvc.Result;
import static play.mvc.Results.badRequest;
import static play.mvc.Results.ok;

/**
 *
 * @author simon
 */
public class Sales {
    public static Result list() {
        ObjectNode result = Json.newObject();
        
        result.put("message", "OK");
        result.put("sales", Json.toJson(Sale.all()));
        return ok(result);
    }
    
    /**
     * Handle the 'new product form' submission 
     * @return 
     */
    public static Result save() {
        JsonNode json = request().body().asJson();
        ObjectNode result = Json.newObject();
        
        Form<Sale> SaleForm = Form.form(Sale.class).bind(request().body().asJson());
        if(SaleForm.hasErrors()){
            return badRequest(SaleForm.errorsAsJson());
        }
        Sale sale = (Sale) Json.fromJson(json, Sale.class);
        Product product = Product.find.where().eq("name", sale.saleProduct.name).setMaxRows(1).findUnique();
        if (product == null){
            result.put("message", "El producto '"+sale.saleProduct.name+"' no existe");
            result.withArray("saleProduct").add("El producto '"+sale.saleProduct.name+"' no existe");
            return badRequest(result);
        }
        sale.saleProduct.id = null;
        sale.saleProduct.name = product.name;
        sale.saleProduct.description = product.description;
        sale.saleProduct.price = product.price;
        try {
            Ebean.beginTransaction(); 
            for (ProductSupply productSupply: product.productSupplies){
                Supply supply = Supply.find.where().eq("name", productSupply.name).setMaxRows(1).findUnique();
                if (supply == null){
                    result.put("message", "El insumo '"+productSupply.name+"' es parte del producto y no existe en stock");
                    result.withArray("productSupplies").add("El insumo '"+productSupply.name+"' es parte del producto y no existe en stock");
                    return badRequest(result);
                }
                if (supply.quantity < (sale.saleProduct.quantity * productSupply.quantity)){
                    result.put("message", "Se necesitan "+sale.saleProduct.quantity * productSupply.quantity+" unidades del insumo '"+productSupply.name+"' para el producto '"+product.name+"' y solo existen "+supply.quantity+" en stock");
                    result.withArray("productSupplies").add("Se necesitan "+sale.saleProduct.quantity * productSupply.quantity+" unidades del insumo '"+productSupply.name+"' para el producto '"+product.name+"' y solo existen "+supply.quantity+" en stock");
                    return badRequest(result);
                }
                supply.quantity -= productSupply.quantity;
                Ebean.save(supply);
                SaleProductSupply saleProductSupply = new SaleProductSupply(null, supply.name, 1);
                sale.saleProduct.saleProductSupplies.add(saleProductSupply);
            }
            Ebean.save(sale);
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
        result.put("sale", Json.toJson(sale));
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
        Sale sale = Sale.find.byId(id);
        if (sale == null){
            result.put("message", "No se encontró la venta");
            return badRequest(result);
        }
        result.put("sale", Json.toJson(sale));
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
        
        result.put("Sales", Json.toJson(json));
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
        Sale sale = Sale.find.byId(id);
        if (sale == null){
            result.put("message", "No se encontró la venta");
            return badRequest(result);
        }
        sale.delete();
        result.put("message", "OK");
        result.put("sale", Json.toJson(sale));
        return ok(result);
    }
}
