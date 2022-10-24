import java.lang.Math;
import java.util.Scanner;

public class DentalRecords {

    private static final Scanner keyboard = new Scanner(System.in);
    private static final int MAX_TEETH = 8;
    private static final int MAX_TEETH_LOC = 2;

    //------------------------------------------------------------------------------------------------------
    public static void main(String[] args) {

        int numOfFamilyMembers = 0;
        String[] nameOfPerson;
        char[][][] teethInfo;
        int[][] numTeeth;
        char menuOption;


        //----Welcome Message
        System.out.println("Welcome to the Floridian Tooth Records");
        System.out.println("--------------------------------------");

        //----Gathering family information
        numOfFamilyMembers = getNumOfFamilyMembers(numOfFamilyMembers);
        numTeeth = new int[2][numOfFamilyMembers];
        teethInfo = new char[numOfFamilyMembers][MAX_TEETH_LOC][MAX_TEETH];


        nameOfPerson = new String[numOfFamilyMembers];
        getFamilyInfo(numOfFamilyMembers, nameOfPerson, teethInfo, numTeeth);

        System.out.print("(P)rint, (E)xtract, (R)oot, e(X)it : ");
        menuOption = keyboard.next().charAt(0);

        //----Main Menu Options
        while (menuOption != 'X' && menuOption != 'x') {
            switch(menuOption) {
                case 'P':
                case 'p':
                    printTeethRecords(numOfFamilyMembers, nameOfPerson, teethInfo, numTeeth);
                    System.out.print("(P)rint, (E)xtract, (R)oot, e(X)it : ");
                    break;

                case 'E':
                case 'e':
                    extractTooth(numOfFamilyMembers, nameOfPerson, teethInfo, numTeeth);
                    System.out.print("(P)rint, (E)xtract, (R)oot, e(X)it : ");
                    break;

                case 'R':
                case 'r':
                    calculateRoot(numOfFamilyMembers, teethInfo, numTeeth);
                    System.out.print("(P)rint, (E)xtract, (R)oot, e(X)it : ");
                    break;

                default:
                    System.out.print("Invalid menu option, try again : ");
            }
            menuOption = keyboard.next().charAt(0);
        }

        System.out.print("\nExiting the Floridian Tooth Records :-)");
    }
    //------------------------------------------------------------------------------------------------------
    public static int getNumOfFamilyMembers(int numOfFamilyMembers) {

        System.out.print("Please enter number of people in the family : ");
        numOfFamilyMembers = keyboard.nextInt();

        while (numOfFamilyMembers < 0 || numOfFamilyMembers > 5) {
            System.out.print("Invalid number of people, try again : ");
            numOfFamilyMembers = keyboard.nextInt();
        }
        return (numOfFamilyMembers);
    }

    //------------------------------------------------------------------------------------------------------
    public static void getFamilyInfo(int numOfFamilyMembers, String[] nameOfPerson, char[][][] teethInfo, int[][] numTeeth) {
        int numDisplayed;
        String uppers;
        String lowers;

        for (int familyMemberIndex = 0; familyMemberIndex < numOfFamilyMembers; familyMemberIndex++) {
            numDisplayed = familyMemberIndex + 1;
            System.out.print("Please enter the name for family member " + numDisplayed + " : ");
            nameOfPerson[familyMemberIndex] = keyboard.next();

            //----Gathering uppers info
            uppers = getUppersAndLowers(nameOfPerson[familyMemberIndex], "uppers");

            numTeeth[0][familyMemberIndex] = uppers.length();
            for (int i = 0; i < uppers.length(); i++) {
                teethInfo[familyMemberIndex][0][i] = uppers.charAt(i);
            }

            //----Gathering lowers info
            lowers = getUppersAndLowers(nameOfPerson[familyMemberIndex], "lowers");

            numTeeth[1][familyMemberIndex] = lowers.length();
            for (int i = 0; i < lowers.length(); i++) {
                teethInfo[familyMemberIndex][1][i] = lowers.charAt(i);
            }
        }
    }

    //------------------------------------------------------------------------------------------------------
    public static String getUppersAndLowers(String nameOfPerson, String teethloc) {
        String teethLayer;
        int teethLength;
        boolean validity = true;
        System.out.printf("Please enter their %s for %-10s : ", teethloc, nameOfPerson);
        teethLayer = keyboard.next();

        //----All char are I, B, or M
        validity = checkForValidity(teethLayer);
        teethLength = teethLayer.length();

        //----Limit length of teeth
        while (!validity || teethLength > MAX_TEETH) {
            if (!validity) {
                System.out.print("Invalid teeth types, try again : ");
            } else {
                System.out.print("Too many teeth, try again : ");
            }
            teethLayer = keyboard.next();
            teethLength = teethLayer.length();
            validity = checkForValidity(teethLayer);
        }
        teethLayer = teethLayer.toUpperCase();
        return teethLayer;
    }
    //------------------------------------------------------------------------------------------------------
    public static boolean checkForValidity(String teeth) {

        boolean validity = false;

        for (int teethIndex = 0; teethIndex < teeth.length(); teethIndex++) {
            switch (teeth.charAt(teethIndex)) {
                case 'I':
                case 'i':
                case 'B':
                case 'b':
                case 'M':
                case 'm':
                    validity = true;
                    break;
                default:
                    validity = false;
            }
        }
        return validity;
    }

    //------------------------------------------------------------------------------------------------------
    public static void printTeethRecords(int numOfFamilyMembers, String[] nameOfPerson, char[][][] teethInfo, int[][] numTeeth) {

        //----Use string array for names
        for (int familyMemberIndex = 0; familyMemberIndex < numOfFamilyMembers; familyMemberIndex++) {
            System.out.println();
            System.out.println(nameOfPerson[familyMemberIndex]);
            System.out.print(" Uppers: ");
            for (int teethIndex = 0; teethIndex < numTeeth[0][familyMemberIndex]; teethIndex++) {
                System.out.printf("%3d:%S", teethIndex + 1, teethInfo[familyMemberIndex][0][teethIndex]);
            }
            System.out.println();
            System.out.print(" Lowers: ");
            for (int teethIndex = 0; teethIndex < numTeeth[1][familyMemberIndex]; teethIndex++) {
                System.out.printf("%3d:%S", teethIndex + 1, teethInfo[familyMemberIndex][1][teethIndex]);
            }
            System.out.println();
        }
    }
    //------------------------------------------------------------------------------------------------------
    public static void extractTooth(int numOfFamilyMembers, String[] nameOfPerson, char[][][] teethInfo, int[][] numTeeth) {

        String memberOfFamily;
        boolean personExists = false;
        boolean correctLayer = false;
        char layer;
        int savePersonID = 0;
        int toothNum;
        int toothRow = 0;

        System.out.print("Which family member : ");
        memberOfFamily = keyboard.next();

        //----Does family member match the ones input?
        do {
            for (int familyMemberIndex = 0; familyMemberIndex < numOfFamilyMembers; familyMemberIndex++) {
                if (memberOfFamily.equalsIgnoreCase(nameOfPerson[familyMemberIndex])) {
                    personExists = true;
                    savePersonID = familyMemberIndex;
                }
            }
            if (!personExists) {
                System.out.print("Invalid family member, try again : ");
                memberOfFamily = keyboard.next();
            }
        } while (!personExists);

        System.out.print("Which tooth layer (U)pper or (L)ower : ");
        layer = keyboard.next().charAt(0);

        //----Gather which layer tooth extract is on
        do {
            switch (layer) {
                case 'U':
                case 'u':
                    correctLayer = true;
                    toothRow = 0;
                    break;
                case 'L':
                case 'l':
                    correctLayer = true;
                    toothRow = 1;
                    break;
                default:
                    System.out.print("Invalid layer, try again : ");
                    layer = keyboard.next().charAt(0);
            }
        } while (!correctLayer);

        System.out.print("Which tooth number : ");
        toothNum = keyboard.nextInt();

        //----Is tooth missing or valid?
        while (toothNum > numTeeth[toothRow][savePersonID] || toothNum <= 0 || teethInfo[savePersonID][toothRow][toothNum - 1] == 'M') {
            if (toothNum > numTeeth[toothRow][savePersonID] || toothNum <= 0) {
                System.out.print("Invalid tooth number, try again : ");
            } else if (teethInfo[savePersonID][toothRow][toothNum-1] == 'M') {
                System.out.print("Missing tooth, try again : ");
            }
            toothNum = keyboard.nextInt();
        }
        teethInfo[savePersonID][toothRow][toothNum-1] = 'M';
    }
    //------------------------------------------------------------------------------------------------------
    public static void calculateRoot(int numOfFamilyMembers, char[][][] teethInfo, int[][] numTeeth) {
        double a;
        double b;
        double c;
        double root1;
        double root2;
        double discriminant;
        double totalI = 0.0;
        double totalB = 0.0;
        double totalM = 0.0;


        //----Calculating I, B, and M
        for (int familyMemberIndex = 0; familyMemberIndex < numOfFamilyMembers; familyMemberIndex++) {
            for (int maxLayersIndex = 0; maxLayersIndex < MAX_TEETH_LOC; maxLayersIndex++) {
                int teethIndex;
                for (teethIndex = 0; teethIndex < numTeeth[maxLayersIndex][familyMemberIndex]; teethIndex++){
                    switch (teethInfo[familyMemberIndex][maxLayersIndex][teethIndex]) {
                        case 'I':
                            totalI += 1;
                            break;
                        case 'B':
                            totalB += 1;
                            break;
                        case 'M':
                            totalM += 1;
                            break;
                        default:
                            System.out.print("Error in reading teeth information : ");
                    }
                }
            }
        }
        //----Quadratic equation: Ix^2 + Bx - M
        double I = totalI;
        double B = totalB;
        double M = -1 * totalM;

        discriminant = (B * B) - (4 * I * M);
        if (discriminant == 0) {
            root1 = (-B + Math.sqrt(discriminant))/ (2.0 * I);
            System.out.printf("One root canal at %.2f\n", root1);
            System.out.println();
        } else if (discriminant > 0.0) {
            root1 = (-B + Math.sqrt(discriminant)) / (2.0 * I);
            root2 = (-B - Math.sqrt(discriminant)) / (2.0 * I);
            System.out.printf("One root canal at %.2f\n", root1);
            System.out.println();
            System.out.printf("Another root canal at %.2f", root2);
            System.out.println();
        } else {
            System.out.printf("No real roots");
        }

    }




}