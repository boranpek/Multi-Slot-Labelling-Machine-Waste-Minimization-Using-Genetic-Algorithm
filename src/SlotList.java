import java.util.ArrayList;
import java.util.Random;

public class SlotList {

    private ArrayList<ArrayList<Product>> slots;
    private int waste;

    public void setSlots(ArrayList<ArrayList<Product>> slots) {
        this.slots = slots;
    }


    public SlotList() {
        this.slots = new ArrayList<ArrayList<Product>>();
        this.waste = 0;
    }

    public int getWaste() {
        return waste;
    }

    public void setWaste(int waste) {
        this.waste = waste;
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

        while (this.slots.size() < Machine.getNumberOfSlotConfig()) {
            for (int i  = 0; i < Machine.getNumberOfSlotConfig(); i++) {
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
            int[] countProducts = new int[ProductList.getProductsNumber()];

            for(int i = 0; i < Machine.getNumberOfSlotConfig(); i++){
                for (int j = 0; j < Machine.getSlotNumber(); j++){
                    for (Product product : ProductList.getProducts()) {
                        if (product.getProductId() == this.slots.get(i).get(j).getProductId()) {
                            countProducts[product.getProductId()]++;
                        }
                    }
                }
            }

            for (int i = 0; i < ProductList.getProductsNumber(); i++) {
                if (countProducts[i] == 0) {
                    this.slots.clear();
                    break;
                }
            }
        }


    }
}
