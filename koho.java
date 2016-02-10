
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
public class koho {
    
//    init variable 
    static int grid_size = 10;
    static int num_sample = 16;
    static int num_attribute = 13;
    static double weigth [][][] = new double [grid_size][grid_size][num_attribute];
    static int input [][] = new int [num_sample][num_attribute];
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        readData();
        for(int num_iteration = 0; num_iteration < num_sample; num_iteration++){
            
        }
    }
    
    public static void readData(){
        String path = "D:\\BVH\\MEE\\Intelligent System\\Task3\\koho\\src\\animal.txt";
        File file = new File(path);
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            int current_line = 0;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
//                String[] ary = line.split(",");
//                for (int i = 0; i < ary.length; i++) {
//                    if (i < this.NumClassInput) {
//                        this.DataInput[current_line][i] = Double.parseDouble(ary[i]);
//                    } else {
//                        this.DataOutput[current_line][i - this.NumClassInput] = Integer.parseInt(ary[i]);
//                    }
//                }
//                current_line++;
            }
            br.close();
            //showDataArray();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
