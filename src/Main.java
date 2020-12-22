import org.apache.log4j.BasicConfigurator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String fileName = "";
        int cond = 0;
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome!");
        System.out.print("For instance1, press 1, For instance2, press 2:");
        cond = sc.nextInt();
        System.out.println("");

        if (cond == 1) {
            fileName = "test1.txt";
            System.out.println("Bare with us for about 15 seconds for your result.");
        }
        else if(cond == 2) {
            fileName = "test2.txt";
            System.out.println("Bare with us for about 40 seconds for your result.");
        }
        else {
            System.out.println("You pressed wrong!");
        }

        ArrayList<Integer> data = readFile(fileName);
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
        Object[][] object = new Object[10][(Machine.getNumberOfSlotConfig()*Machine.getSlotNumber()) + 1];
        for (int z = 1;z < 2;z++) {// You can change this to change run number

            Population slotLists = new Population(true, 10);
            SlotList[] geneticSlotLists = new SlotList[2000];//if u want to try bigger population change 2000
            SlotList bestSlotList = new SlotList();
            int temp = 0;
            for (int i = 0; i < 2000; i++) {//also change here if u change above
                slotLists = GeneticAlgorithm.evolveSlotLists(slotLists);
                geneticSlotLists[i] = slotLists.getBestSlotList();

                if ((i % 200) == 0 && i != 0) {//u can change the printing interval
                    int minWaste = 999999;
                    for (int j = 0; j < (200 + (temp * 200));j++) {//also change here if u change above
                        if (geneticSlotLists[j].getWaste() < minWaste) {
                            minWaste = geneticSlotLists[j].getWaste();
                            bestSlotList = geneticSlotLists[j];
                        }
                    }

                    Machine.setSlotList(bestSlotList);

                    Machine.run(true);

                    object[temp][0] = calculateTotalWaste();
                    int columnIndex = 1;
                    for (int k = 0;k < Machine.getNumberOfSlotConfig();k++) {
                        for (int p = 0;p < Machine.getSlotNumber();p++) {
                            object[temp][columnIndex] = bestSlotList.getSlots().get(k).get(p).getProductId();
                            columnIndex++;
                        }
                    }
                    temp++;
                }
            }

        }
        writeToExcel(object);
        System.out.println("Nice! result excel file has been created.");
        System.out.println("We would like to remind you that you can achieve better result by using a larger population. You can do this by checking the comments in the rows:56, 59, 63, 65 in the Main.class");



        //create a slotlist manual for trials
       /* ArrayList<Product> products1 = new ArrayList<>();
        ArrayList<Product> products2 = new ArrayList<>();
        ArrayList<Product> products3 = new ArrayList<>();
        ArrayList<Product> products4 = new ArrayList<>();

        products1.add(ProductList.getProducts().get(0));
        products1.add(ProductList.getProducts().get(8));
        products1.add(ProductList.getProducts().get(9));
        products1.add(ProductList.getProducts().get(12));
        products1.add(ProductList.getProducts().get(12));
        products1.add(ProductList.getProducts().get(13));
        products1.add(ProductList.getProducts().get(14));
        products1.add(ProductList.getProducts().get(15));
        products1.add(ProductList.getProducts().get(16));
        products1.add(ProductList.getProducts().get(16));
        products1.add(ProductList.getProducts().get(17));
        products1.add(ProductList.getProducts().get(17));
        products1.add(ProductList.getProducts().get(17));
        products1.add(ProductList.getProducts().get(17));

        products2.add(ProductList.getProducts().get(0));
        products2.add(ProductList.getProducts().get(5));
        products2.add(ProductList.getProducts().get(6));
        products2.add(ProductList.getProducts().get(7));
        products2.add(ProductList.getProducts().get(8));
        products2.add(ProductList.getProducts().get(8));
        products2.add(ProductList.getProducts().get(9));
        products2.add(ProductList.getProducts().get(9));
        products2.add(ProductList.getProducts().get(11));
        products2.add(ProductList.getProducts().get(11));
        products2.add(ProductList.getProducts().get(12));
        products2.add(ProductList.getProducts().get(12));
        products2.add(ProductList.getProducts().get(14));
        products2.add(ProductList.getProducts().get(14));

        products3.add(ProductList.getProducts().get(0));
        products3.add(ProductList.getProducts().get(0));
        products3.add(ProductList.getProducts().get(1));
        products3.add(ProductList.getProducts().get(2));
        products3.add(ProductList.getProducts().get(2));
        products3.add(ProductList.getProducts().get(2));
        products3.add(ProductList.getProducts().get(3));
        products3.add(ProductList.getProducts().get(4));
        products3.add(ProductList.getProducts().get(10));
        products3.add(ProductList.getProducts().get(10));
        products3.add(ProductList.getProducts().get(14));
        products3.add(ProductList.getProducts().get(16));
        products3.add(ProductList.getProducts().get(16));
        products3.add(ProductList.getProducts().get(17));

        products4.add(ProductList.getProducts().get(1));
        products4.add(ProductList.getProducts().get(2));
        products4.add(ProductList.getProducts().get(4));
        products4.add(ProductList.getProducts().get(4));
        products4.add(ProductList.getProducts().get(8));
        products4.add(ProductList.getProducts().get(10));
        products4.add(ProductList.getProducts().get(11));
        products4.add(ProductList.getProducts().get(12));
        products4.add(ProductList.getProducts().get(13));
        products4.add(ProductList.getProducts().get(14));
        products4.add(ProductList.getProducts().get(14));
        products4.add(ProductList.getProducts().get(15));
        products4.add(ProductList.getProducts().get(16));
        products4.add(ProductList.getProducts().get(17));



        SlotList testSlotList = new SlotList();

        testSlotList.addProductsToSlot(products1);
        testSlotList.addProductsToSlot(products2);
        testSlotList.addProductsToSlot(products3);
        testSlotList.addProductsToSlot(products4);

        Machine.setSlotList(testSlotList);
        Machine.run(true);
        calculateTotalWaste();
        */



    }

    static private int calculateTotalWaste() {
        int[] waste = new int[ProductList.getProductsNumber()];
        int totalWaste = 0;
        int producedProduct = 0;
        for (int i = 0; i < ProductList.getProductsNumber(); i++) {
            waste[i] = ProductList.getProducts().get(i).getAmountOfProduct() - ProductList.getProducts().get(i).getDemandOfProduct();
            System.out.println("Product " + (i+1) + " demand: " + ProductList.getProducts().get(i).getDemandOfProduct() + " ,produced: " + ProductList.getProducts().get(i).getAmountOfProduct() + " waste: " + waste[i]);
            totalWaste = totalWaste + waste[i];
        }

        System.out.println("Total waste: " + totalWaste);
        return totalWaste;
    }

    static private ArrayList<Integer> readFile(String fileName){
        ArrayList<Integer> data = new ArrayList<>();
        try {

            File myObj = new File(fileName);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                data.add(Integer.parseInt(myReader.nextLine()));
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Please re-run the program.");
        }
        return data;
    }

    static private void writeToExcel(Object[][] object) {
        BasicConfigurator.configure();
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("result");

        int rowCount = 0;

        for (Object[] aBook : object) {
            Row row = sheet.createRow(rowCount++);

            int columnCount = 0;

            for (Object field : aBook) {
                Cell cell = row.createCell(columnCount++);
                if (field instanceof String) {
                    cell.setCellValue((String) field);
                } else if (field instanceof Integer) {
                    cell.setCellValue((Integer) field);
                }
            }

        }


        try (FileOutputStream outputStream = new FileOutputStream("result.xlsx")) {
            workbook.write(outputStream);
        }
        catch (Exception e){
            System.out.println("Please re-run the program.");
        }
    }
}
