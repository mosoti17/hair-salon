import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;
/**
 * StylistTest
 */
public class StylistTest {
    @Rule
    public DatabaseRule database = new DatabaseRule();

    @Test
        public void stylist_stylistInstatiatesProperly(){
            Stylist myStylist = new Stylist("Jay jay");
            myStylist.save();
            assertTrue(myStylist instanceof Stylist);
        }
        @Test
        public void all_returnsAllInstancesOfStylist(){
            Stylist myStylist = new Stylist("Jay jay");
            myStylist.save();
            assertEquals(myStylist.getName(), Stylist.all().get(0).getName());  
        }
    
  
}