import java.util.ArrayList;

public class Machine {
    private int slotNumber;
    private int numberOfSlotConfig;
    private ArrayList<Product> slots;

    public Machine(int slotNumber, int numberOfSlotConfig) {
        this.slotNumber = slotNumber;
        this.numberOfSlotConfig = numberOfSlotConfig;
        this.slots = new ArrayList<Product>();
    }

    public int getSlotNumber() {
        return slotNumber;
    }

    public void setSlotNumber(int slotNumber) {
        this.slotNumber = slotNumber;
    }

    public int getNumberOfSlotConfig() {
        return numberOfSlotConfig;
    }

    public void setNumberOfSlotConfig(int numberOfSlotConfig) {
        this.numberOfSlotConfig = numberOfSlotConfig;
    }

    public ArrayList<Product> getSlots() {
        return slots;
    }

    public void setSlots(ArrayList<Product> slots) {
        this.slots = slots;
    }

    public void run() {
        for (Product product: this.slots) {
            product.incrementAmountOfProduct();
        }
    }

}
