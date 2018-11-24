package AnalizorAutomatom;

import Automat.AutomatFinitDeterminist;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        AnalizorLexical analizorLexical=null;
        try {
            AutomatFinitDeterminist identificatoriAFD=new AutomatFinitDeterminist("D:\\RepoUniversity\\LTFC\\LFTC\\src\\automatonIdentificatori.txt");
            AutomatFinitDeterminist integersAFD=new AutomatFinitDeterminist("D:\\RepoUniversity\\LTFC\\LFTC\\src\\automatonMyIntegers.txt");
            AutomatFinitDeterminist realsAFD=new AutomatFinitDeterminist("D:\\RepoUniversity\\LTFC\\LFTC\\src\\automatonMyReals.txt");
            AutomatFinitDeterminist stringsAFD=new AutomatFinitDeterminist("D:\\RepoUniversity\\LTFC\\LFTC\\src\\automatonConsts.txt");
            AutomatFinitDeterminist afdConstsPascal=new AutomatFinitDeterminist("D:\\RepoUniversity\\LTFC\\LFTC\\src\\AFDCConstPascal.txt");
            analizorLexical=new AnalizorLexical("D:\\RepoUniversity\\LTFC\\LFTC\\src\\program",identificatoriAFD,integersAFD,realsAFD,stringsAFD,afdConstsPascal);
            analizorLexical.generateTables();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
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
