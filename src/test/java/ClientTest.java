import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;

public class ClientTest {
    @Rule
    public DatabaseRule database = new DatabaseRule();
   @Test 
   public void client_clientInstatiatesCorrectly(){
       Client myClient= new Client("Mercy", 1);
       assertTrue(myClient instanceof Client);
   } 
   @Test
   public void all_returnsAllInstancesOfClients(){
    Client myClient1= new Client("Mercy", 1);
    myClient1.save();
    Client myClient2= new Client("Elvis", 1);
    myClient2.save();
    assertEquals(true, Client.all().get(0).equals(myClient1));
    assertEquals(true, Client.all().get(1).equals(myClient2));
   }

}