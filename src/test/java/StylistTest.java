import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
/**
 * StylistTest
 */
public class StylistTest {
    @Rule
    public DatabaseRule database = new DatabaseRule();
    @Test 
    public void save_assingsIdToIstance(){
        Stylist myStylist = new Stylist("Jay jay", "image");
        myStylist.save();
        Stylist savedStylist = Stylist.all().get(0);
        assertEquals(myStylist.getId(),savedStylist.getId() ) ; 
    }

    @Test
        public void stylist_stylistInstatiatesProperly(){
            Stylist myStylist = new Stylist("Jay jay", "image");
            myStylist.save();
            assertTrue(myStylist instanceof Stylist);
        }
        @Test
        public void all_returnsAllInstancesOfStylist(){
            Stylist myStylist = new Stylist("Jay jay", "image");
            myStylist.save();
            Stylist myStylist1 = new Stylist("Jay jay1", "image");
            myStylist1.save();

            assertEquals(true, Stylist.all().get(0).equals(myStylist));
            assertEquals(true, Stylist.all().get(1).equals(myStylist1));    
        }
        @Test
        public void find_returnsStylistWithSameId(){
            Stylist myStylist = new Stylist("Jay jay", "image");
            myStylist.save();
            Stylist myStylist1 = new Stylist("Jay","image");
            myStylist1.save();  
            assertEquals(Stylist.find(myStylist1.getId()),myStylist1);
        }
        @Test
        public void getClients_RetrivesAllClientsWithTheSameStylist(){
            Stylist myStylist = new Stylist("Jay jay","image");
            myStylist.save();
            Client myClient1= new Client("Mercy", myStylist.getId());
            myClient1.save();
            Client myClient2= new Client("Elvis", myStylist.getId());
            myClient2.save(); 
            Client[] clients= new Client[] {myClient1, myClient2};
            assertTrue(myStylist.getClients().containsAll(Arrays.asList(clients)));

        }
        @Test
        public void update_updatesStylistName(){
            Stylist myStylist = new Stylist("Jay jay", "image");
            myStylist.save();
            myStylist.update("elvis");
            assertEquals("elvis", Stylist.find(myStylist.getId()).getName()); 
        }
        @Test
        public void delete_deletesStylist_null(){
            Stylist myStylist = new Stylist("Jay jay","image");
            myStylist.save();
            myStylist.delete();
            assertEquals(null, Stylist.find(myStylist.getId()));  
        }
        
    
  
}