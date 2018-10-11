import java.util.Scanner;

public class Suma {
    final static double PI = 3.14;

    public static void main(String[] args) {

        System.out.print("Give n ");
        Scanner input = new Scanner(System.in);
        int n = input.nextInt();
        double suma = 0;
        for (int i = 0; i < n; i++) {
            System.out.print("Give the next number: ");
            double nr = input.nextDouble();
            suma = suma + nr;
        }

        System.out.println("Suma = " + suma);
    }
}
