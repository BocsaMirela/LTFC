import java.io.File;
import java.util.Scanner;

public class UI {
    private AutomatFinitDeterminist automatFinitDeterminist;

    public UI(AutomatFinitDeterminist automatFinitDeterminist) {
        this.automatFinitDeterminist = automatFinitDeterminist;
    }

    public void displayMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean ok=true;
        while (ok) {
            System.out.println("Alege ce element sa se afiseze: ");
            System.out.println("    1:Multimea de stari Q:");
            System.out.println("    2:Alfabetul Sigma:");
            System.out.println("    3:Tranzitiile");
            System.out.println("    4:Starea initiala q0:");
            System.out.println("    5:Multimea starilor finale F");
            System.out.println("    0:Exit");

            String value = scanner.nextLine().trim();
            switch (value) {
                case "1":
                    System.out.println("Multimea de stari:");
                    System.out.print("Q={");
                    for (String state:automatFinitDeterminist.getStates()) {
                        System.out.print(state+" ");
                    }
                    System.out.println("}");
                    System.out.println();
                    break;
                case "2":
                    System.out.println("Alfabetul Sigma:");
                    System.out.print("S={");
                    for (String alfabet:automatFinitDeterminist.getAlphabet()) {
                        System.out.print(alfabet+" ");
                    }
                    System.out.println("}");
                    System.out.println();
                    break;
                case "3":
                    System.out.println("Tranzitiile:");
                    for (Transition tr:automatFinitDeterminist.getTransitions()) {
                        System.out.println(tr);
                    }
                    System.out.println();
                    break;
                case "4":
                    System.out.println("Starea initala q0="+automatFinitDeterminist.getInitialState());
                    System.out.println();
                    break;
                case "5":
                    System.out.println("Multimea starilor finale:");
                    System.out.print("F={");
                    for (String stateF:automatFinitDeterminist.getFinalStates()) {
                        System.out.print(stateF+" ");
                    }
                    System.out.println("}");
                    System.out.println();
                    break;
                default:
                    ok=false;
                    break;
            }
        }
    }
}
