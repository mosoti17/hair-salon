import org.junit.rules.ExternalResource;
import org.sql2o.*;

public class DatabaseRule extends ExternalResource {
    
      @Override
     protected void before() {
       DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/hair_salon_test", null, null);
     }
    
     @Override
     protected void after() {
       try(Connection con = DB.sql2o.open()) {
         String deleteStylistQuery = "DELETE FROM stylists *;";
         String deleteClientQuery = "DELETE FROM clients*;";
         con.createQuery(deleteStylistQuery).executeUpdate();
         con.createQuery(deleteClientQuery).executeUpdate();
       }
     }
    }