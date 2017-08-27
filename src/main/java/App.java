import java.util.HashMap;
import java.util.Map;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;
import java.util.List;
import java.util.ArrayList;
public class App{
  public static void main(String[] args){
    ProcessBuilder process = new ProcessBuilder();
       Integer port;

       // This tells our app that if Heroku sets a port for us, we need to use that port.
       // Otherwise, if they do not, continue using port 4567.

       if (process.environment().get("PORT") != null) {
           port = Integer.parseInt(process.environment().get("PORT"));
       } else {
           port = 4567;
       }

       setPort(port);
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
        String image = request.queryParams("image");
        Stylist newStylist = new Stylist(name, image);
        newStylist.save();
        model.put("stylists", Stylist.all());
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

      post("/stylists/:id/delete", (request, response) -> {
        HashMap<String, Object> model = new HashMap<String, Object>();
        Stylist stylist = Stylist.find(Integer.parseInt(request.params(":id")));
        stylist.delete();
        model.put("stylist", stylist);
        model.put("stylists", Stylist.all());
        model.put("template", "templates/index.vtl");
        return new ModelAndView(model, layout);
      }, new VelocityTemplateEngine());
      get("/stylists/:id/clients/:clientid", (request, response) -> {
        Map<String, Object> model = new HashMap<String, Object>();
        Stylist stylist = Stylist.find(Integer.parseInt(request.params(":id")));
        Client client = Client.find(Integer.parseInt(request.params(":clientid")));
        model.put("stylist", stylist);
        model.put("client",client);
        model.put("template", "templates/client.vtl");
        return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
    post("/stylists/:id/clients/:clientid", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Client client = Client.find(Integer.parseInt(request.params(":clientid")));
      String name = request.queryParams("name");
      Stylist stylist = Stylist.find(client.getStylistId());
      client.update(name);
      String url = String.format("/stylists/%d/clients/%d", stylist.getId(), client.getId());
      response.redirect(url);
      return new ModelAndView(model, layout);
     }, new VelocityTemplateEngine());
     post("/stylists/:id/clients/:clientid/delete", (request, response) -> {
       Map<String, Object> model = new HashMap<String, Object>();
       Client client = Client.find(Integer.parseInt(request.params(":clientid")));
       Stylist stylist = Stylist.find(client.getStylistId());
       client.delete();
       String url = String.format("/stylists/%d", stylist.getId());
       response.redirect(url);
       return new ModelAndView(model, layout);
      }, new VelocityTemplateEngine());
  }
}
