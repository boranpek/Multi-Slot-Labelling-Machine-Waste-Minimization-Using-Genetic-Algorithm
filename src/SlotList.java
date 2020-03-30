import java.util.ArrayList;

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
}
