package kohonen;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author 2IDR-PC
 */
public class Kohonen {

//    init variable 
    static int grid_size = 10;
    static int num_sample = 16;
    static int num_attribute = 13;
    static int input_vec_dim = 2;
    static double weight[][][] = new double[grid_size][grid_size][num_attribute];
    static double input[][] = new double[num_sample][num_attribute];
    static String input_name[] = new String[num_sample];
    static double error[] = new double[num_sample];

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        readData();
        random_weight();

        /* show input 
         for (int i=0; i < input.length; i++) {
         for (int j=0; j < input[i].length; j++) {
         System.out.print("input[" + i + "][" + j + "]=" + input[i][j] + ",");
         }
         System.out.println("type_input[" + i + "]=" + type_input[i]);
         }
         */
        /* show weight 
         int count = 0;
         for (int i = 0; i < grid_size; i++) {
         for (int j = 0; j < grid_size; j++) {
         for (int k = 0; k < input_vec_dim; k++) {
         count++;
         System.out.println("weight[" + i + "][" + j + "][" + k + "]=" + weight[i][j][k] + ",count=" + count);
         }
         }
         }
         */
        for (int num_iteration = 0; num_iteration < num_sample; num_iteration++) {
            /* Find the nearest weight to the current training pattern */
            double sum = 0.0;
            
            double distance, new_distance;
            
            int nearest_weight_row_index, nearest_weight_column_index;
            
            double eta, sigma;
            
            for (int i = 0; i < input_vec_dim; ++i) {
                sum += (input[num_iteration][i] - weight[0][0][i])
                        * (input[num_iteration][i] - weight[0][0][i]);
            }
            distance = Math.sqrt(sum);
            nearest_weight_row_index = 0;
            nearest_weight_column_index = 0;
            System.out.println("distance=" + distance);

            for (int i = 0; i < grid_size; i++) {
                for (int j = 0; j < grid_size; j++) {
                    sum = 0.0;
                    for (int k = 0; k < input_vec_dim; k++) {
                        sum += (input[num_iteration][k] - weight[i][j][k])
                                * (input[num_iteration][k] - weight[i][j][k]);
                    }
                    new_distance = Math.sqrt(sum);

                    if (new_distance < distance) {
                        distance = new_distance;
                        nearest_weight_row_index = i;
                        nearest_weight_column_index = j;
                    }
                } /* for */

            } /* for */

            /* Calculate error between the nearest centre and the current
             training pattern */
            error[num_iteration] = 0.0;
            for (int i = 0; i < input_vec_dim; i++) {
                error[num_iteration]
                        += (input[num_iteration][i] - weight[nearest_weight_row_index][nearest_weight_column_index][i])
                        * (input[num_iteration][i] - weight[nearest_weight_row_index][nearest_weight_column_index][i]);
            }

            /* adjust width of Gaussian neighbourhood function */
            //sigma = sigma_ini * exp(-(double) (iteration_counter) / SIGMA_TIME_CONST);

            /* adjust learning rate parameter */
            //eta = eta_ini * exp(-(double) (iteration_counter) / ETA_TIME_CONST);
        }
    }

    public static void readData() {
        String path = "D:\\anatoliy\\MEE-Term2\\Intelligence System\\Kohonen\\animal.txt";
        File file = new File(path);
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            int current_line = 0;
            while ((line = br.readLine()) != null) {
//                System.out.println(line);
                String[] ary = line.split(",");
                for (int i = 0; i < ary.length; i++) {
                    if (i < num_attribute) {
                        input[current_line][i] = Double.parseDouble(ary[i]);
                    } else {
                        input_name[current_line] = ary[i];
                    }
                }
                current_line++;
            }
            br.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void random_weight() {
        for (int i = 0; i < grid_size; i++) {
            for (int j = 0; j < grid_size; j++) {
                for (int k = 0; k < input_vec_dim; k++) {
                    weight[i][j][k] = (double) Math.random();
                }
            }
        }
    }
}