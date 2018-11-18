package AnalizorAutomatom;

import Automat.AutomatFinitDeterminist;

import java.io.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class AnalizorLexical {
    private Map<String, Integer> codficationTable = new HashMap<>();
    private Vector<FIPElement> tableFIP;
    private Vector<LexicalError> tableError;
    private Vector<String> tableAthoms;
    private HashMap<Integer, String> tableTS;
    AutomatFinitDeterminist afdIden;
    AutomatFinitDeterminist afdInt;
    AutomatFinitDeterminist afdR;
    AutomatFinitDeterminist afdConsts;

    Vector<String> athoms;

    public AnalizorLexical(String filename, AutomatFinitDeterminist identificatoriAFD, AutomatFinitDeterminist integersAFD, AutomatFinitDeterminist realsAFD, AutomatFinitDeterminist stringsAFD) {
        afdIden = identificatoriAFD;
        afdInt = integersAFD;
        afdR = realsAFD;
        afdConsts = stringsAFD;
        tableFIP = new Vector<>();
        tableError = new Vector<>();
        tableAthoms = new Vector<>();
        tableTS = new HashMap<Integer, String>();
        buildCodeTable();
    }

    private void buildCodeTable() {
        codficationTable.put("identificator", 0);
        codficationTable.put("constante", 1);
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
        codficationTable.put("const", 19);
        codficationTable.put("\n", 20);
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
//        codficationTable.put("'", 35);
        codficationTable.put(";", 35);
        codficationTable.put("end.", 36);

    }

    public void generateTables() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("D:\\RepoUniversity\\LTFC\\LFTC\\src\\program"), Charset.forName("UTF-8")));
            String line = reader.readLine().trim();
            int lin = 1;
            while (line != null && line.length() > 0) {
                while (line != null && line.length() > 0) {
                    line = line.trim();
                    String maybeR = afdR.getTheLongestAcceptedSequence(line);
                    String maybeIden = afdIden.getTheLongestAcceptedSequence(line);
                    String maybeConts = afdConsts.getTheLongestAcceptedSequence(line);
                    String maybeInt = afdInt.getTheLongestAcceptedSequence(line);
                    String maybeSep = "";
                    if (line.length() > 0)
                        maybeSep = String.valueOf(line.charAt(0));
                    String maybeSepD = "";
                    if (line.length() > 1)
                        maybeSepD = String.valueOf(line.charAt(0)).concat(String.valueOf(line.charAt(1)));
                    if (codficationTable.containsKey(maybeSep.toLowerCase())) {// e var, op sau separator => cod -1
                        tableAthoms.add(maybeSep);
                        line = line.substring(1);
                        tableFIP.add(new FIPElement(codficationTable.get(maybeSep), -1));
                    } else if (codficationTable.containsKey(maybeSepD.toLowerCase())) {// e var, op sau separator => cod -1
                        line = line.substring(2);
                        tableAthoms.add(maybeSepD);
                        tableFIP.add(new FIPElement(codficationTable.get(maybeSepD), -1));
                    } else if (codficationTable.containsKey(line)) {
                        tableAthoms.add(line);
                        tableFIP.add(new FIPElement(codficationTable.get(line), -1));
                        line = null;
                    } else if (codficationTable.containsKey(maybeIden)) {
                        line = line.substring(maybeIden.length());
                        tableAthoms.add(maybeIden);
                        tableFIP.add(new FIPElement(codficationTable.get(maybeIden), -1));
                    } else if (maybeR.equals("") && maybeConts.equals("") && maybeInt.equals("") && maybeIden.equals("")) {
                        tableError.add(new LexicalError("Error", lin, 0));
                    } else if (!maybeIden.equals("")) { ///e ientificato
                        tableAthoms.add(maybeIden);
                        int pos = cautaTS(maybeIden);
                        if (pos == -1) {
                            pos = addTS(maybeIden);
                            tableFIP.add(new FIPElement(0, pos));
                        }
                        line = line.substring(maybeIden.length());
                    } else if (!maybeConts.equals("")) { ///e constanta
                        line = line.substring(maybeConts.length());
                        char next = '?';
                        if (line.length() > 1)
                            next = line.charAt(0);
                        if (!codficationTable.containsKey(String.valueOf(next))) {
                            tableError.add(new LexicalError("Incorect constant", lin, 0));
                            while (!codficationTable.containsKey(String.valueOf(next)) && line != null) {
                                line = line.substring(1);
                                next = line.charAt(0);
                            }
                        } else {
                            tableAthoms.add(maybeConts);
                            int pos = cautaTS(maybeConts);
                            if (pos == -1) {
                                pos = addTS(maybeConts);
                                tableFIP.add(new FIPElement(1, pos));
                            }
                        }
                    } else if (maybeR.length() > maybeInt.length()) { //real
                        line = line.substring(maybeR.length());
                        char next = line.charAt(0);
                        if (!codficationTable.containsKey(String.valueOf(next))) {
                            tableError.add(new LexicalError("Incorect constant", lin, 0));
                            while (!codficationTable.containsKey(String.valueOf(next)) && line != null) {
                                line = line.substring(1);
                                next = line.charAt(0);
                            }
                        } else {
                            tableAthoms.add(maybeR);
                            int pos = cautaTS(maybeR);
                            if (pos == -1) {
                                pos = addTS(maybeR);
                                tableFIP.add(new FIPElement(1, pos));
                            }
                        }
                    } else if (maybeInt.length() > 0) { // e int
                        line = line.substring(maybeInt.length());
                        char next = line.charAt(0);
                        if (!codficationTable.containsKey(String.valueOf(next))) {
                            tableError.add(new LexicalError("Incorect constant", lin, 0));
                            while (!codficationTable.containsKey(String.valueOf(next)) && line != null) {
                                line = line.substring(1);
                                next = line.charAt(0);
                            }
                        } else {
                            tableAthoms.add(maybeInt);
                            int pos = cautaTS(maybeInt);
                            if (pos == -1) {
                                pos = addTS(maybeInt);
                                tableFIP.add(new FIPElement(1, pos));
                            }
                        }
                    } else {
                        line = line.substring(1);
                        tableError.add(new LexicalError("Incorect constant", lin, 0));
                    }
                }
                lin++;
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
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
        for (String a : tableAthoms) {
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
        for (LexicalError a : tableError) {
            br.write(a.toString() + "\n");
        }
        br.close();

    }
}
