package problems;

import java.util.Scanner;

public class Suma {

    public static void main(String[] args) {

        System.out.print("Give n ");
        Scanner input = new Scanner(System.in);
        int n = input.nextInt();
        double suma = 0;
        int i = 0;
        while (i < n) {
            System.out.print("Give the next number: ");
            double nr = input.nextDouble();
            suma = suma + nr;
            i = i + 1;
        }

        System.out.println("problems.Suma = " + suma);
    }
}
