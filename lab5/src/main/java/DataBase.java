import java.sql.*;
import java.util.Random;

public class DataBase {

    static public DataBase instance() {
        while(dataBase == null) {
            try {
                dataBase = new DataBase();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return dataBase;
    }

    private static DataBase dataBase = null;
    private static String name = "products";
    private static int prodid = 1;
    private Connection connection;

    private DataBase() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/lab4?useSSL=false";
        String username = "root";
        String password = "1234";
        this.connection = DriverManager.getConnection(url, username, password);
        createProductsTable(10);
    }

    private void createProductsTable(int N)  {
        String dropQuery = "SHOW TABLES LIKE '" + name + "'";
        try {
            ResultSet resultSet = executeQuery(dropQuery);

            if (resultSet != null) {
                if (resultSet.next()) {
                    clearTable();
                } else {
                    String createQuery = "CREATE TABLE " + name +
                            "(" +
                            "id INT NOT NULL AUTO_INCREMENT, " +
                            "prodid INT NOT NULL, " +
                            "title VARCHAR(90) NOT NULL UNIQUE  , " +
                            "cost DOUBLE NOT NULL," +
                            "PRIMARY KEY (id)" +
                            ");";
                    executeUpdate(createQuery);
                }
                resultSet.close();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        String title = "product";
        Random random = new Random();

        for (int i = 1; i <= N; ++i) {
            int titleNumber = random.nextInt(1001);
            double cost = random.nextInt(10001);
            try {
                addProduct(title + titleNumber, cost);
            }
            catch (SQLException e) {
                --i;
            }
        }

    }

    private void executeUpdate(String query) throws SQLException{
        Statement statement;
        statement = connection.createStatement();
        statement.executeUpdate(query);
        statement.close();
    }

    private ResultSet executeQuery(String query) throws SQLException {
        Statement statement;
        statement = connection.createStatement();
        return statement.executeQuery(query);
    }

    private void clearTable() {
        String clearQuery = "TRUNCATE TABLE " + name;
        try {
            executeUpdate(clearQuery);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addProduct(String title, double cost) throws SQLException{
        String addQuery = "INSERT INTO " +
                name + " (prodid, title, cost) VALUES (" +
                prodid + ", '" + title + "', " + cost + ')';
        executeUpdate(addQuery);

        ++prodid;
    }

    public void deleteProduct(String title) {
        String deleteQuery = "DELETE FROM " + name + " WHERE title = '" + title + "'";
        try {
            executeUpdate(deleteQuery);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public ResultSet getProducts() {
        String showQuery = "SELECT * FROM " + name;
        try {
            return executeQuery(showQuery);
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public ResultSet getPriceByTitle(String title) {
        String priceQuery = "SELECT cost FROM " + name + " WHERE title ='" + title + "'";
        try {
            return executeQuery(priceQuery);
        }
        catch (SQLException e) {
            System.out.println();
        }

        return null;
    }

    public void changePrice(String title, double newPrice) {
        String changeQuery = "UPDATE " + name + " SET cost = " + newPrice + " WHERE title = '" + title + "'";
        try {
            executeUpdate(changeQuery);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public ResultSet getProductsInPriceRange(double leftBorder, double rightBorder) throws SQLException {
        if (leftBorder > rightBorder) {
            double temp = leftBorder;
            leftBorder = rightBorder;
            rightBorder = temp;
        }
        String showQuery = "SELECT * FROM " + name +
                " WHERE cost BETWEEN " + leftBorder + " AND " + rightBorder;
        return executeQuery(showQuery);
    }

    public ResultSet getProduct(String title) throws SQLException {
        String getQuery = "SELECT * FROM " + name + " WHERE title = '" + title + "'";
        return executeQuery(getQuery);
    }
}
