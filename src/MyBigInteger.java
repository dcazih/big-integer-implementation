public class MyBigInteger {

    // == Linked Node Class ==
    static class IntegerNode {
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
            num = num.substring(1);
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
    public MyBigInteger(MyBigInteger num) {
        sign = num.sign;
        numOfNodes = num.numOfNodes;
    }
    // Int Constructor
    public MyBigInteger(int num){
        this(String.valueOf(num));
    }
    // Long Constructor
    public MyBigInteger(long num){
        this(Long.toString(num));
    }
    // Empty Constructor
    public MyBigInteger(){
        sign = new IntegerNode(0);
    }

    // === Methods ===
    // Performs the addition operation between two BigIntegers
    public static MyBigInteger add(MyBigInteger int1, MyBigInteger int2){

        // Copy big integers for use
        MyBigInteger int1Copy = new MyBigInteger(int1);
        MyBigInteger int2Copy = new MyBigInteger(int2);
        MyBigInteger sum = new MyBigInteger();

        // Return recursive sum calculation
        return add(int1Copy, int2Copy, sum, new IntegerNode(0), 0, 0, 0);

    }
    private static MyBigInteger add(MyBigInteger int1, MyBigInteger int2, MyBigInteger sum, IntegerNode tempNode, int overflow, int underflow, int counter){

        // Add digits from each node together accounting for over/underflow
        int newNum = (int1.sign != null ? int1.sign.digits : 0 ) + (int2.sign != null ? int2.sign.digits : 0 ) + overflow - underflow;

        // Return condition
        if (newNum == 0 && (int1.sign == null || int2.sign == null)) {
            return sum;
        }

        // If newNum is longer than 4 digits calculate the overflow and underflow, then reassign newNum
        else if(newNum > 0 && String.valueOf(newNum).length() > 4){
            
            // Calculate the overflow/underflow based on the length of newNum
            String numString = String.valueOf(newNum);
            int flow = Integer.parseInt(numString.substring(0, numString.length()-4));
            if (flow > 0) overflow = flow;
            else underflow = flow;

            // If newNum contains leading zeros, remove them before converting back to int
            String digitsString = numString.substring(numString.length()-4);
            if (digitsString.charAt(0) == '0') {
                for (int i = 0; i < digitsString.length(); i++) {
                    if (digitsString.charAt(i) != '0') newNum = Integer.parseInt(digitsString.substring(i+1));
                }
            } else newNum = Integer.parseInt(digitsString); // else convert back to int

        }
        else { overflow = 0; underflow = 0; } // else reset flows for next recursive call

        // Assign a new node with digits = newNum to sum.next
        IntegerNode newNode = new IntegerNode(newNum);
        if (counter == 0) sum.sign = newNode;
        else tempNode.higher_positions = newNode;
        tempNode = newNode; // update tempNode

        // Break condition if both integers dont have higher positions and there is no over/underflow
        try {if (overflow != 0 && int1.sign.higher_positions == null && int2.sign.higher_positions == null) return sum;}
        catch (Exception ignored) {}

        // Move current node forward if integer's current node not null
        if (int1.sign != null) int1.sign = int1.sign.higher_positions;
        if (int2.sign != null) int2.sign = int2.sign.higher_positions;

        // Recursively adds each pair of nodes
        return add(int1, int2, sum, tempNode, overflow, underflow, counter + 1);
    }

    // Outputs a string representation of MyBigInteger
    public String toString(){
        String output = "";
        IntegerNode head = sign.higher_positions; // temp node
        while (head != null){ // iterates through list

            // Calculates any leading zeros
            String zeros = ""; int digitLength = String.valueOf(head.digits).length();
            if (head.higher_positions != null && digitLength < 4) for (int i = 0; i < 4 - digitLength; i++) zeros += "0";

            // Add leading zeros and node's digits to output
            output = zeros + head.digits + output;

            // Update current temp node
            if (head.higher_positions != null) head = head.higher_positions;
            else break;
        }

        // Return final output
        return (sign.digits == -1 ? "-" : "" ) + output;
    }

    public boolean equals(MyBigInteger num){

        // Break condition if integer lengths dont match
        if (numOfNodes != num.numOfNodes) return false;

        // Temp nodes
        IntegerNode head1 = sign;
        IntegerNode head2 = num.sign;

        // Iterate through integer's nodes
        while(head1 != null && head2 != null){

            // Check if node's digits match
            if (head1.digits != head2.digits) return false;

            // Move temp pointers forward
            head1 = head1.higher_positions;
            head2 = head2.higher_positions;
        }

        return true;
    }
}
