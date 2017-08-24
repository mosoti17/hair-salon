import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;
/**
* Stylist
*/
public class Stylist {
   private String name;
   private int id;
   

   public Stylist (String name) {
     this.name =name; 
   }
   public String getName(){
       return name;
   }
   public int getId(){
       return id;
   }
   public void save() {
  try(Connection con = DB.sql2o.open()) {
    String sql = "INSERT INTO stylists(name) VALUES (:name)";
    this.id = (int) con.createQuery(sql, true)
      .addParameter("name", this.name)
      .executeUpdate()
      .getKey();
    }
 }
public static List<Stylist> all() {
    String sql = "SELECT id, name FROM stylists";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Stylist.class);
    }
  }
}