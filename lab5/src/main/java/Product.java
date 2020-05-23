public class Product {
    private int id;
    private int prodid;
    private String title;
    private double cost;

    Product(int id, int prodid, String title, double cost) {
        this.id = id;
        this.prodid = prodid;
        this.title = title;
        this.cost = cost;
    }

    public int getId() {
        return id;
    }

    public int getProdid() {
        return prodid;
    }

    public String getTitle() {
        return title;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
