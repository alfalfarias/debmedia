package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {

    public static Result index() {
        return redirect(routes.Supplies.list());
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

}
