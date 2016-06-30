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

        String nameStructure = name.substring(0,1).toUpperCase()+name.substring(1).toLowerCase();
        String weaponStructure = weapon.substring(0,1).toUpperCase()+weapon.substring(1).toLowerCase();
        try {
            int i = rs.getInt("id");
            i++;
            rs.last();

            rs.moveToInsertRow();
            rs.updateInt("ID",i);
            rs.updateString("Name", nameStructure);
            rs.updateString("Weapon", weaponStructure);

            rs.insertRow();

            st.close();
            rs.close();

        } catch (SQLException e) {
            System.out.println(e+" Error Adding");
        }
    }

    public void updateRow(String newName,String newWeapon,String originalName) {
        String sql = "UPDATE HEROES SET NAME=?, WEAPON=? WHERE NAME=?";
        String newNameStructure =  newName.substring(0,1).toUpperCase()+newName.substring(1).toLowerCase();
        try {
            pst = con.prepareStatement(sql);

            String dataName= "";

            while(rs.next()) {
                if(rs.getString("name").equalsIgnoreCase(originalName)) {
                    dataName = originalName.substring(0,1).toUpperCase()+originalName.substring(1).toLowerCase();
                }
            }

            pst.setString(3,dataName);
            pst.setString(1,newNameStructure);
            pst.setString(2,newWeapon);

            pst.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e+" Error Updating");
        }
    }

    public void deleteRow(String name) {
        String sql = "DELETE HEROES WHERE NAME= ?";

        String nameStructure = name.substring(0,1).toUpperCase()+name.substring(1).toLowerCase();
        try {

            pst = con.prepareStatement(sql);
            pst.setString(1,nameStructure);

            pst.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e+" Error Updating");
        }
    }

}
