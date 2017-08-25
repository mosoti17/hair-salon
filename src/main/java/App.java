import java.util.HashMap;
import java.util.Map;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;
import java.util.List;
import java.util.ArrayList;
public class App{
  public static void main(String[] args){
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";
    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("stylists", Stylist.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
    post("/", (request, response) -> {
        Map<String, Object> model = new HashMap<String, Object>();
        String name = request.queryParams("name");
        Stylist newStylist = new Stylist(name);
        newStylist.save();
        model.put("template", "templates/index.vtl");
        return new ModelAndView(model, layout);
      }, new VelocityTemplateEngine());
    get("/stylists/:id", (request, response) -> {
        Map<String, Object> model = new HashMap<String, Object>();
        Stylist stylist = Stylist.find(Integer.parseInt(request.params(":id")));
        model.put("stylists", stylist);
        model.put("template", "templates/category.vtl");
        return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

  }
}
