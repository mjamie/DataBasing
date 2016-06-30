import java.sql.*;

public class Main  {

    Statement st = null;
    Connection con=null;

    static ResultSet rs= null;
    PreparedStatement pst= null;

    public void DBconnection() {

        try{
            con = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/XE","HR","1234");
            st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            String sql = "SELECT HEROES.* FROM  HEROES";
            rs = st.executeQuery(sql);

        }
        catch(Exception e){
            System.out.println(e+" Error 1");
        }

    }

    public void start() {
        DBconnection();

    }

    public void add(String name, String weapon) {
        String sql = "INSERT INTO HEROES(NAME,WEAPON,ID) VALUES(?,?,?) ";
        int i = 1;
        String nameStructure = capitalize(name);
        String weaponStructure = capitalize(weapon);

        try {

            pst = con.prepareStatement(sql);
            while(rs.next()) {
                i++;
            }

            pst.setString(1,nameStructure);
            pst.setString(2,weaponStructure);
            pst.setInt(3,i);

            pst.executeUpdate();

            rs.close();

        } catch (SQLException e) {
            System.out.println(e+" Error Adding");
        }
    }

    public void updateRow(String newName,String newWeapon,String originalName) {
        String sql = "UPDATE HEROES SET NAME=?, WEAPON=? WHERE NAME=?";
        String newNameStructure =  capitalize(newName);
        try {
            pst = con.prepareStatement(sql);

            String dataName= "";

            while(rs.next()) {
                if(rs.getString("name").equalsIgnoreCase(originalName)) {
                    dataName = capitalize(originalName);
                }
            }

            pst.setString(3,dataName);
            pst.setString(1,newNameStructure);
            pst.setString(2,newWeapon);

            pst.executeUpdate();

            rs.close();

        } catch (SQLException e) {
            System.out.println(e+" Error Updating");
        }
    }

    public void deleteRow(String name) {
        String sql = "DELETE HEROES WHERE NAME= ?";

        String nameStructure = capitalize(name);
        try {

            pst = con.prepareStatement(sql);
            pst.setString(1,nameStructure);

            pst.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e+" Error Updating");
        }
    }

    public String capitalize(String word){
        String newWord = word.substring(0,1).toUpperCase()+word.substring(1).toLowerCase();
        return newWord;
    }

}
