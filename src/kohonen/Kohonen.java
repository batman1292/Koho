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
    static int input_vec_dim = 13;
    static double weight[][][] = new double[grid_size][grid_size][num_attribute];
    static double input[][] = new double[num_sample][num_attribute];
    static String input_name[] = new String[num_sample];
    static double error[] = new double[num_sample];

    static double ETA_TIME_CONST = 4000;
    static double SIGMA_TIME_CONST = 4000;
    static int MAX_EPOCH = 20000;
    static double sigma_init = 0.9;
    static double eta_init = 0.9;

    static double mse_error[] = new double[MAX_EPOCH];
    
    static int output[][] = new int[grid_size][grid_size];

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        readData();
        random_weight();

        //train 
        double distance = 0, new_distance;

        int num_epoch,
                num_iteration,
                iteration_counter = 0;

        int nearest_weight_row_index, nearest_weight_column_index;

        double distance_sq_between_neuron,
                neighbour_function;

        double error_n[] = new double[num_sample+1];

        double eta, sigma;

        for (num_epoch = 0; num_epoch < MAX_EPOCH; ++num_epoch) {
            for (num_iteration = 0; num_iteration < num_sample; ++num_iteration) {
                /* Find the nearest weight to the current training pattern */
                double sum = 0.0;

                for (int i = 0; i < input_vec_dim; ++i) {
                    sum += (input[num_iteration][i] - weight[0][0][i])
                            * (input[num_iteration][i] - weight[0][0][i]);
                }
                distance = Math.sqrt(sum);
                nearest_weight_row_index = 0;
                nearest_weight_column_index = 0;

                for (int i = 0; i < grid_size; ++i) {
                    for (int j = 0; j < grid_size; ++j) {
                        sum = 0.0;
                        for (int k = 0; k < input_vec_dim; ++k) {
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
                error_n[num_iteration] = 0.0;
                for (int i = 0; i < input_vec_dim; ++i) {
                    error_n[num_iteration]
                            += (input[num_iteration][i] - weight[nearest_weight_row_index][nearest_weight_column_index][i])
                            * (input[num_iteration][i] - weight[nearest_weight_row_index][nearest_weight_column_index][i]);
                }

                /* adjust width of Gaussian neighbourhood function */
                sigma = sigma_init * Math.exp(-(iteration_counter / SIGMA_TIME_CONST));

                /* adjust learning rate parameter */
                eta = eta_init * Math.exp(-(iteration_counter / ETA_TIME_CONST));

                /* Adjust connection weight */
                for (int i = 0; i < grid_size; ++i) {
                    for (int j = 0; j < grid_size; ++j) {
output[i][j] = -1;
                        /* calculate distance between winning neuron and 
                         current neuron */
                        distance_sq_between_neuron
                                = (double) ((i - nearest_weight_row_index) * (i - nearest_weight_row_index)
                                + (j - nearest_weight_column_index) * (j - nearest_weight_column_index));

                        /* calculate pi(i,j) */
                        neighbour_function = Math.exp(-distance_sq_between_neuron / (2.0 * sigma * sigma));

                        /* adjust connection weight */
                        for (int k = 0; k < input_vec_dim; ++k) {
                            weight[i][j][k] += eta * neighbour_function * (input[num_iteration][k] - weight[i][j][k]);
                        }
                    }
                }
                iteration_counter++;
            }
//            System.out.println("distance=" + distance);
//            num_iteration--;

//            System.out.println("number of iterations = " + num_iteration);
            mse_error[num_epoch] = 0.0;
            for (int i = 0; i < num_sample; ++i) {
                mse_error[num_epoch] += error_n[num_iteration];
            }
            mse_error[num_epoch] = mse_error[num_epoch] / (double) num_sample;
        }
        System.out.println("Total number of iterations = " + iteration_counter);
        
//        test
        for (num_iteration = 0; num_iteration < num_sample; num_iteration++) {
            /* Find the nearest weight to the current training pattern */
            double sum = 0.0;

            for (int i = 0; i < input_vec_dim; ++i) {
                sum += (input[num_iteration][i] - weight[0][0][i])
                        * (input[num_iteration][i] - weight[0][0][i]);
            }
//            System.out.println("test sum =" + sum);
            distance = Math.sqrt(sum);
            nearest_weight_row_index = 0;
            nearest_weight_column_index = 0;
//System.out.println("test distance=" + distance);
            for (int i = 0; i < grid_size; ++i) {
                for (int j = 0; j < grid_size; ++j) {
                    
                    sum = 0.0;
                    for (int k = 0; k < input_vec_dim; ++k) {
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

            System.out.print("at num_iteration " + input_name[num_iteration] + "\t");
            System.out.print("nearest_weight_row_index = " + nearest_weight_row_index + "\t");
            System.out.println("nearest_weight_column_index = " + nearest_weight_column_index + "\t");
            output[nearest_weight_row_index][nearest_weight_column_index] = num_iteration;
//            System.out.println(output[nearest_weight_row_index][nearest_weight_column_index]);
        }
        
        for (int i = 0; i < grid_size; i++) {
            System.out.print("|\t");
            for (int j = 0; j < grid_size; j++) {
                if(output[i][j] > -1){
                    System.out.print(input_name[output[i][j]]+"\t");
                }else{
                    System.out.print("\t");
                }
//                 System.out.print(output[i][j]+"\t");
                System.out.print("|\t");
            }
            System.out.println("");
        }
    }

    public static void readData() {
        String path = "D:\\anatoliy\\MEE-Term2\\Intelligence System\\Koho\\animal.txt";
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
