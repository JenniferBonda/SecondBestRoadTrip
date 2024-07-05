//Jennifer Bonda
import java.util.Scanner;

public class CMSC401_A3 {
    private static int numOfCities;
    private static int shortest;
    private static int secondShortest;
    private static int d0;

    public static void main(String[] args) {

        //receiving input from user and storing them in 1D arrays
        Scanner sc = new Scanner(System.in);
        numOfCities = sc.nextInt();
        int numOfHighways = sc.nextInt();
        int lmp = numOfCities - 2;
        int[] arr1 = new int[lmp]; //city number correlated to motel price
        int[] arr2 = new int[lmp]; //motel price
        int[] arr3 = new int[numOfHighways]; //First city on path
        int[] arr4 = new int[numOfHighways]; //Second city on path
        int[] arr5 = new int[numOfHighways]; //Gas price


        for (int i = 0; i < lmp; i++) {
            arr1[i] = sc.nextInt();
            arr2[i] = sc.nextInt();
        }

        for (int i = 0; i < numOfHighways; i++) {
            arr3[i] = sc.nextInt();
            arr4[i] = sc.nextInt();
            arr5[i] = sc.nextInt();
        }

        int[][] matrix = new int[numOfCities][numOfCities];
        int INF = 9999999;
        int notDiagonal = 0;
        //putting 0 in diagonal spaces
        for (int i = 0; i < numOfCities; i++) {
            for (int j = 0; j < numOfCities; j++) {
                if (i == j) {
                    matrix[i][j] = 0; // Diagonal element becomes 0
                } else {
                    matrix[i][j] = INF; // Non-diagonal element becomes INF
                    notDiagonal = 1;
                }
            }
        }

        //populating matrix in non diagonal spots
        if (notDiagonal == 1) {
            for (int i = 0; i < numOfHighways; i++) {
                int firstNum;
                int secondNum;
                firstNum = arr3[i];
                secondNum = arr4[i];

                boolean canIDoThis = false;
                if (arr4[i] != 1 && arr4[i] != 2) {
                    int cityNumber = arr1[secondNum - 3];
                    canIDoThis = true;
                }

                //System.out.println(arr1[secondNum-3]);
                if (canIDoThis == true) {
                    // If a matching city connection is found, use the distance from arr5
                    matrix[firstNum - 1][secondNum - 1] = arr5[i] + arr2[secondNum - 3];
                } else {
                    matrix[firstNum - 1][secondNum - 1] = arr5[i];
                }
            }
        }

        //populating matrix with reverse paths
        if (notDiagonal == 1) {
            for (int i = 0; i < numOfHighways; i++) {
                int firstNum;
                int secondNum;
                firstNum = arr4[i];
                secondNum = arr3[i];

                boolean canIDoThis = false;
                if (arr3[i] != 1 && arr3[i] != 2) {
                    int cityNumber = arr1[secondNum - 3];
                    canIDoThis = true;
                }

                //System.out.println(arr1[secondNum-3]);
                if (canIDoThis == true) {
                    // If a matching city connection is found, use the distance from arr5
                    matrix[firstNum - 1][secondNum - 1] = arr5[i] + arr2[secondNum - 3];
                } else {
                    matrix[firstNum - 1][secondNum - 1] = arr5[i];
                }
            }
        }

        //printing out matrix d0
//        for (int i = 0; i < numOfCities; i++) {
//            for (int j = 0; j < numOfCities; j++) {
//                System.out.print(matrix[i][j] + " ");
//            }
//            System.out.println();
//
//        }
        //storing direct path between 1 and 2
        d0 = matrix[0][1];
        floydWarshall(matrix);

        //printing out matrix after floyd warshall
//        for (int i = 0; i < numOfCities; i++) {
//            for (int j = 0; j < numOfCities; j++) {
//                System.out.print(matrix[i][j] + " ");
//            }
//            System.out.println();
//        }


        //System.out.println("The shortest path is " + shortest);
        //System.out.println("The second shortest path is " + secondShortest);
        //System.out.println("d0 is " + d0);
        System.out.println(secondShortest);

    }

    //floyd warshall function
    private static void floydWarshall(int matrix[][]) {
        int i, j, k;
        int INF = Integer.MAX_VALUE;
        shortest = Integer.MAX_VALUE;
        secondShortest = Integer.MAX_VALUE;

        for (k = 0; k < numOfCities; k++) {
            for (i = 0; i < numOfCities; i++) {
                for (j = 0; j < numOfCities; j++) {
                    if (matrix[i][k] != INF && matrix[k][j] != INF) {
                        //Update the shortest path inside the matrix
                        matrix[i][j] = Math.min(matrix[i][j], matrix[i][k] + matrix[k][j]);

                        //Find or update the shortest and second shortest paths
                        if (i == 0 && j == 1) {
                            if (matrix[i][k] + matrix[k][j] < shortest) {
                                secondShortest = shortest;
                                shortest = matrix[i][k] + matrix[k][j];
                            } else if (matrix[i][k] + matrix[k][j] < secondShortest || shortest == secondShortest) {
                                secondShortest = matrix[i][k] + matrix[k][j];
                            }
                        }
                    }
                }
            }
        }
        //System.out.println("before shortest " + shortest);
        //System.out.println("before 2nd shortest " + secondShortest);
        //System.out.println("before direct " + d0);

        //check if direct path of matrix[0][1] is less than shortest path
        if(d0 < shortest){
            secondShortest = shortest;
            shortest = d0;
        //check if direct path is less than second shortest path and the second shortest path is equal to INF
        } else if (d0 < secondShortest && secondShortest == Integer.MAX_VALUE){
            secondShortest = d0;
        }

//        System.out.println("The shortest path is " + shortest);
//        System.out.println("The second shortest path is " + secondShortest);
    }

}
