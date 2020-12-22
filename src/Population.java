public class Population {
    SlotList[]  slotLists;
    public Population(boolean initialization, int popSize) {
        if(!initialization) {
            slotLists = new SlotList[popSize];
        }
        else {
            slotLists = new SlotList[popSize];
            for (int i = 0; i < popSize; i++) {
                SlotList slotList = new SlotList();
                slotList.createSlotList();
                setSlotList(slotList,i);
            }
        }
    }

    public SlotList[] getSlotLists() {
        return slotLists;
    }

    public void setSlotList(SlotList slotList, int index) {
        slotLists[index] = slotList;
    }

    public SlotList getBestSlotList() {
        for (SlotList slotList : slotLists) {
            Machine.setSlotList(slotList);
            Machine.run(false);
            slotList.setWaste(Machine.calculateTotalWaste());
            resetAmountOfProducts();
        }
        int min = 999999;
        SlotList bestSlotList = new SlotList();
        for (SlotList slotList : slotLists) {
            if (slotList.getWaste() < min) {
                min = slotList.getWaste();
                bestSlotList = slotList;
            }
        }
        return  bestSlotList;
    }

    public void addSlotList(int index, SlotList slotList) {
        slotLists[index] = slotList;
    }

    public void resetAmountOfProducts() {
        for (Product product : ProductList.getProducts()) {
            product.setAmountOfProduct(0);
        }
    }
}
