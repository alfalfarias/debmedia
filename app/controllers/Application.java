package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {

    public static Result index() {
        return redirect(routes.Application.showSupplies());
    }

    public static Result showSupplies() {
    	return ok(
          views.html.supplies.render()
        );
    }

    public static Result showProducts() {
    	return ok(
          views.html.products.render()
        );
    }

    public static Result showSales() {
        return ok(
          views.html.sales.render()
        );
    }

}
