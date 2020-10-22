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

    public static int calculateTotalWaste() {
        int[] waste = new int[ProductList.getProductsNumber()];
        int totalWaste = 0;
        for (int i = 0; i < ProductList.getProductsNumber(); i++) {
            waste[i] = ProductList.getProducts().get(i).getAmountOfProduct() - ProductList.getProducts().get(i).getDemandOfProduct();
            totalWaste = totalWaste + waste[i];
        }

        return totalWaste;
    }

    public static void run(boolean showSteps) {
        int[] countList = new int [numberOfSlotConfig];
        boolean condition1 = true;
        boolean condition2 = false;

        for(int i = 0; i < numberOfSlotConfig; i++){
            for (int j = 0; j < slotNumber; j++) {
                condition1 = condition1 && slotList.getSlots().get(i).get(j).getAmountOfProduct() < slotList.getSlots().get(i).get(j).getDemandOfProduct();
            }
            while (condition1) {
                for (Product product: slotList.getSlots().get(i)){
                    product.incrementAmountOfProduct();
                }
                countList[i]++;
                for (int j = 0; j < slotNumber; j++) {
                    condition1 = condition1 && slotList.getSlots().get(i).get(j).getAmountOfProduct() < slotList.getSlots().get(i).get(j).getDemandOfProduct();
                }
            }
        }
        for (int i = numberOfSlotConfig-1; i >= 0; i--){
            for (int j = 0; j < slotNumber; j++) {
                condition2 = condition2 || slotList.getSlots().get(i).get(j).getAmountOfProduct() < slotList.getSlots().get(i).get(j).getDemandOfProduct();
            }
            while (condition2) {
                condition2 = false;
                for (Product product: slotList.getSlots().get(i)) {
                    product.incrementAmountOfProduct();
                }
                countList[i]++;
                for (int j = 0; j < slotNumber; j++) {
                    condition2 = condition2 || slotList.getSlots().get(i).get(j).getAmountOfProduct() < slotList.getSlots().get(i).get(j).getDemandOfProduct();
                }
            }

        }

        if (showSteps) {
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

}
