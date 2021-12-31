
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class BasicStringAlignment{
    static String xString;
    static String yString;
    static StringBuilder string1 = new StringBuilder();
    static StringBuilder string2 = new StringBuilder();

    /**
     *
     * @param list1 : List of first indexes
     * @param list2: List of second indexes
     * @param x : first basic string
     * @param y : second basic string
     */
    public static void stringGenerator(List<Integer> list1, List<Integer> list2, String x, String y){
        String previous = x;
        for(Integer i:list1){
            StringBuilder temp = new StringBuilder();
            temp.append(previous.substring(0, i+1));
            temp.append(previous);
            temp.append(previous.substring(i+1));
            previous = temp.toString();
        }
        String String_X = previous;
        previous = y;
        for(Integer i:list2){
            StringBuilder temp = new StringBuilder();
            temp.append(previous.substring(0, i+1));
            temp.append(previous);
            temp.append(previous.substring(i+1));
            previous = temp.toString();
        }
        String StringY = previous;
        xString = String_X;
        yString = StringY;


    }

    /**
     *
     * @param x : First string to compare
     * @param y : Second string to compare
     */
    static void getMinimumPenalty(String x, String y)
    {
        String c = "ACGT";

        //Setting up the penalty value
        int[][] penalty = new int[][]{{0,110,48,94},{110,0,118,48},{48,118,0,110},{94,48,110,0}};
        //Setting the gap value
        int gap = 30;
        int i;
        int j;
        
        int m = x.length(); // length of first String
        int n = y.length(); // length of second String
        
        // Table for storing optimal substructure answers
        int[][] dp = new int[n + m + 1][n + m + 1];
        
        /**for (int[] x1 : dp)
            Arrays.fill(x1, 0);*/
        
    // Initialising the table 
        for (i = 0; i <= (n + m); i++) {
            dp[i][0] = i * gap;
            dp[0][i] = i * gap;
        }
 
    // Calculating the minimum penalty
        for (i = 1; i <= m; i++) {
            for (j = 1; j <= n; j++)
            {
                char c1 = x.charAt(i-1);
                char c2 = y.charAt(j-1);
                int tempPenalty = penalty[c.indexOf(c1)][c.indexOf(c2)];
                dp[i][j] = Math.min(Math.min(dp[i - 1][j - 1] + tempPenalty , dp[i - 1][j] + gap),
                                            dp[i][j - 1] + gap );
            }
        }

        int len = n+m;
        i=m;
        j=n;

        int xPos = len;
        int yPos = len;
 
    // Final answers for the respective strings
        int[] xAns = new int[len + 1];
        int[] yAns = new int[len + 1];

        while ( !(i == 0 || j == 0)) {
            //No gap but taking the penalty
            if (dp[i - 1][j - 1] + penalty[c.indexOf(x.charAt(i-1))][c.indexOf(y.charAt(j-1))] == dp[i][j]) {
                yAns[yPos--] = y.charAt(j - 1);
                xAns[xPos--] = x.charAt(i - 1);
                i--;
                j--;
            }
            // Adding gap in 1st string
            else if (dp[i - 1][j] + gap == dp[i][j]) {
                yAns[yPos--] = '_';
                xAns[xPos--] = x.charAt(i - 1);
                i--;
            }
            //Adding gap in 2nd string
            else if (dp[i][j - 1] + gap == dp[i][j]) {
                yAns[yPos--] = y.charAt(j - 1);
                xAns[xPos--] = '_';
                j--;
            }
        }

        //Remaining x string
        while (xPos > 0) {
            if (i > 0) {
                xAns[xPos--] = x.charAt(--i);
            }
            else {
                xAns[xPos--] = '_';
            }
        }

        //Remaining y string
        while (yPos > 0) {
            if (j > 0) {
                yAns[yPos--] = y.charAt(--j);
            }
            else {
                yAns[yPos--] = '_';
            }
        }

        // Removing extra character from final array
        int id = 1;
        for (i = len; i >= 1; i--)
        {
            if ((char)yAns[i] == '_' &&
                    (char)xAns[i] == '_')
            {
                id = i + 1;
                break;
            }
        }

        //Printing the 2 strings
        for (i = id; i <= len; i++)
        {

            string1.append((char)xAns[i]);
        }

        for (i = id; i <= len; i++)
        {
            string2.append((char)yAns[i]);
        }


        // Total cost of alignment
        /**
         * System.out.println(dp[m][n]);
         */
    }

    /**
     * Main method
     * @param args command line args
     */
    public static void main(String[] args){
        long startTime = System.currentTimeMillis();
        long beforeUsedMem = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        StringBuilder StringX = new StringBuilder("");
        StringBuilder StringY = new StringBuilder("");
        ArrayList<Integer> index1 = new ArrayList<>();
        ArrayList<Integer> index2 = new ArrayList<>();
        boolean firstDone = false;
        boolean secondDone = false;
        try {
            File myObj = new File(args[0]);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
              String data = myReader.nextLine();
              
              if(!firstDone){
                  StringX.append(data.trim());
                  firstDone = true;
                  continue;
              }
              if(firstDone && secondDone){
                  index2.add(Integer.parseInt(data.trim()));
              }
              if(firstDone && !secondDone){
                  if(data.charAt(0)=='A'||data.charAt(0)=='C'||
                    data.charAt(0)=='T'||data.charAt(0)=='G'){
                        StringY.append(data.trim());
                        secondDone = true;
                  }else{
                      index1.add(Integer.parseInt(data.trim()));
                  }
              }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        stringGenerator(index1,index2,StringX.toString(),StringY.toString());
        getMinimumPenalty(xString, yString);
        long stopTime = System.currentTimeMillis();
        long afterUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        double elapsedTime = ((stopTime - startTime)*0.001);
        elapsedTime = Math.round(elapsedTime*1000.0)/1000.0;
        long actualMemUsed=(afterUsedMem-beforeUsedMem)/1024;

        String first1 ="";
        String first2 ="";

        if(string1.length()>=100){
            first1 = string1.substring(0,50);
            first2 = string1.substring(string1.length()-50);
        }
        else if(string1.length()>=50){
            first1 = string1.substring(0,50);
            first2 = string1.substring(50);
        }
        else{
            first1 = string1.toString();
        }

        String second1 ="";
        String second2 ="";

        if(string2.length()>=100){
            second1 = string2.substring(0,50);
            second2 = string2.substring(string2.length()-50);
        }
        else if(string2.length()>=50){
            second1 = string2.substring(0,50);
            second2 = string2.substring(50);
        }
        else{
            second1 = string2.toString();
        }

        File myObj = new File("output.txt");
        try {
            if (!myObj.exists())
                myObj.createNewFile();
            FileWriter fileWriter = new FileWriter(myObj,true);
            fileWriter.write(first1+" "+first2+"\n");
            fileWriter.write(second1+" "+second2+"\n");
            fileWriter.write(actualMemUsed+"\n"+ elapsedTime +"\n");
            fileWriter.close();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}