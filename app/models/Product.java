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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import play.data.validation.Constraints;
import play.db.ebean.Model;

@Entity
@Table(name="products")
public class Product extends Model {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;
    @Column(nullable=false, length=500)
    @Constraints.Required
    @Constraints.MaxLength(500)
    public String name;
    @Column(nullable=false, length=500)
    @Constraints.Required
    @Constraints.MaxLength(500)
    public String description;
    @Constraints.Min(value=1, message = "El producto no puede ser gratis")
    @Column(nullable=false)
    @Constraints.Required
    public double price;
    @OneToMany(mappedBy = "product", cascade=CascadeType.ALL)
    @JsonIgnoreProperties("product")
    @Valid
    @Constraints.Required
    public List<ProductSupply> productSupplies;

    public static List<Product> all() {
      return find.all();
    }
    
    public static void create(Product product) {
      product.save();
    }
    
    public static Model.Finder<Long,Product> find = new Model.Finder(
        Long.class, Product.class
    );
}
