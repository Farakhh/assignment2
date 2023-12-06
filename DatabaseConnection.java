import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseConnection {
    public static void main(String[] args) {
        String url="jdbc:postgresql://localhost:5432/2assignment";
        String username="postgres";
        String password="5864";
        try {



            Connection connection= DriverManager.getConnection(url,username,password);

            Statement statement=connection.createStatement();

            ResultSet resultSet= statement.executeQuery( "select * from customers");
            while (resultSet.next()){
                System.out.println(resultSet.getInt(1)+" "+resultSet.getString(2)+" "+resultSet.getString(3)+" "+resultSet.getString(4));
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
}