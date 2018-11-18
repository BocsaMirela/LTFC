package AnalizorAutomatom;

public class LexicalError {
    private String msg;
    private int line;
    private int col;

    public LexicalError(final String msg, final int line, final int col){
        this.msg=msg;
        this.line=line;
        this.col=col;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "Analizor.LexicalError{" +
                "msg='" + msg + '\'' +
                ", line=" + line +
                ", col=" + col +
                '}';
    }
}
