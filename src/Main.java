import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        AnalizorLexical analizorLexical=new AnalizorLexical("D:\\RepoUniversity\\LTFC\\LFTC\\src\\program");
        analizorLexical.generateTables();
        try {
            analizorLexical.printAthoms("D:\\RepoUniversity\\LTFC\\LFTC\\src\\tables\\atomi.txt");
            analizorLexical.printFIP("D:\\RepoUniversity\\LTFC\\LFTC\\src\\tables\\FIP.txt");
            analizorLexical.printTS("D:\\RepoUniversity\\LTFC\\LFTC\\src\\tables\\TS.txt");
            analizorLexical.printERRORS("D:\\RepoUniversity\\LTFC\\LFTC\\src\\tables\\errors.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
