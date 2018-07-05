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

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Product;
import models.Supply;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import static play.mvc.Results.badRequest;
import static play.mvc.Results.ok;

public class Supplies extends Controller {
    
    public static Result list() {
        ObjectNode result = Json.newObject();
        result.put("message", "OK");
        result.put("supplies", Json.toJson(Supply.all()));
        return ok(result);
    }
    
    /**
     * Handle the 'new supply form' submission 
     * @return 
     */
    public static Result save() {
        JsonNode json = request().body().asJson();
        ObjectNode result = Json.newObject();
        
        Form<Supply> supplyForm = Form.form(Supply.class).bind(request().body().asJson());
        if(supplyForm.hasErrors()){
            return badRequest(supplyForm.errorsAsJson());
        }
        Supply supply = (Supply) Json.fromJson(json, Supply.class);
        
        Supply supplyTable = Supply.find.where().eq("name", supply.name).setMaxRows(1).findUnique();
        if (supplyTable != null){
            result.put("message", "The Supply name '"+supply.name+"' already exists");
            return badRequest(result);
        }
        
        supply.save();
        result.put("message", "OK");
        result.put("supply", Json.toJson(supply));
        return ok(result);
    }
    
    /**
     * Handle the 'edit form' submission 
     *
     * @param id Id of the supply to edit
     * @return 
     */
    public static Result retrieve(Long id) {
        ObjectNode result = Json.newObject();
        Supply supply = Supply.find.byId(id);
        if (supply == null){
            result.put("message", "Supply not found");
            return badRequest(result);
        }
        result.put("supply", Json.toJson(supply));
        result.put("message", "OK");
        return ok(result);
    }
    
    /**
     * Handle the 'edit form' submission 
     *
     * @param id Id of the supply to edit
     * @return 
     */
    public static Result update(Long id) {
        JsonNode json = request().body().asJson();
        ObjectNode result = Json.newObject();
        Supply supply = Supply.find.byId(id);
        if (supply == null){
            result.put("message", "Supply not found");
            return badRequest(result);
        }
        Form<Supply> supplyForm = Form.form(Supply.class).bind(request().body().asJson());
        if(supplyForm.hasErrors()){
            return badRequest(supplyForm.errorsAsJson());
        }
        supply.name = json.findPath("name").textValue();
        supply.quantity = json.findPath("quantity").intValue();
        Supply supplyTable = Supply.find.where().eq("name", supply.name).ne("id", id).setMaxRows(1).findUnique();
        if (supplyTable != null){
            result.put("message", "The Supply name '"+supply.name+"' already exists");
            return badRequest(result);
        }
        supply.update();
        result.put("message", "OK");
        result.put("supply", Json.toJson(supply));
        return ok(result);
    }
    
    /**
     * Handle supply deletion
     * @param id
     * @return 
     */
    public static Result delete(Long id) {
        ObjectNode result = Json.newObject();
        Supply supply = Supply.find.byId(id);
        if (supply == null){
            result.put("message", "Supply not found");
            return badRequest(result);
        }
        supply.delete();
        result.put("message", "OK");
        result.put("supply", Json.toJson(supply));
        return ok(result);
    }
}