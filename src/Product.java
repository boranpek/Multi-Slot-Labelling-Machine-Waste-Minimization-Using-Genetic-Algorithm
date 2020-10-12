public class Product {

    private int amountOfProduct;
    private int demandOfProduct;
    private int productId;

    public Product(int demandOfProduct, int productId) {
        this.demandOfProduct = demandOfProduct;
        this.amountOfProduct = 0;
        this.productId = productId;
    }

    public int getAmountOfProduct() {
        return amountOfProduct;
    }

    public int getProductId() {
        return productId;
    }

    public void setAmountOfProduct(int amountOfProduct) {
        this.amountOfProduct = amountOfProduct;
    }

    public int getDemandOfProduct() {
        return demandOfProduct;
    }

    public void setDemandOfProduct(int demandOfProduct) {
        this.demandOfProduct = demandOfProduct;
    }

    public void incrementAmountOfProduct() {
        this.amountOfProduct = this.amountOfProduct + 1;
    }
}
