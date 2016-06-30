import org.junit.*;

import static org.junit.Assert.*;

/**
 * Created by jammis01 on 6/29/2016.
 */
public class MainTest {

    Main main = new Main();
    @Test
    public void add() throws Exception {
         main.start();
         main.add("Foxis","Guns");
    }

    @Test
    public void updateRow() throws Exception {
         main.DBconnection();

         main.updateRow("Steve","Sword","Gaia");

    }

    @Test
    public void deleteRow() throws Exception {
        main.start();
        main.deleteRow("steve");
    }

}