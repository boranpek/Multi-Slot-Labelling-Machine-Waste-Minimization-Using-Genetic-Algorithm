import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.io.File;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        ArrayList<Integer> data = readFile();

        int productNumber = data.get(0);
        int slotNumber = data.get(1);
        int sizeOfData = data.size();

        SlotList slotList = new SlotList();
        Machine machine = new Machine(slotNumber,2);
        ArrayList<Product> products = new ArrayList<>();
        int productId = 1;
        for (int i = 2; i < sizeOfData; i++) {
            Product product = new Product(data.get(i),productId);
            products.add(product);
            productId++;
        }

        ArrayList<Product> slot1 = new ArrayList<>();
        slot1.add(products.get(0));
        slot1.add(products.get(0));
        slot1.add(products.get(1));

        ArrayList<Product> slot2 = new ArrayList<>();
        slot2.add(products.get(1));
        slot2.add(products.get(2));
        slot2.add(products.get(3));

        slotList.addProductsToSlot(slot1);
        slotList.addProductsToSlot(slot2);

        run(machine, slotList);

        int[] waste = new int[productNumber];
        int totalWaste = 0;
        for (int i = 0; i < productNumber; i++) {
            waste[i] = products.get(i).getAmountOfProduct() - products.get(i).getDemandOfProduct();
            System.out.println("Product " + (i+1) + " demand: " + products.get(i).getDemandOfProduct() + " ,produced: " + products.get(i).getAmountOfProduct() + " waste: " + waste[i]);
            totalWaste = totalWaste + waste[i];
        }

        System.out.println("Total waste: " +totalWaste);

    }

    static private void run(Machine machine, SlotList slots) {
        int countProduced = 0;
        int[] countList = new int [machine.getNumberOfSlotConfig()];
        for(int i = 0; i < machine.getNumberOfSlotConfig(); i++){
            countProduced = 0;
            machine.setSlots(slots.getSlots().get(i));
            while (slots.getSlots().get(i).get(0).getAmountOfProduct() < slots.getSlots().get(i).get(0).getDemandOfProduct() && slots.getSlots().get(i).get(1).getAmountOfProduct() < slots.getSlots().get(i).get(1).getDemandOfProduct() && slots.getSlots().get(i).get(2).getAmountOfProduct() < slots.getSlots().get(i).get(2).getDemandOfProduct()) {
                machine.run();
                countProduced++;
            }
            countList[i] = countProduced;
        }
        for (int i = machine.getNumberOfSlotConfig()-1; i >= 0; i--){
            machine.setSlots(slots.getSlots().get(i));
            while (slots.getSlots().get(i).get(0).getAmountOfProduct() < slots.getSlots().get(i).get(0).getDemandOfProduct() || slots.getSlots().get(i).get(1).getAmountOfProduct() < slots.getSlots().get(i).get(1).getDemandOfProduct() || slots.getSlots().get(i).get(2).getAmountOfProduct() < slots.getSlots().get(i).get(2).getDemandOfProduct()) {
                machine.run();
                countList[i]++;
            }

        }


        for(int i = 0;i < machine.getNumberOfSlotConfig();i++) {
            System.out.println("At run " + (i+1) + ", " + countList[i] + " product produced in each slot.");
            System.out.print("Slots: ");
            for (Product product : slots.getSlots().get(i)) {
                System.out.print("Product " + product.getProductId() + ", ");
            }
            System.out.println("");
            System.out.println("-----------------------------------------------------------------------");
        }
    }

    static private ArrayList<Integer> readFile(){
        ArrayList<Integer> data = new ArrayList<>();
        try {

            File myObj = new File("test.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                data.add(Integer.parseInt(myReader.nextLine()));
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return data;
    }
}
