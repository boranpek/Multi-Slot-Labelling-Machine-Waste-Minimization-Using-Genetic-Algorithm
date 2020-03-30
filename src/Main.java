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
        int[] amountList = {100, 80};

        SlotList slotList = new SlotList();
        Machine machine = new Machine(slotNumber,2);
        ArrayList<Product> products = new ArrayList<>();

        for (int i = 2; i < sizeOfData; i++) {
            Product product = new Product(data.get(i));
            products.add(product);
        }

        //bir algoritma slotsayısı kadar doncek ve en optimal slotları ve amountlisti verecek
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

        run(machine, amountList, slotList);

        int[] waste = new int[productNumber];
        int totalWaste = 0;
        for (int i = 0; i < productNumber; i++) {
            waste[i] = products.get(i).getAmountOfProduct() - products.get(i).getDemandOfProduct();
            totalWaste = totalWaste + waste[i];
        }

        System.out.println(totalWaste);

    }

    static private void run(Machine machine, int [] amountList, SlotList slots) {
        for(int i = 0; i < machine.getNumberOfSlotConfig(); i++){
            machine.setSlots(slots.getSlots().get(i));
            machine.run(amountList[i]);
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
