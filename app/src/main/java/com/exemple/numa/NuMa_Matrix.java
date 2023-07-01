package com.exemple.numa;

import java.util.ArrayList;
import java.util.Random;

public class NuMa_Matrix {
    static final int BEGINNER = 4;
    static final int EASY = 6;
    static final int MEDIUM = 8;
    static final int HARD = 10;


    static private ArrayList<ArrayList<Integer>> matrix = new ArrayList<>();
    static private ArrayList<ArrayList<Integer>> solution_matrix = new ArrayList<>();
    static private ArrayList<Integer> columns_results = new ArrayList<>();
    static private ArrayList<Integer> rows_results = new ArrayList<>();

    static Random random = new Random();

    static void FillMatrix(int difficulty){
        for(int j=0;j<difficulty;j++){
            rows_results.add(0);
            columns_results.add(0);
            ArrayList<Integer> row = new ArrayList<>();
            ArrayList<Integer> solution_row = new ArrayList<>();
            for(int i=0;i<difficulty;i++){
                row.add(random.nextInt(20+10*((difficulty/4)-1))+1);
                solution_row.add(0);
            }
            matrix.add(row);
            solution_matrix.add(solution_row);
        }
        FillSolutionMatrix(difficulty);
    }

    private static void FillSolutionMatrix(int difficulty){
        for(int i=0;i<difficulty;i++){
            int rand = random.nextInt(difficulty)+1;
            int sum=0;
            for(int j=0;j<difficulty;j++){
                int val=random.nextInt(2);
                if(difficulty-1-j==rand-sum){
                    val=1;
                }
                sum+=val;
                solution_matrix.get(i).set(j,val);
                if(val==1){
                    rows_results.set(i,rows_results.get(i)+matrix.get(i).get(j));
                    columns_results.set(j,columns_results.get(j)+matrix.get(i).get(j));
                }
                if(sum==rand){
                    break;
                }
            }
        }
    }

    public static ArrayList<ArrayList<Integer>> getMatrix() {
        return matrix;
    }

    public static ArrayList<ArrayList<Integer>> getSolution_matrix() {
        return solution_matrix;
    }

    public static ArrayList<Integer> getColumns_results() {
        return columns_results;
    }

    public static ArrayList<Integer> getRows_results() {
        return rows_results;
    }
}
