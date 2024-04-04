import java.util.Stack;

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

    // == Head of MyBigInteger's Linked List ==
    IntegerNode sign;
    int numOfNodes = 1;

    // === Constructors ===
    // String Constructor (Main Constructor)
    public MyBigInteger(String num){

        // Create first node
        sign = new IntegerNode(0);

        if (num.isEmpty()) return;

        // Ensure string is numeric
        else if (!isNumeric(num)){
            System.out.println("ERROR!\nIllegalInputValueException: Enter numeric values only");
            return;
        }

        // Change sign if negative int
        else if (num.charAt(0) == '-') {
            sign.digits = -1;
            num = num.substring(1);
        }

        // Traverse String num and add each section by 4's to sign
        // Ex: num = 24 2300 2345 -> add(2345) -> add(2300) -> add(24)
        String partOfNum;
        IntegerNode current = null;
        int breakCondition = (num.length() > 4) ? ((num.length() % 4 == 0) ? (num.length()/4) : (num.length()/4) + 1) : 1;
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
    public boolean isNumeric(String s) {
        return s != null && s.matches("[-+]?\\d*\\.?\\d+");
    }
    // (Dmitri's Implementation) Performs the addition operation between two BigIntegers
    public static MyBigInteger add(MyBigInteger int1, MyBigInteger int2){
        return add(new MyBigInteger(int1), new MyBigInteger(int2), new MyBigInteger(), new IntegerNode(0), int1.sign.digits, int2.sign.digits, 0, 0);
    }
    private static MyBigInteger add(MyBigInteger int1, MyBigInteger int2, MyBigInteger sum, IntegerNode tempNode, int int1sign, int int2sign, int overflow, int counter){

        // If integers both negative change sign of sum to negative
        if (counter == 0 && int1sign == -1 && int2sign == -1) {
            sum.sign.digits = -1;
        }

        // If integers are negative, subtract them
        else if (int1sign == -1 && int2sign == 0) {
            return subtract(int1, int2, new MyBigInteger(), new IntegerNode(0), int1.sign.digits,-1, 0, 0);
        }
        else if (int1sign == 0 && int2sign == -1) {
            int2.sign.digits = 0;
            return subtract(int1, int2, new MyBigInteger(), new IntegerNode(0), int1.sign.digits,0, 0, 0);
        }

        // Main line for addition: int (positive) + int (positive) = int (positive)
        int nodeSum = (int1.sign != null ? int1.sign.digits : 0 ) + (int2.sign != null ? int2.sign.digits : 0 ) + overflow;

        // Return condition: if the end of both nums has been reached and no overflows are represent
        if (nodeSum == 0 && int1.sign == null && int2.sign == null) return sum;

        // If nodeSum is longer than 4 digits calculate the overflow, then reassign nodeSum
        else if(nodeSum > 0 && String.valueOf(nodeSum).length() > 4){

            overflow = 1;

            // If nodeSum contains leading zeros, remove them before converting back to int
            String sumString = String.valueOf(nodeSum).substring(String.valueOf(nodeSum).length()-4);
            if (sumString.charAt(0) == '0') {
                for (int i = 0; i < sumString.length(); i++) {
                    if (sumString.charAt(i) != '0') {
                        nodeSum = Integer.parseInt(sumString.substring(i));
                        break;
                    } else if (i == sumString.length() - 1) nodeSum = 0;
                }
            } else nodeSum = Integer.parseInt(sumString); // else if no leading zeros, convert back to int
        } else overflow = 0; // else reset flows for next recursive call

        // Create new node with nodeSum, add to list
        IntegerNode newNode = new IntegerNode(nodeSum);
        if (counter != 0) {
            if (counter == 1) sum.sign.higher_positions = newNode;
            else tempNode.higher_positions = newNode;
            sum.numOfNodes++;
            tempNode = newNode; // update tempNode
        }

        // Return condition: if both integers don't have higher positions and no overflow present
        if ((int1.sign != null && int2.sign != null) && (int1.sign.higher_positions == null && int2.sign.higher_positions == null)) return sum;

        // Move current node forward if integer's current node not null
        if (int1.sign != null) int1.sign = int1.sign.higher_positions;
        if (int2.sign != null) int2.sign = int2.sign.higher_positions;

        // Recursively adds each pair of nodes
        return add(int1, int2, sum, tempNode, int1sign, int2sign, overflow, counter + 1);
    }

    // (Dmitri's Implementation) Performs the subtraction operation between two BigIntegers
    public static MyBigInteger subtract(MyBigInteger int1, MyBigInteger int2){
        return subtract(new MyBigInteger(int1), new MyBigInteger(int2), new MyBigInteger(), new IntegerNode(0), int1.sign.digits, int2.sign.digits, 0, 0);
    }
    private static MyBigInteger subtract(MyBigInteger int1, MyBigInteger int2, MyBigInteger difference, IntegerNode tempNode, int int1sign, int int2sign, int underflow, int counter) {
        if (int1sign == 0 && int2sign == -1) { // Ex: 100 - (-10) = 100 + 10
            return add(int1, int2, new MyBigInteger(), new IntegerNode(0), int1.sign.digits, 0, 0, 0);

        } else if (int1sign == -1 && int2sign == 0) { // Ex: -100 - 10 = -(100 + 10)
            return add(int1, int2, new MyBigInteger(), new IntegerNode(0), int1.sign.digits, -1, 0, 0);

        } else if ((int1sign == -1 && int2sign == -1)) { // Ex: -10 - (-100) = 100 - 10
            if (int1.lessThanOrEqual(int2)) { // swap order, subtract
                difference = subtract(int2, int1, new MyBigInteger(), new IntegerNode(0), 0, 0, 0, 0);
            } else {
                difference = subtract(int1, int2, new MyBigInteger(), new IntegerNode(0), 0, 0, 0, 0);
                difference.sign.digits = -1; // convert positive difference to negative
            }
            return difference;

        } else if (int1sign == 0 && int2sign == 0) {
            if (int1.lessThanOrEqual(int2)) {  // Ex: 10 - 100 = -(100 - 10)
                difference = subtract(int2, int1); // swap order to larger num minus smaller num, then subtract
                difference.sign.digits = -1;
                return difference;
            }
        }

        // Main subtraction line: num (larger) - num (smaller) = difference (positive)
        int nextUnderflow = 0;
        if (counter != 0) {
            // Converts current node's digits into string for condition checks (below)
            StringBuilder int1digits = new StringBuilder(String.valueOf(int1.sign != null ? int1.sign.digits : 0));
            while (int1digits.length() != 4) int1digits.insert(0, "0"); // adds extra zeros

            StringBuilder int2digits = new StringBuilder(String.valueOf(int2.sign != null ? int2.sign.digits : 0));
            while (int2digits.length() != 4) int2digits.insert(0, "0"); // Ex: 43 -> 0043

            // Calculates the underflow for the next node based on digit condition checks
            if (int1.sign != null) {
                if (int1digits.toString().equals("0000") && !int2digits.toString().equals("0000")) {
                    int1.sign.digits = 10000;
                    nextUnderflow = 1;
                } else if (Integer.parseInt(int1digits.toString()) < Integer.parseInt(int2digits.toString()) && int1.sign.higher_positions != null) {
                    int1.sign.digits = Integer.parseInt(("1" + int1digits.charAt(0)) + (int1digits.substring(1)));
                    nextUnderflow = 1;
                } else if (underflow != 0 && int1digits.toString().equals("0000")) {
                    int1.sign.digits = 9999;
                    underflow = 0;
                    nextUnderflow = 1;
                }
            }

            // Subtract node's digits from each other accounting for underflow
            int nodeDifference = (int1.sign != null ? int1.sign.digits : 0) - (int2.sign != null ? int2.sign.digits : 0) - underflow;
            if (int1.sign != null)
                int1.sign.digits = Integer.parseInt(int1digits.toString()); // change node's digits back to original
            if (int2.sign != null) int2.sign.digits = Integer.parseInt(int2digits.toString());

            // Return condition: if the end of both nums has been reached and no underflows are represent
            if (nodeDifference == 0 && (int1.sign == null && int2.sign == null)) return difference;

            // Assign a new node with digits = nodeDifference to sum.next
            IntegerNode newNode = new IntegerNode(nodeDifference);
            if (counter == 1) difference.sign.higher_positions = newNode;
            else tempNode.higher_positions = newNode;
            difference.numOfNodes++; // update num of nodes
            tempNode = newNode; // update tempNode
        }

        // Move current node forward if integer's current node not null
        if (int1.sign != null) int1.sign = int1.sign.higher_positions;
        if (int2.sign != null) int2.sign = int2.sign.higher_positions;

        // Break condition if both integers don't have higher positions and there is no over/underflow
        if ((int1.sign == null && int2.sign == null)) return difference;

        // Recursively adds each pair of nodes
        return subtract(int1, int2, difference, tempNode, int1sign, int2sign, nextUnderflow, counter + 1);
    }

    // Outputs a string representation of MyBigInteger
    @Override
    public String toString(){
        StringBuilder output = new StringBuilder();
        IntegerNode head = sign.higher_positions; // temp node
        while (head != null){ // iterates through list

            // Calculates any leading zeros
            StringBuilder zeros = new StringBuilder();
            int digitLength = String.valueOf(head.digits).length();
            if (head.higher_positions != null && digitLength < 4) zeros.append("0".repeat(4 - digitLength));

            // Add node's digits (along with leading zeros) to output
            output.insert(0, zeros.toString() + Math.abs(head.digits));

            // Update current temp node
            if (head.higher_positions != null) head = head.higher_positions;
            else break;
        }

        // Removes unnecessary leading zeros
        if (output.charAt(0) == '0') {
            for (int i = 0; i < output.length(); i++) {
                if (output.charAt(i) != '0') {
                    output = new StringBuilder(output.substring(i));
                    break;
                }
            }
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

    public boolean lessThanOrEqual(MyBigInteger num){

        // if the length of 'this' is less than num, than false
        if (numOfNodes < num.numOfNodes) return true;
        else if (numOfNodes > num.numOfNodes) return false;
        else {

            // Temp nodes for iteration
            IntegerNode head1 = sign;
            IntegerNode head2 = num.sign;

            Stack<IntegerNode> nodeStack1 = new Stack<>();
            Stack<IntegerNode> nodeStack2 = new Stack<>();

            // Iterate through integer's nodes adding them to stacks
            while (head1 != null && head2 != null) {
                nodeStack1.push(head1); // add to stack
                nodeStack2.push(head2);
                head1 = head1.higher_positions; // Move temp pointers forward
                head2 = head2.higher_positions;
            }

            // Iterate through stacks comparing nodes digits, returns if > or
            while(!nodeStack1.isEmpty()){
                int digits1 = nodeStack1.pop().digits; int digits2 = nodeStack2.pop().digits;
                if (digits1 > digits2) return false;
                else if (digits1 < digits2) return true;
            }
        }
        return false;
    }
}
