import java.sql.ResultSet;
import java.sql.SQLException;

public class DataOutput {
    static public void showTable(ResultSet resultSet) {
        if (resultSet != null) {
            System.out.println("prodid title cost");
            try {
                while (resultSet.next()) {
                    int prodid = resultSet.getInt("prodid");
                    String title = resultSet.getString("title");
                    double cost = resultSet.getDouble("cost");
                    System.out.println(prodid + " " + title + " " + cost);
                }
                resultSet.close();
            }
            catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        else {
            System.out.println("There are no records");
        }
    }

    static public void showPrice(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.next();
                System.out.println("Product with title " + resultSet.getString("title") + " has price " + resultSet.getInt("cost"));
                resultSet.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        else {
            System.out.println("There is no product with such title");
        }
    }

}
