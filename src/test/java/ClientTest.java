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
   @Test
   public void find_ReturnsClientWithSameId(){
   Client myClient1= new Client("Mercy", 1);
   myClient1.save();
   Client myClient2= new Client("Elvis", 1);
   myClient2.save();
   assertEquals(Client.find(myClient2.getId()), myClient2);
}
@Test
public void update_updatesClientsName(){
    Client myClient= new Client("Mercy", 1); 
    myClient.save();
    myClient.update("elvis");
    assertEquals("elvis", Client.find(myClient.getId()).getName());
}
public void delete_deleteClient(){
    Client myClient= new Client("Mercy", 1); 
    myClient.save();
    myClient.delete();
    assertEquals(null, Client.find(myClient.getId()));
}
}