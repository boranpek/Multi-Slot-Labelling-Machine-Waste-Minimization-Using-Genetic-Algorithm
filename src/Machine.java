import java.util.ArrayList;

public class Machine {
    private static int slotNumber;
    private static int numberOfSlotConfig;
    private static SlotList slotList;


    public static int getSlotNumber() {
        return slotNumber;
    }

    public static void setSlotNumber(int slotNumber) {
        Machine.slotNumber = slotNumber;
    }

    public static int getNumberOfSlotConfig() {
        return numberOfSlotConfig;
    }

    public static void setNumberOfSlotConfig(int numberOfSlotConfig) {
        Machine.numberOfSlotConfig = numberOfSlotConfig;
    }

    public static SlotList getSlotList() {
        return slotList;
    }

    public static void setSlotList(SlotList slotlist) {
        Machine.slotList = slotlist;
    }

    public static void run() {
        int countProduced = 0;
        int[] countList = new int [numberOfSlotConfig];
        for(int i = 0; i < numberOfSlotConfig; i++){
            countProduced = 0;
            while (slotList.getSlots().get(i).get(0).getAmountOfProduct() < slotList.getSlots().get(i).get(0).getDemandOfProduct() && slotList.getSlots().get(i).get(1).getAmountOfProduct() < slotList.getSlots().get(i).get(1).getDemandOfProduct() && slotList.getSlots().get(i).get(2).getAmountOfProduct() < slotList.getSlots().get(i).get(2).getDemandOfProduct()) {
                for (Product product: slotList.getSlots().get(i)){
                    product.incrementAmountOfProduct();
                }
                countProduced++;
            }
            countList[i] = countProduced;
        }
        for (int i = numberOfSlotConfig-1; i >= 0; i--){
            while (slotList.getSlots().get(i).get(0).getAmountOfProduct() < slotList.getSlots().get(i).get(0).getDemandOfProduct() || slotList.getSlots().get(i).get(1).getAmountOfProduct() < slotList.getSlots().get(i).get(1).getDemandOfProduct() || slotList.getSlots().get(i).get(2).getAmountOfProduct() < slotList.getSlots().get(i).get(2).getDemandOfProduct()) {
                for (Product product: slotList.getSlots().get(i)) {
                    product.incrementAmountOfProduct();
                }
                countList[i]++;
            }

        }


        for(int i = 0;i < numberOfSlotConfig;i++) {
            System.out.println("At run " + (i+1) + ", " + countList[i] + " product produced in each slot.");
            System.out.print("Slots: ");
            for (Product product : slotList.getSlots().get(i)) {
                System.out.print("Product " + (1 + product.getProductId()) + ", ");
            }
            System.out.println("");
            System.out.println("-----------------------------------------------------------------------");
        }
    }

}
