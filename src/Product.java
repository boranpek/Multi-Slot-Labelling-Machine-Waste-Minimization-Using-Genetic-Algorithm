public class Product {

    private int amountOfProduct;
    private int demandOfProduct;

    public Product(int demandOfProduct) {
        this.demandOfProduct = demandOfProduct;
        this.amountOfProduct = 0;
    }

    public int getAmountOfProduct() {
        return amountOfProduct;
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

    public void incrementAmountOfProduct(int amount) {
        this.amountOfProduct = this.amountOfProduct + amount;
    }
}
