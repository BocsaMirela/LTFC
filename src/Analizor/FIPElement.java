package Analizor;

import java.util.Objects;

public class FIPElement {
    private int codAtom;
    private int pozTS;

    public FIPElement(int codAtom, int pozTS) {
        this.codAtom = codAtom;
        this.pozTS = pozTS;
    }

    public int getCodAtom() {
        return codAtom;
    }

    public void setCodAtom(int codAtom) {
        this.codAtom = codAtom;
    }

    public int getPozTS() {
        return pozTS;
    }

    public void setPozTS(int pozTS) {
        this.pozTS = pozTS;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FIPElement that = (FIPElement) o;
        return codAtom == that.codAtom &&
                pozTS == that.pozTS;
    }

    @Override
    public int hashCode() {
        return Objects.hash(codAtom, pozTS);
    }
}
