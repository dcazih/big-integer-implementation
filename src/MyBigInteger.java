public class MyBigInteger {

    // == Linked Node Class ==
    class IntegerNode {
        int digits; // 4 digits in the current node
        IntegerNode higher_positions; // digits in the higher

        public IntegerNode(int digits){
            this.digits = digits;
            higher_positions = null;
        }
    }

    // == Head of Linked List ==
    int numOfNodes = 1;
    IntegerNode sign;

    // === Constructors ===
    // String Constructor (Main Constructor)
    public MyBigInteger(String num){

        // Create first node
        sign = new IntegerNode(0);

        // Additional Checks:
        //if (num.isEmpty()){}
        //else if (!num.isNumeric()){}

        // Change sign if negative int
        if (num.charAt(0) == '-') {
            sign.digits = -1;
            num = num.substring(1, num.length());
        }

        // Traverse String num and add each section by 4's to sign
        // Ex: num = 24 2300 2345 -> add(2345) -> add(2300) -> add(24)
        String partOfNum = "";
        IntegerNode current = null;
        int breakCondition = (num.length() % 4 == 0) ? (num.length()/4) : (num.length()/4) + 1;
        for(int i = 0; i < breakCondition; i++){

            // Select 4 char long substring of num starting from end of string
            if (num.length() % 4 != 0 && i == breakCondition-1) partOfNum = num.substring(0, num.length() - (i*4));// if length of num not divisible by 4 and we are at the last 4 char substring
            else partOfNum = num.substring(num.length()-((i+1)*4), num.length()-(i*4));

            // Create node from parsed num string
            IntegerNode newNode = new IntegerNode(Integer.parseInt(partOfNum));

            // Upon first addition add to sign, else add to current node
            if (i == 0) sign.higher_positions = newNode;
            else current.higher_positions = newNode;
            current = newNode; // update current node
            numOfNodes++; // update number of nodes
        }
    }
    // Copy Constructor
    public MyBigInteger(MyBigInteger num){
        // NULL for now
    }
    // Int Constructor
    public MyBigInteger(int num){
        this(String.valueOf(num));
    }
    // Long Constructor
    public MyBigInteger(long num){
        this(Long.toString(num));
    }

    // === Methods ===
    // Performs the addition operation between two BigIntegers
    public static MyBigInteger add(MyBigInteger int1, MyBigInteger int2){
        return null;
    }

    // Outputs a string representation of MyBigIntegers
    public String toString(){
        String output = "";
        IntegerNode head = sign.higher_positions;
        while (head != null){
            output = head.digits + output;
            if (head.higher_positions != null) head = head.higher_positions;
            else break;
        }

        return (sign.digits == -1 ? "-" : "" ) + output;
    }

    public boolean equals(MyBigInteger num){
        if (numOfNodes != num.numOfNodes) return false;

        IntegerNode head1 = sign;
        IntegerNode head2 = num.sign;
        while(head1 != null && head2 != null){

            // Check if digits match
            if (head1.digits != head2.digits) return false;

            // Move head pointers forward
            head1 = head1.higher_positions;
            head2 = head2.higher_positions;
        }

        return true;
    }

}
