package controllers.api;

import java.util.List;

import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import models.Meal;
import models.Restaurant;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class RestaurantList extends Controller {
	
	public static Result restaurants() {
		List<Restaurant> restaurants = Restaurant.all();
		if (restaurants != null) {
			return ok(RestaurantList.restaurantsList(restaurants));
		}
		return ok(new ArrayNode(JsonNodeFactory.instance));
	}
	
	public static Result oneRestaurant(){
		JsonNode json = request().body().asJson();
		String id = json.findPath("id").textValue();
		Restaurant restaurant = Restaurant.find(Integer.parseInt(id));
		if (restaurant != null){
			return ok(RestaurantList.restaurantToApp(restaurant));
		}
		return badRequest();
	}

	public static ArrayNode restaurantsList(List <Restaurant> restaurants) {
		ArrayNode array = new ArrayNode(JsonNodeFactory.instance);
	
		for (Restaurant r : restaurants) {
			ObjectNode restaurant = Json.newObject();
			restaurant.put("name", r.name);
			restaurant.put("minOrder", r.minOrder);
			array.add(restaurant);
		}
		return array;
	}
	
	public static ObjectNode restaurantToApp(Restaurant r) {
		
		ObjectNode restaurant = Json.newObject();
		restaurant.put("id", r.id);
		restaurant.put("name", r.name);
		restaurant.put("price", r.minOrder);
		
		return restaurant;
	}
}
