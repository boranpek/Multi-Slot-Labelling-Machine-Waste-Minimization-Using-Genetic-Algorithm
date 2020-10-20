import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.io.File;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        ArrayList<Integer> data = readFile();

        int productNumber = data.get(0);
        int slotNumber = data.get(1);
        int numberOfSlotConfig = data.get(2);
        int sizeOfData = data.size();

        Machine.setSlotNumber(slotNumber);
        Machine.setNumberOfSlotConfig(numberOfSlotConfig);

        int productId = 0;
        for (int i = 3; i < sizeOfData; i++) {
            Product product = new Product(data.get(i),productId);
            ProductList.addProduct(product);
            productId++;
        }

        Population slotLists = new Population(true, 20);

        Machine.setSlotList(slotLists.getBestSlotList());

        Machine.run();

        calculateTotalWaste();

        /*
        for (int i = 0; i < 50; i++) {
            slotLists = GeneticAlgorithm.evolveSlotLists(slotLists);
        } //This creates new populations with genetic algorithm 50 times
        */


    }

    static private void calculateTotalWaste() {
        int[] waste = new int[ProductList.getProductsNumber()];
        int totalWaste = 0;
        for (int i = 0; i < ProductList.getProductsNumber(); i++) {
            waste[i] = ProductList.getProducts().get(i).getAmountOfProduct() - ProductList.getProducts().get(i).getDemandOfProduct();
            System.out.println("Product " + (i+1) + " demand: " + ProductList.getProducts().get(i).getDemandOfProduct() + " ,produced: " + ProductList.getProducts().get(i).getAmountOfProduct() + " waste: " + waste[i]);
            totalWaste = totalWaste + waste[i];
        }

        System.out.println("Total waste: " +totalWaste);
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
