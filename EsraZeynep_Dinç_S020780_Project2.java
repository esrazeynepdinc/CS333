import java.util.Arrays;
import java.util.Scanner;

public class EsraZeynep_Dinç_S020780_Project2 {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int number_of_the_outlets = scanner.nextInt();
        if (number_of_the_outlets <= 50) {
            String[] outlets = new String[number_of_the_outlets];
            for (int i = 0; i < number_of_the_outlets; i++) {
                // I can give name the outlets in order
                outlets[i] = scanner.next();
            }
            String[] lamps = new String[number_of_the_outlets];
            for (int i = 0; i < number_of_the_outlets; i++) {                           // I can give name to lamps in order
                lamps[i] = scanner.next();
            }
            int[][] DP = new int[number_of_the_outlets + 1][number_of_the_outlets + 1]; // I started to create DP table like I put 0 for first row and first column

            for (int i = 0; i <= number_of_the_outlets; i++) {
                for (int j = 0; j <= number_of_the_outlets; j++) {
                    if ((i == 0 || j == 0)) {
                        DP[i][j] = 0;
                    }
                }
            }
            for (int i = 1; i <= number_of_the_outlets; i++) {                         // because I solved longest common subsequence problem like that and than I
                for (int j = 1; j <= number_of_the_outlets; j++) {                     // applied the rules for filling the table
                    if (outlets[i - 1].equals(lamps[j - 1])) {                         // I applied method that I know If they match I can add +1 easily because
                        DP[i][j] = 1 + DP[i - 1][j - 1];                               // whatever happend behind the last character it doesn't matter last character always
                    }                                                                  // match without crossing
                    else {
                        DP[i][j] = Math.max(DP[i - 1][j], DP[i][j - 1]);
                    }


                }
            }
            int index = DP[number_of_the_outlets][number_of_the_outlets];
            int temp = index;
            String[] longest_common_subsequence = new String[index + 1];
            longest_common_subsequence[index] = " ";
            int i = number_of_the_outlets;
            int j = number_of_the_outlets;
            while (i > 0 && j > 0) {
                if (outlets[i - 1].equals(lamps[j - 1])) {
                    longest_common_subsequence[index - 1] = outlets[i - 1];
                    i--;
                    j--;
                    index--;
                } else if (DP[i - 1][j] > DP[i][j - 1])
                    i--;
                else
                    j--;
            }
            for (int k = 0; k <= temp; k++)
                System.out.print(longest_common_subsequence[k] + " ");
            System.out.println("");
        } else{
            System.out.println("You exceed the maximum number of outlets and lambs limit");
        }
    }
}









