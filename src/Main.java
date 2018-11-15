
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            AutomatFinitDeterminist automatFinitDeterminist=new AutomatFinitDeterminist("D:\\RepoUniversity\\LTFC\\LFTC\\src\\AFDCConstIntC++.txt");
            UI ui=new UI(automatFinitDeterminist);
//            ui.displayMenu();
//            System.out.println("01  - "+automatFinitDeterminist.accepts("01"));
//            System.out.println("010  - "+automatFinitDeterminist.accepts("010"));
//            System.out.println("0111  - "+automatFinitDeterminist.accepts("0111"));
//            System.out.println("0101  - "+automatFinitDeterminist.accepts("0101"));
//            System.out.println("0  - "+automatFinitDeterminist.accepts("0"));
//            System.out.println("1  - "+automatFinitDeterminist.accepts("1"));
//
            System.out.println();
            System.out.println("01  - longest: "+automatFinitDeterminist.getTheLongestAcceptedSequence("01"));
            System.out.println("010  - longest: "+automatFinitDeterminist.getTheLongestAcceptedSequence("010"));
            System.out.println("0191  - longest: "+automatFinitDeterminist.getTheLongestAcceptedSequence("0191"));
            System.out.println("0109  - longest: "+automatFinitDeterminist.getTheLongestAcceptedSequence("0109" +
                    ""));
            System.out.println("0  - longest: "+automatFinitDeterminist.getTheLongestAcceptedSequence("0"));
            System.out.println("1  -longest:  "+automatFinitDeterminist.getTheLongestAcceptedSequence("1"));

            System.out.println("01  - "+automatFinitDeterminist.accepts("01"));
            System.out.println("18446744073709550592ull  - "+automatFinitDeterminist.accepts("18446744073709550592ull"));
            System.out.println("052  - "+automatFinitDeterminist.accepts("052"));
            System.out.println("092  - "+automatFinitDeterminist.accepts("092"));
            System.out.println("02229 - "+automatFinitDeterminist.accepts("02229"));
            System.out.println("02229u - "+automatFinitDeterminist.accepts("02229u"));

            System.out.println("0x2a  - "+automatFinitDeterminist.accepts("0X2a"));
            System.out.println("0X2A  - "+automatFinitDeterminist.accepts("0X2A"));
            System.out.println("0X2uA  - "+automatFinitDeterminist.accepts("0X2uA"));
            System.out.println("0X2ll  - "+automatFinitDeterminist.accepts("0X2ll"));

            System.out.println("0b101010  - "+automatFinitDeterminist.accepts("0b101010"));
            System.out.println("0b109010  - "+automatFinitDeterminist.accepts("0b109010"));

            System.out.println("12345678901234567890u     - "+automatFinitDeterminist.accepts("12345678901234567890u"));
            System.out.println("09  - "+automatFinitDeterminist.accepts("09"));
            System.out.println("10  - "+automatFinitDeterminist.accepts("10"));
            System.out.println("1uull  - "+automatFinitDeterminist.accepts("1uull"));
            System.out.println("1Ull  - "+automatFinitDeterminist.accepts("1Ull"));
            System.out.println("1LlU  - "+automatFinitDeterminist.accepts("1LlU"));
            System.out.println("1Ulu  - "+automatFinitDeterminist.accepts("1Ulu"));
            System.out.println("0u  - "+automatFinitDeterminist.accepts("0u"));
            System.out.println("0ulUL  - "+automatFinitDeterminist.accepts("0ulUL"));
            System.out.println("0b0x123  - "+automatFinitDeterminist.accepts("0b0x123"));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
