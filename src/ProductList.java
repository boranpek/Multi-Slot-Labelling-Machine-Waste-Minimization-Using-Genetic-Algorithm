import java.util.ArrayList;

public class ProductList {
    private static ArrayList<Product> products = new ArrayList<>();

    public static ArrayList<Product> getProducts() {
        return products;
    }

    public static void addProduct(Product product) {
        products.add(product);
    }

    public static int getProductsNumber() {
        return products.size();
    }
}
