import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class AutomatFinitDeterminist {
    private Set<String> states;
    private Set<String> finalStates;
    private Set<Transition> transitions;
    private Set<String> alphabet;
    private String initialState;

    public AutomatFinitDeterminist(final String fileName) throws FileNotFoundException {
        states = new HashSet<>();
        finalStates = new HashSet<>();
        transitions = new HashSet<>();
        alphabet = new HashSet<>();
        createAutomaton(fileName);

    }

    public Set<String> getStates() {
        return states;
    }

    public void setStates(Set<String> states) {
        this.states = states;
    }

    public Set<String> getFinalStates() {
        return finalStates;
    }

    public void setFinalStates(Set<String> finalStates) {
        this.finalStates = finalStates;
    }

    public Set<Transition> getTransitions() {
        return transitions;
    }

    public void setTransitions(Set<Transition> transitions) {
        this.transitions = transitions;
    }

    public Set<String> getAlphabet() {
        return alphabet;
    }

    public void setAlphabet(Set<String> alphabet) {
        this.alphabet = alphabet;
    }

    public String getInitialState() {
        return initialState;
    }

    public void setInitialState(String initialState) {
        this.initialState = initialState;
    }

    private void createAutomaton(String fileName) throws FileNotFoundException {
        //prima linie - starile = Q
        //linia 2 - alfabetul =Sigma
        //linia 3 - starea initiala =q0
        //linia 4 - starile finale =F
        //linia 5 - nr de tranzitii
        //next lines - cate o tranzitie de forma stare initala, valoare, stare finala
        Scanner scanner = new Scanner(new File(fileName));
        String[] stari = scanner.nextLine().split(" ");
        Collections.addAll(states, stari);

        String[] alfabet = scanner.nextLine().split(" ");
        Collections.addAll(alphabet, alfabet);

        initialState = scanner.nextLine().trim();

        String[] finale = scanner.nextLine().split(" ");
        Collections.addAll(finalStates, finale);

        // citim nrTranzitii
        int nrTranzitii = Integer.parseInt(scanner.nextLine());
        // citim tranzitiile
        String[] tranzitie;
        for (int i = 0; i < nrTranzitii; i++) {
            tranzitie = scanner.nextLine().split(" ");
            transitions.add(new Transition(tranzitie[0], tranzitie[1], tranzitie[2]));
            alphabet.add(tranzitie[1]);
        }
    }

    public boolean accepts(String sequence) {
        String curentState = initialState;
        while (sequence.length() > 0) {
            String first = sequence.substring(0, 1);
            String nextState = getNextState(curentState, first);
            if (nextState!=null){
                curentState=nextState;
                sequence=sequence.substring(1);
            }else{
                return false;
            }
        }
        return finalStates.contains(curentState);
    }

    private String getNextState(String curentState, String first) {
        for (Transition tr : transitions)
            if (tr.getInitialState().equals(curentState) && tr.getValue().equals(first))
                return tr.getFinalState();
        return null;
    }

    public String getTheLongestAcceptedSequence(String sequence){
        String curentState = initialState;
        String acceptedSeq="";
        while (sequence.length() > 0) {
            String first = sequence.substring(0, 1);
            String nextState = getNextState(curentState, first);
            if (nextState!=null){
                curentState=nextState;
                sequence=sequence.substring(1);
                acceptedSeq=acceptedSeq.concat(first);
            }else{
              break;
            }
        }
        return acceptedSeq;
    }
}
