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

    public void setSlotList(SlotList slotList, int index) {
        slotLists[index] = slotList;
    }

    public SlotList getBestSlotList() {
        SlotList slotList = slotLists[0];
        return slotList;
    }

    public void addSlotList(int index, SlotList slotList) {
        slotLists[index] = slotList;
    }
}
