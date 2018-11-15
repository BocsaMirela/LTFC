import java.util.Objects;

public class Transition implements Comparable<Transition> {
    private String initialState;
    private String finalState;
    private String value;

    public Transition(String initialState, String value, String finalState) {
        this.initialState = initialState;
        this.finalState = finalState;
        this.value = value;
    }

    public String getInitialState() {
        return initialState;
    }

    public void setInitialState(String initialState) {
        this.initialState = initialState;
    }

    public String getFinalState() {
        return finalState;
    }

    public void setFinalState(String finalState) {
        this.finalState = finalState;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int compareTo(Transition o) {
        return 0;
    }

    @Override
    public String toString() {
        return "T("+initialState +" , "+value+")={"+finalState+"}\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transition that = (Transition) o;
        return Objects.equals(initialState, that.initialState) &&
                Objects.equals(finalState, that.finalState) &&
                Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(initialState, finalState, value);
    }
}
