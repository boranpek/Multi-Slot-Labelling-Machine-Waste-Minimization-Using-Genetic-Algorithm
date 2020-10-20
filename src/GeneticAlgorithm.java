public class GeneticAlgorithm {

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
        return slotLists.getBestSlotList();
    }

    public static SlotList crossingOver(SlotList parent1, SlotList parent2) {
        return parent1;
    }

    public static void mutation(SlotList child) {

    }
}
