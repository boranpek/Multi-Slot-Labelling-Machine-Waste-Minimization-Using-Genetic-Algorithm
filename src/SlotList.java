import java.util.ArrayList;
import java.util.Random;

public class SlotList {

    private ArrayList<ArrayList<Product>> slots;

    public SlotList() {
        this.slots = new ArrayList<ArrayList<Product>>();
    }

    public void setSlots(ArrayList<Product> slot, int index) {
        this.slots.set(index, slot);
    }

    public void addProductsToSlot(ArrayList<Product> products) {
        this.slots.add(products);
    }

    public ArrayList<ArrayList<Product>> getSlots() {
        return slots;
    }

    public void createSlotList() {
        Random random = new Random();
        int randomProductId;


        for (int i = 0; i<Machine.getNumberOfSlotConfig(); i++) {
            ArrayList<Product> slotForEachRun = new ArrayList<>();
            for (int j = 0; j < Machine.getSlotNumber(); j++) {
                randomProductId = random.nextInt(ProductList.getProductsNumber());
                for(Product product : ProductList.getProducts()) {
                    if (product.getProductId() == randomProductId) {
                        slotForEachRun.add(product);
                    }
                }
            }
            this.slots.add(slotForEachRun);

        }




    }
}
