import java.util.ArrayList;

public class bracelets {
    static boolean verbose = false;
    public static ArrayList<Bracelet> generateBracelets(int numLength, int numColors) {
        ArrayList<ArrayList<Bracelet>> fullCollection = new ArrayList<ArrayList<Bracelet>>(numLength);
        int curLengthIndex = 0;
        ArrayList<Bracelet> sublist = new ArrayList<Bracelet>();

        //start the base case
        for (int c=0; c<numColors; c++) {
            sublist.add(new Bracelet(new Bead(c)));
            // System.out.println(String.format("Sub list %s of %s, adding Bracelet:", curLengthIndex+1, numLength));
            // System.out.println("    ["+Integer.toString(c)+"]");
        }
        fullCollection.add(sublist);
        
        while (curLengthIndex < numLength-1) {
            sublist = new ArrayList<Bracelet>();
            for (Bracelet b : fullCollection.get(curLengthIndex)) {
                for (int c=0; c<numColors; c++) {
                    Bracelet nB = b.copy();
                    nB.addBead(new Bead(c));
                    sublist.add(nB);
                    // System.out.println(String.format("Sub list %s of %s, adding Bracelet:", curLengthIndex+2, numLength));
                    // System.out.println("    "+nB);
                }
            }
            fullCollection.add(sublist);
            curLengthIndex++;
        }
        return fullCollection.get(fullCollection.size()-1); //send back the last sub list
    }
    public static ArrayList<Bracelet> refineBracelets(ArrayList<Bracelet> rawBracelets) {
        ArrayList<Bracelet> processedBracelets = new ArrayList<>(rawBracelets.size());
        Bracelet workingBracelet;
        int inequalityCount;
        while (rawBracelets.size() > 1) {
            inequalityCount = 0;
            workingBracelet = rawBracelets.remove(rawBracelets.size()-1);
            for (Bracelet rb : rawBracelets) {
                if (verbose) {System.out.println("Comparing "+workingBracelet+" to "+rb);}
                if (!workingBracelet.equals(rb)) {
                    inequalityCount++;
                }
            }
            if (inequalityCount==rawBracelets.size()) {
                processedBracelets.add(workingBracelet);
            }
        }
        processedBracelets.add(rawBracelets.get(0));
        return processedBracelets;
    }
    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("selftest")) {
            selftest();
            System.exit(0);
        }
        else if (args.length < 2) {
            System.out.println("\nUsage:\n    java bracelets [int: length] [int: number of colors]\n");
            System.exit(0);
        }
        int length=0, numColors=0;
        try {
            length    = Integer.parseInt(args[0]);
            numColors = Integer.parseInt(args[1]);
        } catch (Exception numberFormatException) {
            System.out.println("\nAn argument passed is not an integer:");
            System.out.println("    [0]: "+args[0]);
            System.out.println("    [1]: "+args[1]+"\n");
            System.exit(0);
        }

        ArrayList<Bracelet> bs = generateBracelets(length, numColors);
        if (verbose) {
            System.out.println("Unrefined list of bracelets:");
            for (Bracelet b : bs) { System.out.println(b);}
        }

        bs = refineBracelets(bs);
        if (verbose) {
            System.out.println("Refined list of bracelets:");
            for (Bracelet b : bs) { System.out.println(b);}
        }

        System.out.println("\nFor bracelets of length "
                            +length
                            +" and with "+numColors
                            +" different colors,");
        System.out.println("  the number of possible unique bracelets is "+bs.size()+".\n");
    }
    public static void selftest() {
        Bracelet xs = new Bracelet(new int[]{1,2,3,4});
        Bracelet ys = new Bracelet(new int[]{2,3,4,1});
        Bracelet zs = new Bracelet(new int[]{4,3,2,1});
        Bracelet ws = new Bracelet(new int[]{1,3,2,4});
        Bracelet vs = new Bracelet(new int[]{1,3,2,4,5});
        System.out.println(xs.equals(ys));                      //true
        System.out.println(xs.equals(zs));                      //true
        System.out.println(xs.equals(ws));                      //false
        System.out.println(xs.sameSequenceAs(ws));              //false
        System.out.println(xs.sameSequenceAs(vs));              //false
        System.out.println((new Bead(3)).equals(new Bead(3)));  //true
        System.out.println((new Bead(2)).equals(new Bead(3)));  //false
    }
}
class Bracelet {
    private ArrayList<Bead> beads;
    public Bracelet(ArrayList<Bead> bs) {
        beads = bs;
    }
    public Bracelet(Bead[] bs) {
        beads = new ArrayList<Bead>();
        for (Bead b : bs) {
            beads.add(b);
        }
    }
    public Bracelet(Bead b) {
        beads = new ArrayList<Bead>();
        beads.add(b);
    }
    public Bracelet(Bracelet b) {
        beads = b.getBeads();
    }
    public Bracelet(int[] cs) {
        beads = new ArrayList<Bead>();
        for (int c : cs) {
            beads.add(new Bead(c));
        }
    }
    public Bracelet copy() {
        ArrayList<Bead> newBs = new ArrayList<Bead>();
        for (Bead b : beads) {
            newBs.add(b.copy());
        }
        return new Bracelet(newBs);
    }
    public ArrayList<Bead> getBeads() {
        return beads;
    }
    public Bead getBeadAt(int i) {
        return beads.get(i);
    }
    public boolean addBead(Bead b) {
        beads.add(b);
        return true;
    }
    public boolean addBead(int i) {
        beads.add(new Bead(i));
        return true;
    }
    public boolean rotate() {
        beads.add(beads.remove(0));
        return true;
    }
    public boolean reverse() {
        ArrayList<Bead> rBs = new ArrayList<>(beads.size());
        for (int i=beads.size()-1;i>=0;i--) {
            rBs.add(beads.remove(i));
        }
        beads = rBs;
        return true;
    }
    public int length() {
        return beads.size();
    }
    public String toString() {
        String s = "[ ";
        for (Bead b : beads) {
            s += b.toString()+" ";
        }
        s += "]";
        return s;
    }
    public boolean equals(Bracelet b1) {
        boolean equal = false;
        if (length() != b1.length()) {
            return false;
        } else {
            Bracelet wb = copy();
            for (int i=0; i<length(); i++) {
                wb.rotate();
                if (wb.sameSequenceAs(b1)) {equal=true;}
            }
            wb.reverse();
            for (int i=0; i<length(); i++) {
                wb.rotate();
                if (wb.sameSequenceAs(b1)) {equal=true;}
            }
        }
        return equal;
    }
    public boolean sameSequenceAs(Bracelet b1) {
        int equalBeadCount = 0;
        if (length() != b1.length()) {
            return false;
        } else {
            for (int i=0; i<length(); i++) {
                if (getBeadAt(i).equals(b1.getBeadAt(i))) {
                    equalBeadCount++;
                }
            }
        }
        return equalBeadCount == length();
    }
    public static void main(String[] args) {
        System.out.println("Wrong class. Run:\n\n    java bracelets [int: length] [int: number of colors]\n");
    }
}
class Bead {
    private int color;
    public Bead(int c) {
        color = c;
    }
    public Bead copy() {
        return new Bead(color);
    }
    public int getColor() {
        return color;
    }
    public String toString() {
        return Integer.toString(color);
    }
    public boolean equals(Bead b1) {
        return (color==b1.getColor());
    }
    public static void main(String[] args) {
        System.out.println("Wrong class. Run:\n\n    java bracelets [int: length] [int: number of colors]\n");
    }
}