import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Vector;

public class AtomDetector {
    public static final String EMPTY_STRING = "";
    private String fileName;
    private Vector<String> athoms;
    private Vector<String> separators;
    private Vector<String> operators;
    private Vector<LexicalError> errors;

    public AtomDetector(String fileName) {
        this.fileName = fileName;
        athoms = new Vector<>();
        errors = new Vector<>();
        separators = generateSeparators();
        operators = generateOperators();
        try {
            detectAthoms();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Vector<String> generateSeparators() {
        Vector<String> sep = new Vector<>();
        sep.add(" ");
        sep.add(";");
        sep.add(":");
        sep.add(")");
        sep.add("(");
        sep.add("\n");
        sep.add("[");
        sep.add("]");
//        sep.add(".");
        return sep;
    }

    private Vector<String> generateOperators() {
        Vector<String> oprs = new Vector<>();
        oprs.add("+");
        oprs.add("-");
        oprs.add("/");
        oprs.add("*");
        oprs.add("%");
        oprs.add("<");
        oprs.add(">");
        oprs.add(":");
        oprs.add("=");
        return oprs;
    }

    public Vector<String> getAthoms() {
        return athoms;
    }

    public void setAthoms(Vector<String> athoms) {
        this.athoms = athoms;
    }

    private void detectAthoms() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), Charset.forName("UTF-8")));
        int c;
        String atom = EMPTY_STRING;
        int line = 1;
        int col = 0;
        while ((c = reader.read()) != -1) {
            String s = String.valueOf((char) c);
            //am ajus la un separator sau operator
            if (operators.contains(s) || separators.contains(s) || s.matches("\n")) {
                if (atom.equals("<") && s.equals(">")) {
                    athoms.add("<>");
                    atom = EMPTY_STRING;
                    continue;
                } else if (atom.equals("<") && s.equals("=")) {
                    athoms.add("<=");
                    atom = EMPTY_STRING;
                    continue;
                } else if (atom.equals(">") && s.equals("=")) {
                    athoms.add(">=");
                    atom = EMPTY_STRING;
                    continue;
                }
                else if (!atom.equals(EMPTY_STRING)) {
                    addAthom(atom, line, col);
                    atom = EMPTY_STRING;
                }
                //cautam :=
                if (!((s.equals(":")) || (s.equals("<")) || (s.equals(">")))) {//operator simplu
                    athoms.add(s);
                    atom = EMPTY_STRING;
                } else if (s.equals(":")) {
                    c = reader.read();
                    String next = String.valueOf((char) c);
                    if (next.equals("=")) {
                        athoms.add(":=");
                    } else {
                        athoms.add(":");
                        atom = "" + next;
                    }

                } else if (s.equals("<")) { //cautam <>
                    c = reader.read();
                    String next = String.valueOf((char) c);
                    if (next.equals(">")) {
                        athoms.add("<>");
                    } else if (next.equals("=")) {
                        athoms.add("<=");
                    } else {
                        athoms.add("<");
                        atom = "" + next;
                    }
                } else if (s.equals(">")) { //cautam <>
                    c = reader.read();
                    String next = String.valueOf((char) c);
                    if (next.equals("=")) {
                        athoms.add(">=");
                    } else {
                        athoms.add(">");
                        atom = "" + next;
                    }
                }
                if (c == '"') {
                    c = reader.read();
                    String next = String.valueOf((char) c);
                    atom = "\'".concat(next);
                    while (c != '\'' && c != -1) {
                        c = reader.read();
                        next = String.valueOf((char) c);
                        atom = atom.concat(next);
                        if (!apartineAlfabetului(c) && c != '\'') {
                            errors.add(new LexicalError(next + " Is not in the alphabet", line, col));
                        }
                        if (s.matches("\r")) {
                            line++;
                            col = 1;
                        }
                        col++;
                    }
                    athoms.add(atom);
                    atom = EMPTY_STRING;
                }
                col++;
            } else if (c == '\'') {
                c = reader.read();
                String next = String.valueOf((char) c);
                atom = "\'".concat(next);
                while (c != '\'' && c != -1) {
                    c = reader.read();
                    next = String.valueOf((char) c);
                    atom = atom.concat(next);
                    if (!apartineAlfabetului(c) && c != '\'') {
                        errors.add(new LexicalError(next + " Is not in the alphabet", line, col));
                    }
                    if (s.matches("\r")) {
                        line++;
                        col = 1;
                    }
                    col++;
                }
                athoms.add(atom);
                atom = EMPTY_STRING;
                col++;
            } else if (s.matches("\r")) {
                line++;
                col = 1;

            } else if ((c >= 'A' && c <= 'z') || (c >= '0' && c <= '9') || c == '.') {//nu am ajuns la un operator si caracterul apartine alfabetului
                atom = atom.concat(s);
                col++;
            } else {
                errors.add(new LexicalError((char) c + " Is not recognized", line, col));
                col++;
            }

        }
        addAthom(atom, line, col);

    }

    private boolean apartineAlfabetului(int c) {
        return (c >= 'A' && c <= 'z') || (c >= '0' && c <= '9');
    }

    private void addAthom(String atom, int line, int col) {
        if (!EMPTY_STRING.equals(atom)) {
            atom=atom.trim();
            if (isNumber(atom)) {
                if (!correctConstant(atom)) {
                    errors.add(new LexicalError(atom + " Not a corect constant", line, col));
                }
            } else if (atom.length() > 1 && !isaLiteral(atom)) {//nu e corect
                errors.add(new LexicalError(atom + " Is not a literal", line, col));

            }
            if (atom.length() > 256) {
                errors.add(new LexicalError(atom + " Literal is too long", line, col));
            }
            athoms.add(atom);
        }
    }

    private boolean isNumber(String atom) {
        return (atom.matches("^\\d+$")) || atom.matches("^[-+]?[0-9]*\\.?[0-9]+$");

    }

    public boolean isaLiteral(String atom) {
        return atom.matches("(^[a-zA-Z]+[a-zA-Z0-9]*$)|end\\.");
    }

    private boolean correctConstant(final String atom) {
        if (atom.matches("^\\d+$")) {//e numar
            if (atom.charAt(0) == '0' && atom.length() > 1) return false;
        } else if (atom.matches("^[-+]?[0-9]*\\.?[0-9]+$")) {
            if ((atom.charAt(0) == '0') && (atom.charAt(1) != '.')) return false;
        }
        return true;
    }

    private boolean isFirstCharacterDigit(final String atom) {
        return atom.charAt(0) >= '0' && atom.charAt(0) <= '9';
    }

    public Vector<LexicalError> getErrors() {
        return errors;
    }
}
