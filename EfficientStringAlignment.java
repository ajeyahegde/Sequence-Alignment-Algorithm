
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;

import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;


public class EfficientStringAlignment {

    static String xString;
    static String yString;
    static String c = "ACGT";
    static int[][] penalty = new int[][]{{0,110,48,94},{110,0,118,48},{48,118,0,110},{94,48,110,0}};
    static int gap = 30;
    static int value = 0;

    /**
     * DP solution starts here
     * @param x First string
     * @param y Second string
     * @return Penalty array
     */
    static String[] getMinimumPenalty(String x, String y)
    {
        int i;
        int j;
        //Initializing the length of strings
        int m = x.length(); 
        int n = y.length(); 
        
        // Table for storing optimal substructure answers
        int[][] dp = new int[n + m + 1][n + m + 1];
        
        /** for (int[] x1 : dp)
            Arrays.fill(x1, 0);*/
        
        // Initialising the table
        for (i = 0; i <= (n + m); i++) {
            dp[i][0] = i * gap;
            dp[0][i] = i * gap;
        }

        int penaltyValue =0;
        
        // Calculating the minimum penalty
        for (i = 1; i <= m; i++) {
            for (j = 1; j <= n; j++) {
                char c1 = x.charAt(i-1);
                char c2 = y.charAt(j-1);
                int temp_penalty = penalty[c.indexOf(c1)][c.indexOf(c2)];

                penaltyValue =  dp[i][j] = Math.min(Math.min(dp[i - 1][j - 1] + temp_penalty , dp[i - 1][j] + gap) ,
                                            dp[i][j - 1] + gap );
            }
        }

        value += penaltyValue;
        
        int len = n+m;
        i=m;
        j=n;

        int xPos = len;
        int yPos = len;
 
    // Final answers for the respective strings
        int[] xAns = new int[len + 1];
        int[] yAns = new int[len + 1];
     
        while ( !(i == 0 || j == 0)) {
            if (dp[i - 1][j - 1] + penalty[c.indexOf(x.charAt(i-1))][c.indexOf(y.charAt(j-1))] == dp[i][j]) {
                yAns[yPos--] = y.charAt(j - 1);
                xAns[xPos--] = x.charAt(i - 1);
                i--;
                j--;
            }
            else if (dp[i - 1][j] + gap == dp[i][j]) {
                yAns[yPos--] = '_';
                xAns[xPos--] = x.charAt(i - 1);
                i--;
            }
            else if (dp[i][j - 1] + gap == dp[i][j]) {
                yAns[yPos--] = y.charAt(j - 1);
                xAns[xPos--] = '_';
                j--;
            }
        }
        while (xPos > 0) {
            if (i > 0) 
                xAns[xPos--] = x.charAt(--i);
            else 
                xAns[xPos--] = '_';
        }
        while (yPos > 0) {
            if (j > 0) 
                yAns[yPos--] = y.charAt(--j);
            else 
                yAns[yPos--] = '_';
        }
    
        // Removing extra character from final array
        int id = 1;
        for (i = len; i >= 1; i--) {
            if ((char)yAns[i] == '_' && (char)xAns[i] == '_') {
                id = i + 1;
                break;
            }
        }
        
        //Generating the 2 strings
        StringBuilder s1 = new StringBuilder();
        StringBuilder s2 = new StringBuilder();
        for (i = id; i <= len; i++) {
            s1.append((char)xAns[i]);
        }
        for (i = id; i <= len; i++) {
            s2.append((char)yAns[i]);
        }
        return new String[]{s1.toString(),s2.toString()};
    }


    /**
     * Generates initial 2 strings
     * @param list1 : First set of values
     * @param list2 : Second set of values
     * @param x : 1st basic string
     * @param y : 2nd basic string
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
        String StringX = previous;
        previous = y;
        for(Integer i:list2){
            StringBuilder temp = new StringBuilder();
            temp.append(previous.substring(0, i+1));
            temp.append(previous);
            temp.append(previous.substring(i+1));
            previous = temp.toString();
        }
        String StringY = previous;
        xString = StringX;
        yString = StringY;

    }
    
    /**
     * 
     * @param x : 1st String 
     * @param y : 2nd String
     * @return : Return Array DP containing min alignment values of for All of X with each index of Y 
     */
    static int[] findDPValue(String x,String y){

        int xlen = x.length();
        int ylen = y.length();
        int[] dp = new int[ylen+1];
        dp[0] = 0;
        int i,j;
        for(i=1;i<=ylen;i++)
            dp[i] = dp[i-1]+gap;
        for(i=1;i<=xlen;i++){
            int[] temp = new int[ylen+1];
            temp[0] = dp[0]+gap;
            for(j=1;j<=ylen;j++){
                char c1 = x.charAt(i-1);
                char c2 = y.charAt(j-1);
                int pen = penalty[c.indexOf(c1)][c.indexOf(c2)];
                temp[j] = Math.min(Math.min(dp[j-1] + pen,
                                            dp[j] + gap) ,
                                            temp[j-1] + gap );                                
            }
            dp = temp;
        }
        return dp;
    }
    
    /**
     * 
     * @param xLeft : Left of x string
     * @param xRight : Right of x string
     * @param y : y String
     * @return : Index(where we get Minimum Penalty Value) where we cut the second String Y
     */
    static int findMid(String xLeft,String xRight,String y){
        int[] dp1 = findDPValue(xLeft, y);
        StringBuilder s1 = new StringBuilder(xRight);
        StringBuilder s2 = new StringBuilder(y);
        s1.reverse();
        s2.reverse();
        int[] dp2 = findDPValue(s1.toString(), s2.toString());
        int left = 0;
        int right = dp2.length-1;
        
        while(left<right){
            int temp = dp2[left];
            dp2[left] = dp2[right];
            dp2[right] = temp;
            left++;
            right--;
        }    
        
        int min = Integer.MAX_VALUE;
        int minIndex = 0;
        
        for(int i=0;i<dp1.length;i++){
            if(min>(dp1[i]+dp2[i])){
                min = dp1[i]+dp2[i];
                minIndex = i;
            }
        }
        
        return minIndex;
    }

    /**
     * Takes Two String X and Y for which Alignment is to be found and gives out the alignment
     * @param x :First string
     * @param y : Second string
     * @return : Alignment of the string
     */
    static String[] align(String x, String y){
        if(x.length()==1){
            if(y.length()==0) {
                value += 30;
                return new String[]{x, "_"};
            }
            if(x.equals(y))
                return new String[]{x,y};
            return getMinimumPenalty(x, y);
        }
        int len = x.length();
        int mid = len/2;
        String xLeft = x.substring(0,mid);
        String xRight = x.substring(mid,len);
        int cut = findMid(xLeft,xRight,y);
        String[] s1 = align(xLeft,y.substring(0,cut));
        String[] s2 = align(xRight, y.substring(cut, y.length()));
        String str1 = s1[0]+s2[0];
        String str2 = s1[1]+s2[1];
        return new String[]{str1,str2};
    }

    /**
     * Main method
     * @param args command line arguements
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
        String[] ans = align(xString, yString);

        String first1 ="";
        String first2 ="";

        if(ans[0].length()>=100){
            first1 = ans[0].substring(0,50);
            first2 = ans[0].substring(ans[0].length()-50);
        }
        else if(ans[0].length()>=50){
            first1 = ans[0].substring(0,50);
            first2 = ans[0].substring(50);
        }
        else{
            first1 = ans[0];
        }

        String second1 ="";
        String second2 ="";

        if(ans[1].length()>=100){
            second1 = ans[1].substring(0,50);
            second2 = ans[1].substring(ans[1].length()-50);
        }
        else if(ans[1].length()>=50){
            second1 = ans[1].substring(0,50);
            second2 = ans[1].substring(50);
        }
        else{
            second1 = ans[1];
        }



        long stopTime = System.currentTimeMillis();
        long afterUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        double elapsedTime = ((stopTime - startTime)*0.001);
        elapsedTime = Math.round(elapsedTime*1000.0)/1000.0;
        long actualMemUsed=(afterUsedMem-beforeUsedMem)/1024;

        /** //Cost of alignment
         * System.out.println(value);
         */
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
