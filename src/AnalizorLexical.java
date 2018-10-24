import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class AnalizorLexical {
    private Map<String, Integer> codficationTable = new HashMap<>();
    private AtomDetector atomDetector;
    private Vector<FIPElement> tableFIP;
    private HashMap<Integer, String> tableTS;
    Vector<String> athoms;

    public AnalizorLexical(String filename) {
        atomDetector = new AtomDetector(filename);
        tableFIP = new Vector<>();
        tableTS = new HashMap<Integer, String>();
        athoms = atomDetector.getAthoms();
        buildCodeTable();
    }

    private void buildCodeTable() {
        codficationTable.put("identificator", 0);
        codficationTable.put("const", 1);
        codficationTable.put("+", 2);
        codficationTable.put("-", 3);
        codficationTable.put("/", 4);
        codficationTable.put("*", 5);
        codficationTable.put("%", 6);
        codficationTable.put("<", 7);
        codficationTable.put(">", 8);
        codficationTable.put("<>", 9);
        codficationTable.put("=", 10);
        codficationTable.put(":=", 11);
        codficationTable.put("(", 12);
        codficationTable.put(")", 13);
        codficationTable.put("<=", 14);
        codficationTable.put(">=", 15);
        codficationTable.put("[", 16);
        codficationTable.put("]", 17);
        codficationTable.put(":", 18);
        codficationTable.put(".", 19);
        codficationTable.put("/n", 20);
        codficationTable.put(" ", 21);
        codficationTable.put("readln", 22);
        codficationTable.put("writeln", 23);
        codficationTable.put("if", 24);
        codficationTable.put("else", 25);
        codficationTable.put("then", 26);
        codficationTable.put("while", 27);
        codficationTable.put("real", 28);
        codficationTable.put("integer", 29);
        codficationTable.put("string", 30);
        codficationTable.put("do", 31);
        codficationTable.put("var", 32);
        codficationTable.put("begin", 33);
        codficationTable.put("end", 35);
        codficationTable.put("'", 35);
        codficationTable.put(";", 35);
    }

    public void generateTables() {
        for (String athom : athoms) {
            if (codficationTable.containsKey(athom)) {// e var, op sau separator => cod -1
                tableFIP.add(new FIPElement(codficationTable.get(athom), -1));
            } else {
                int pos = cautaTS(athom);
                if (pos == -1) {
                    pos = addTS(athom);
                    if (isConst(athom))
                        tableFIP.add(new FIPElement(1, pos));
                    else
                        tableFIP.add(new FIPElement(0, pos));
                }
            }
        }

    }

    private boolean isConst(String athom) {
        try {
            int a = Integer.parseInt(athom);
            return true;
        } catch (Exception e) {
            try {
                double b = Double.parseDouble(athom);
                return true;
            } catch (Exception ee) {
                return (athom.startsWith("\"") && athom.endsWith("\""));
            }
        }
    }

    private int addTS(String athom) {
        final int hash = getHash(athom);
        tableTS.put(hash, athom);
        return hash;
    }

    private int cautaTS(String atom) {
        final int hash = getHash(atom);
        if (tableTS.containsKey(hash))
            return hash;
        return -1;
        //cauta daca exista deja in TS
    }

    private int getHash(final String value) {
        return value.hashCode();
    }

    public void printAthoms(final String fileName) throws IOException {
        BufferedWriter br = new BufferedWriter(new FileWriter(fileName));
        for (String a : athoms) {
            br.write(a + "\n");
        }
        br.close();

    }

    public void printFIP(final String fileName) throws IOException {
        BufferedWriter br = new BufferedWriter(new FileWriter(fileName));
        for (FIPElement a : tableFIP) {
            br.write(a.getCodAtom() + " " + a.getPozTS() + "\n");
        }
        br.close();

    }

    public void printTS(final String fileName) throws IOException {
        BufferedWriter br = new BufferedWriter(new FileWriter(fileName));
        for (Integer a : tableTS.keySet()) {
            br.write(a + " " + tableTS.get(a) + "\n");
        }
        br.close();

    }

    public void printERRORS(final String fileName) throws IOException {
        BufferedWriter br = new BufferedWriter(new FileWriter(fileName));
        for (LexicalError a : atomDetector.getErrors()) {
            br.write(a.toString()+ "\n");
        }
        br.close();

    }
}
