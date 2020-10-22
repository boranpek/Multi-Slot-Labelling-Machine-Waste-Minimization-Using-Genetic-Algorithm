import java.util.ArrayList;
import java.util.Random;

public class GeneticAlgorithm {
    private static final int tournamentSize = 5;

    public static Population evolveSlotLists(Population slotLists) {
        Population evolvedSlotLists = new Population(false,20);
        for (int i = 0; i < 20; i++){
            SlotList parent1 =  tournamentSelection(slotLists);//this selects randomly slotList from slotLists(population) by the tournamentSize then return the best one(slotList which has lowest waste)
            SlotList parent2 =  tournamentSelection(slotLists);
            SlotList child = crossingOver(parent1, parent2); // this creates new slotList which is combination of 2 parents
            mutation(child) ;// one gen changes in child randomly under mutationRate condition
            evolvedSlotLists.addSlotList(i, child); // child is added to evolvedSlotLists
        }
        return evolvedSlotLists;
    }

    public static SlotList tournamentSelection(Population slotLists) {

        Population selectedSlotLists = new Population(false, tournamentSize);
        Random rand = new Random();
        for (int i = 0; i < tournamentSize; i++) {
            selectedSlotLists.addSlotList(i,slotLists.getSlotLists()[rand.nextInt(20)]);
        }

        return selectedSlotLists.getBestSlotList();

    }

    public static SlotList crossingOver(SlotList parent1, SlotList parent2) {
        SlotList crossedSlotList = new SlotList();
        Random rand = new Random();

        while (crossedSlotList.getSlots().size() < Machine.getNumberOfSlotConfig()) {
            crossedSlotList.createSlotList();
            for (int i = 0; i < Machine.getNumberOfSlotConfig(); i++) {
                crossedSlotList.getSlots().get(i).clear();
            }


            int randomRunNumber1 = rand.nextInt(Machine.getNumberOfSlotConfig());
            int randomSlotNumber1 = rand.nextInt(Machine.getSlotNumber());
            int randomRunNumber2 = rand.nextInt(Machine.getNumberOfSlotConfig());
            int randomSlotNumber2 = rand.nextInt(Machine.getSlotNumber());

            int lowerRunNumber = randomRunNumber1;
            int upperRunNumber = randomRunNumber2;
            int lowerSlotNumber = randomSlotNumber1;
            int upperSlotNumber = randomSlotNumber2;

            if (randomRunNumber2 < lowerRunNumber){
                lowerRunNumber = randomRunNumber2;
                upperRunNumber = randomRunNumber1;
            }

            if (randomSlotNumber2 < lowerSlotNumber) {
                lowerSlotNumber = randomSlotNumber2;
                upperSlotNumber = randomSlotNumber1;
            }
            for (int i = 0; i < Machine.getNumberOfSlotConfig(); i++) {
                for (Product product : parent2.getSlots().get(i)) {
                    crossedSlotList.getSlots().get(i).add(product);
                }
            }

            for (int i = lowerRunNumber; i < upperRunNumber; i++ ) {
                for (int j = lowerSlotNumber; j < upperSlotNumber; j++) {
                    crossedSlotList.getSlots().get(i).set(j,parent1.getSlots().get(i).get(j));
                }
            }

            int[] countProducts = new int[ProductList.getProductsNumber()];

            for(int i = 0; i < Machine.getNumberOfSlotConfig(); i++){
                for (int j = 0; j < Machine.getSlotNumber(); j++){
                    for (Product product : ProductList.getProducts()) {
                        if (product.getProductId() == crossedSlotList.getSlots().get(i).get(j).getProductId()) {
                            countProducts[product.getProductId()]++;
                        }
                    }
                }
            }

            for (int i = 0; i < ProductList.getProductsNumber(); i++) {
                if (countProducts[i] == 0) {
                    crossedSlotList.getSlots().clear();
                    break;
                }
            }
        }
        return crossedSlotList;
    }

    public static void mutation(SlotList child) {
        Random rand = new Random();
        int randomRunNumber = rand.nextInt(Machine.getNumberOfSlotConfig());
        int randomSlotNumber = rand.nextInt(Machine.getSlotNumber());
        int randomRunNumber2 = rand.nextInt(Machine.getNumberOfSlotConfig());
        int randomSlotNumber2 = rand.nextInt(Machine.getSlotNumber());

        Product product1 = child.getSlots().get(randomRunNumber).get(randomSlotNumber);
        Product product2 = child.getSlots().get(randomRunNumber2).get(randomSlotNumber2);

        child.getSlots().get(randomRunNumber2).set(randomSlotNumber2, product1);
        child.getSlots().get(randomRunNumber).set(randomSlotNumber, product2);
    }
}
