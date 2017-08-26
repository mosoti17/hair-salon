import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;
/**
* Stylist
*/
public class Stylist {
   private String name;
   private String image;
   private int id;
   

   public Stylist (String name,String image ){
     this.name =name; 
     this.image = image;
   }
   public String getName(){
       return name;
   }
   public int getId(){
       return id;
   }
   public String getImage(){
     return image;
   }
   public void save() {
  try(Connection con = DB.sql2o.open()) {
    String sql = "INSERT INTO stylists(name,image) VALUES (:name, :image)";
    this.id = (int) con.createQuery(sql, true)
      .addParameter("name", this.name)
      .addParameter("image", this.image)
      .executeUpdate()
      .getKey();
    }
 }
public static List<Stylist> all() {
    String sql = "SELECT id, name ,image FROM stylists";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Stylist.class);
    }
  }
  @Override
  public boolean equals(Object otherStylist){
    if (!(otherStylist instanceof Stylist)) {
      return false;
    }else{
      Stylist newStylist =(Stylist) otherStylist;
        return this.getName().equals(newStylist.getName()) &&
        this.getId() == newStylist.getId();
    }
  }
  public static Stylist find(int id){
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM stylists where id=:id";
      Stylist stylist = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Stylist.class);
      return stylist;
    }
  }
  public List<Client> getClients(){
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM clients where stylistid=:id";
      return con.createQuery(sql)
        .addParameter("id", this.id)
        .executeAndFetch(Client.class);
    }
  }
  public void update(String name){
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE stylists SET name = :name WHERE id = :id";
      con.createQuery(sql)
        .addParameter("name", name)
        .addParameter("id", id)
        .executeUpdate();
    }
  }
  public void delete(){
    try(Connection con = DB.sql2o.open()) {
      String cat_sql = "DELETE FROM stylists WHERE id = :id";
      String task_sql = "DELETE FROM CLIENTS WHERE stylistid=:id";
      con.createQuery(cat_sql)
        .addParameter("id", id)
        .executeUpdate();
        con.createQuery(task_sql)
          .addParameter("id", id)
         .executeUpdate();
      }
  }
}