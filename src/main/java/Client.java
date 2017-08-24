import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;
/**
 * Client
 */
public class Client {
    private String name;
    private int stylistId;
    private int id;

    public Client (String name, int stylistId) {
        this.name =name;
        this.stylistId= stylistId;
    }
    public String getName(){
        return name;
    }
    public int getId(){
        return id;
    }
    public int getStylistId(){
        return stylistId;
    }
    public void save() {
        try(Connection con = DB.sql2o.open()) {
          String sql = "INSERT INTO clients(name, stylistId) VALUES (:name, :stylistId)";
          this.id = (int) con.createQuery(sql, true)
            .addParameter("name", this.name)
            .addParameter("stylistId", this.stylistId)
            .executeUpdate()
            .getKey();
          }
    }
    public static List<Client> all() {
        String sql = "SELECT id,name, stylistId FROM clients";
        try(Connection con = DB.sql2o.open()) {
          return con.createQuery(sql).executeAndFetch(Client.class);
        }
      }
}