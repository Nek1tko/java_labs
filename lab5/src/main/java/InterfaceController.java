import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.sql.ResultSet;
import java.sql.SQLException;


public class InterfaceController {
    private GraphicalUserInterface graphicalUserInterface;
    private ObservableList<Product> mainList = FXCollections.observableArrayList();
    private ObservableList<Product> filteredList = FXCollections.observableArrayList();

    @FXML
    private TextField filterField;
    @FXML
    private TextField titleField;
    @FXML
    private TextField costField;
    @FXML
    private TableView<Product> tableView;
    @FXML
    private TableColumn<Product, Integer> id;
    @FXML
    private TableColumn<Product, Integer> prodid;
    @FXML
    private TableColumn<Product, String> title;
    @FXML
    private TableColumn<Product, Double> cost;
    @FXML
    private CheckBox filter;

    public void init(GraphicalUserInterface graphicalUserInterface) {
        this.graphicalUserInterface = graphicalUserInterface;
        filterField.textProperty().addListener((observableValue, oldValue, newValue) -> updateTable());
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        prodid.setCellValueFactory(new PropertyValueFactory<>("prodid"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        cost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        cost.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        cost.setOnEditCommit((TableColumn.CellEditEvent<Product, Double> event) -> {
            TablePosition<Product, Double> position = event.getTablePosition();
            int row = position.getRow();
            Product product = event.getTableView().getItems().get(row);
            Double newValue = event.getNewValue();
            if (newValue != null) {
                product.setCost(newValue);
            }

        });
        show(DataBase.instance().getProducts());
    }

    private void show(ResultSet resultSet) {
        mainList.clear();
        if (resultSet != null) {
            try {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    int prodid = resultSet.getInt("prodid");
                    String title = resultSet.getString("title");
                    double cost = resultSet.getDouble("cost");
                    mainList.add(new Product(id, prodid, title, cost));
                }
                resultSet.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        tableView.setItems(mainList);
    }

    private void updateTable() {
        filteredList.clear();
        for (Product product : mainList) {
            if (match(product.getTitle(), filterField.getText())) {
                filteredList.add(product);
            }
        }
        tableView.setItems(filteredList);
    }

    private boolean match(String tableString, String filterString) {
        if (filterString != null && tableString != null) {
            return tableString.toLowerCase().contains(filterString.toLowerCase());
        }
        return false;
    }

    @FXML
    private void add() {
        String title = titleField.getText();
        double cost;
        try {
            cost = Double.parseDouble(costField.getText());
        }
        catch (NumberFormatException e) {
            graphicalUserInterface.setUpErrorStage("Cost must be a number");
            titleField.clear();
            costField.clear();
            return;
        }

        titleField.clear();
        costField.clear();
        try {
            DataBase.instance().addProduct(title, cost);
            ResultSet resultSet = DataBase.instance().getProduct(title);
            if (resultSet != null) {
                resultSet.next();
                int id  = resultSet.getInt("id");
                int prodid = resultSet.getInt("prodid");
                resultSet.close();
                tableView.getItems().add(new Product(id, prodid, title, cost));
            }
        }
        catch (SQLException e) {
            graphicalUserInterface.setUpErrorStage(e.getMessage());
        }
    }

    @FXML
    private void delete() {
        Product product = tableView.getItems().remove(tableView.getSelectionModel().getSelectedIndex());
        if (product != null) {
            DataBase.instance().deleteProduct(product.getTitle());
        }
    }

    @FXML
    private void filter() {
        if (filter.isSelected()) {
            graphicalUserInterface.setUpFilterStage();
        }
        else {
            graphicalUserInterface.closeFilterStage();
            show(DataBase.instance().getProducts());
        }
    }

    public void filterCommand(double leftBorder, double rightBorder) {
        try {
            show(DataBase.instance().getProductsInPriceRange(leftBorder, rightBorder));
            graphicalUserInterface.closeFilterStage();
        }
        catch (SQLException e) {
            graphicalUserInterface.setUpErrorStage(e.getMessage());
        }
    }

    public void switchCheckBox(boolean property) {
        filter.setSelected(property);
    }
}
