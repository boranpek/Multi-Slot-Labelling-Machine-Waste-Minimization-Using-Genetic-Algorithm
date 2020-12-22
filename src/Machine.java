/*import ilog.concert.IloException;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloNumVar;
import ilog.cplex.IloCplex;*/ // this part for cplex


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

        int[] countProducts = new int[ProductList.getProductsNumber()];
        int[] runIndexes = new int[ProductList.getProductsNumber()];

        int[][] runIndexesForCondition2 = new int[numberOfSlotConfig][ProductList.getProductsNumber()];
        int[] max = new int[ProductList.getProductsNumber()];
        for (int i = 0;i < ProductList.getProductsNumber();i++) {
            max[i] = 0;
        }
        int[] runIndexForMax = new int[ProductList.getProductsNumber()];

        //1st step
        for(int i = 0; i < Machine.getNumberOfSlotConfig(); i++){
            for (int j = 0; j < Machine.getSlotNumber(); j++){
                for (Product product : ProductList.getProducts()) {
                    if (product.getProductId() == slotList.getSlots().get(i).get(j).getProductId()) {
                        countProducts[product.getProductId()]++;
                        runIndexes[product.getProductId()] = i;
                    }
                }
            }
        }

        for (int i = 0; i < ProductList.getProductsNumber(); i++) {
            if (countProducts[i] == 1 && ProductList.getProducts().get(i).getAmountOfProduct() < ProductList.getProducts().get(i).getDemandOfProduct()) {
                while (ProductList.getProducts().get(i).getAmountOfProduct() < ProductList.getProducts().get(i).getDemandOfProduct()) {
                    for (Product product : slotList.getSlots().get(runIndexes[i])) {
                        product.incrementAmountOfProduct();
                    }
                    countList[runIndexes[i]]++;
                }
            }
        }

        //2nd step
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
            condition1 = true;
        }

        //3rd step

        for (int i = 0;i < numberOfSlotConfig; i++) {
            for (int j = 0; j < slotNumber; j++) {
                if(slotList.getSlots().get(i).get(j).getAmountOfProduct() < slotList.getSlots().get(i).get(j).getDemandOfProduct()) {
                    runIndexesForCondition2[i][slotList.getSlots().get(i).get(j).getProductId()]++;
                }
            }
        }

        for(int i = 0;i < numberOfSlotConfig;i++) {
            for (int j = 0; j < ProductList.getProductsNumber();j++) {
                if (runIndexesForCondition2[i][j] > max[j]) {
                    max[j] = runIndexesForCondition2[i][j];
                    runIndexForMax[j] = i;
                }
            }
        }

        for (int i = 0;i < ProductList.getProductsNumber();i++) {
            if (max[i] > 0) {
                while (ProductList.getProducts().get(i).getAmountOfProduct() < ProductList.getProducts().get(i).getDemandOfProduct()) {
                    for (Product product : slotList.getSlots().get(runIndexForMax[i])) {
                        product.incrementAmountOfProduct();
                    }
                    countList[runIndexForMax[i]]++;
                }
            }
        }

        //4th step
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

  /*  public static void runCplex(boolean showSteps) {
        int[] demands = new int[ProductList.getProductsNumber()];
        int totalDemand = 0;
        for (int i = 0; i < ProductList.getProductsNumber();i++) {
            demands[i] = ProductList.getProducts().get(i).getDemandOfProduct();
            totalDemand = totalDemand + demands[i];
        }

        int[][][] x = new int[ProductList.getProductsNumber()][getSlotNumber()][getNumberOfSlotConfig()];
        for (int i = 0; i < ProductList.getProductsNumber();i++) {
            for (int j = 0; j < getSlotNumber(); j++) {
                for (int k = 0; k < getNumberOfSlotConfig(); k++) {
                    x[i][j][k] = 0;
                }
            }
        }

        for (int i = 0; i < getNumberOfSlotConfig(); i++) {
            for (int j = 0; j < getSlotNumber();j++) {
                x[slotList.getSlots().get(i).get(j).getProductId()][j][i] = Integer.MAX_VALUE;
            }
        }

        try {
            // cplex environment
            IloCplex cplex = new IloCplex();

            // decision variable
            IloNumVar[][][] y = new IloNumVar[ProductList.getProductsNumber()][getSlotNumber()][getNumberOfSlotConfig()];

            for (int i = 0;i < ProductList.getProductsNumber();i++) {
                for (int j = 0;j < Machine.getSlotNumber();j++) {
                    for (int k = 0;k < Machine.getNumberOfSlotConfig();k++) {
                        y[i][j][k] = cplex.intVar(0,Integer.MAX_VALUE);
                    }
                }
            }
            // expressions
            IloLinearNumExpr[] totalProduced = new IloLinearNumExpr[ProductList.getProductsNumber()];
            for (int i = 0; i < ProductList.getProductsNumber();i++) {
                totalProduced[i] = cplex.linearNumExpr();
                for (int j = 0;j < getSlotNumber();j++) {
                    for (int k = 0;k < getNumberOfSlotConfig();k++) {
                        totalProduced[i].addTerm(1,y[i][j][k]);
                    }
                }
            }

            IloLinearNumExpr[][] slotEquality = new IloLinearNumExpr[getSlotNumber()][getNumberOfSlotConfig()];
            IloLinearNumExpr[][] slotEquality2 = new IloLinearNumExpr[getSlotNumber()][getNumberOfSlotConfig()];
            for(int j = 0; j < getSlotNumber();j++) {
                for (int k = 0;k < getNumberOfSlotConfig();k++) {
                    slotEquality[j][k] = cplex.linearNumExpr();
                    slotEquality2[j][k] = cplex.linearNumExpr();
                    for(int i = 0;i < ProductList.getProductsNumber();i++) {
                        slotEquality[j][k].addTerm(1,y[i][j][k]);
                        slotEquality2[j][k].addTerm(1,y[i][1][k]);
                    }
                }
            }
            // constraints
                //connection
            for (int i = 0; i < ProductList.getProductsNumber();i++) {
                for (int j = 0; j < getSlotNumber(); j++) {
                    for (int k = 0; k < getNumberOfSlotConfig();k++) {
                        cplex.addGe(x[i][j][k], y[i][j][k]);
                    }
                }
            }

                //greater then demand
            for(int i = 0;i < ProductList.getProductsNumber();i++) {
                cplex.addGe(totalProduced[i],demands[i]);
            }
                //slot equality
            for (int j = 0;j < getSlotNumber();j++) {
                for (int k = 0;k < getNumberOfSlotConfig();k++) {
                    cplex.addEq(slotEquality[j][k],slotEquality2[j][k]);
                }
            }
            // objective
            IloLinearNumExpr objective = cplex.linearNumExpr();

            for (int i = 0;i < ProductList.getProductsNumber();i++) {
                for (int j = 0;j < getSlotNumber();j++) {
                    for (int k = 0; k < getNumberOfSlotConfig();k++) {
                        objective.addTerm(1,y[i][j][k]);
                    }
                }
            }
            cplex.addMinimize(objective);
            // solution
            if (cplex.solve()) {
                int waste = (int)cplex.getObjValue() - totalDemand;
                slotList.setWaste(waste);

                if (showSteps) {
                    for (int k = 0; k < getNumberOfSlotConfig(); k++) {
                        System.out.println("At run: " + (k+1));
                        System.out.println("--------");
                        for(int j = 0; j < getSlotNumber(); j++) {
                            System.out.println("Slot " + (j+1) + ": Product " + (slotList.getSlots().get(k).get(j).getProductId()+1));
                        }
                        System.out.println("------------------");
                    }
                    System.out.println("Total waste = " + waste);
                }
            }
            else {
                System.out.println("Problem is not solved");
            }
        }
        catch (IloException e) {
            e.printStackTrace();
        }


    }*/


}
