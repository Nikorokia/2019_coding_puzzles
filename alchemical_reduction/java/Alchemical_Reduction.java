import java.util.ArrayList;

public class Alchemical_Reduction {
    // public static void runReduction(String s) {
    //     print("\nOriginal: " + s);
    //     print("Stable:   " + reduce(s));
    // }

    public static void main(String[] args) {
        System.out.println("Hello World!");
        if (args.length > 0 && args[0].equals("selftest")) {
            String input = "dabAcCaCBAcCcaDA";
            LabSample l = new LabSample(input);
            l.selftest();
        }
    }
}

class LabSample {
        char[] sequence; boolean reduced;
        LabSample(String s) {
            sequence = s.toCharArray();
        }
        public void reduce() {
            boolean r = true;
            while (r) {
                r = reduce_once();
            }
        }
        private boolean reduce_once() {
            boolean reactionOccurred = false;
            String sequence_new = "";
            if (sequence.length > 1) {
                int p = 0;
                while (p < sequence.length-1) {
                    print(p+" "+sequence[p]+" "+sequence[p+1]);
                    if (react(sequence[p], sequence[p+1])) {
                        p+=2;
                        reactionOccurred = true;
                    } else {
                        sequence_new += sequence[p];
                        p++;
                    }
                    if (p==sequence.length-1) {
                        sequence_new += sequence[p];
                    }
                }
            }
            sequence = sequence_new.toCharArray();
            return reactionOccurred;
        }
        private boolean react(char a, char b) {
            if (    (a==Character.toUpperCase(b)) || 
                    (b==Character.toUpperCase(a)) ) {
                return true;
            } else {
                return false;
            }
        }
        private void print(Object o) {
            System.out.println(o.toString());
        }
        public void selftest() {
            System.out.println(react('a','A'));
            System.out.println(sequence);
            reduce();
            System.out.println(sequence);
        }
    }