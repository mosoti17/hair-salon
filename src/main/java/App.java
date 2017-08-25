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
        model.put("stylist", stylist);
        model.put("template", "templates/stylist.vtl");
        return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
    post("/stylists/:id", (request, response) -> {
        Map<String, Object> model = new HashMap<String, Object>();
        try{
        Stylist stylistid = Stylist.find(Integer.parseInt(request.queryParams("stylistid")));
        String name = request.queryParams("name");
        Client newClient = new Client(name,stylistid.getId());
        newClient.save();
        String url = String.format("/stylists/%d", stylistid.getId());
        response.redirect(url); 
        } catch(NumberFormatException e ) {
          Stylist stylist = Stylist.find(Integer.parseInt(request.params(":id")));
          String name = request.queryParams("name");
          stylist.update(name);
          String url = String.format("/stylists/%d", stylist.getId());
          response.redirect(url);
        }
        model.put("template", "templates/stylist.vtl");
        return new ModelAndView(model, layout);
      }, new VelocityTemplateEngine());
     
  }
}
