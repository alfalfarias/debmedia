/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.forms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.validation.Valid;
import play.data.validation.Constraints;

/**
 *
 * @author simon
 */
public class ProductForm {
    @Constraints.Required
    public String name;
    @Constraints.Required
    public String description;
    @Constraints.Min(value=1, message = "The product cant be free")
    public double price;
    
    @Constraints.Required
    @Valid
    public List<SupplyForm> suppliesForm;
    
    
    public static class SupplyForm {
        @Constraints.Required
        public Long id;
        @Constraints.Required
        public String name;
        @Constraints.Required
        public Double price;
    }
}
