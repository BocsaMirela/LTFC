package problems;

import java.util.Scanner;

public class Cerc {
    final static double PI = 3.14;

    public static void main(String[] args) {

        System.out.print("Enter the radius ");
        Scanner input = new Scanner(System.in);
        double raza = input.nextDouble();
        double perimetru = 2 * PI * raza;
        double aria = PI * raza * raza;

        System.out.println("Perimetru = " + perimetru);
        System.out.println("Aria = " + aria);
    }
}
