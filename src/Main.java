
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            AutomatFinitDeterminist automatFinitDeterminist=new AutomatFinitDeterminist("D:\\RepoUniversity\\LTFC\\LFTC\\src\\automaton.txt");
            UI ui=new UI(automatFinitDeterminist);
            ui.displayMenu();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
