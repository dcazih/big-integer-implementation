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
    // Assert: sign is never null
    IntegerNode sign;
    int numOfNodes = 1;

    // === Constructors ===
    // String Constructor (Main Constructor)
    public MyBigInteger(String num){
        if(num.length() == 0) {
            sign = new IntegerNode(0);
            return;
        }
        // Check the sign
        int i = 0;
        char c = num.charAt(0);
        if(c == '-'){
            ++i;
            sign = new IntegerNode(-1);
        }
        else if(c == '+') {
            ++i;
            sign = new IntegerNode(1);
        }
        else if(Character.isDigit(c)) {
            sign = new IntegerNode(1);
        }
        else {
            throw new IllegalArgumentException("Invalid input");
        }
        
        // First chunk may be less than 4 digits
        int todo = (num.length() - i) % 4;
        if(todo == 0) todo = 4;
        
        // Chunk the input into 4-digit numbers
        while(i < num.length()) {
            IntegerNode n = new IntegerNode(
                Integer.parseInt(num.substring(i, i + todo))
            );
            i += todo;
            todo = 4;
            // Reading big endian into little endian form, so we have to
            //  insert the new node at the beginning of the list
            IntegerNode next = sign.higher_positions;
            sign.higher_positions = n;
            n.higher_positions = next;
        }
    }
    // Copy Constructor
    public MyBigInteger(MyBigInteger other) {
        IntegerNode cur_other = other.sign.higher_positions;
        IntegerNode cur_self = new IntegerNode(other.sign.digits);
        
        sign = cur_self;
        numOfNodes = other.numOfNodes;
        
        while(cur_other != null) {
            cur_self.higher_positions = new IntegerNode(cur_other.digits);
            
            cur_other = cur_other.higher_positions;
            cur_self = cur_self.higher_positions;
        }
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
    public static MyBigInteger add(MyBigInteger int1, MyBigInteger int2) {
        int int1sign = int1.sign.digits;
        int int2sign = int2.sign.digits;
        
        // If integers are negative, subtract them
        if (int1sign == -1 && int2sign == 0) {
            int1.sign.digits = 0;
            MyBigInteger intSum = subtract(int2, int1);
            int1.sign.digits = -1;
            return intSum;
        }
        if (int1sign == 0 && int2sign == -1) {
            int2.sign.digits = 0;
            MyBigInteger intSum = subtract(int1, int2);
            int2.sign.digits = -1;
            return intSum;
        }
        
        // If integers both negative change sign of sum to negative
        MyBigInteger intSum = new MyBigInteger();
        if (int1sign == -1 && int2sign == -1) {
            intSum.sign.digits = -1;
        }
        IntegerNode nodeSum = intSum.sign;
        IntegerNode node1 = int1.sign.higher_positions;
        IntegerNode node2 = int2.sign.higher_positions;
        int carry = 0;
        
        // Sum each digit group one by one until we run out
        while(node1 != null || node2 != null) {
            int digits1, digits2;
            if (node1 == null) {
                digits1 = 0;
            }
            else {
                digits1 = node1.digits;
                node1 = node1.higher_positions;
            }
            
            if (node2 == null) {
                digits2 = 0;
            }
            else {
                digits2 = node2.digits;
                node2 = node2.higher_positions;
            }
            
            int sum = digits1 + digits2 + carry;
            // Test for carry
            if (sum >= 10000) {
                sum -= 10000;
                carry = 1;
            }
            else {
                carry = 0;
            }
            
            nodeSum.higher_positions = new IntegerNode(sum);
            nodeSum = nodeSum.higher_positions;
            ++intSum.numOfNodes;
        }
        
        // If there is a carry left over, add it to the sum
        if (carry == 1) {
            nodeSum.higher_positions = new IntegerNode(1);
            ++intSum.numOfNodes;
        }
        
        return intSum;
    }
    
    public static MyBigInteger subtract(MyBigInteger int1, MyBigInteger int2) {
        int int1sign = int1.sign.digits;
        int int2sign = int2.sign.digits;
        
        // -a - b = -(a + b)
        if (int1sign == -1 && int2sign == 0) {
            // Add will take care of the sign of the sum
            int2.sign.digits = -1;
            MyBigInteger intDif = add(int2, int1);
            int2.sign.digits = 0;
            return intDif;
        }
        // a - (-b) = a + b
        if (int1sign == 0 && int2sign == -1) {
            int2.sign.digits = 0;
            MyBigInteger intDif = add(int1, int2);
            int2.sign.digits = -1;
            return intDif;
        }
        
        MyBigInteger intDif = new MyBigInteger();
        // If integers both negative change sign of sum to negative
        if (int1sign == -1 && int2sign == -1) {
            intDif.sign.digits = -1;
        }
        
        IntegerNode nodeDif = intDif.sign;
        IntegerNode node1 = int1.sign.higher_positions;
        IntegerNode node2 = int2.sign.higher_positions;
        int borrow = 0;
        
        // Subtract each digit group one by one until we run out
        while(node1 != null || node2 != null) {
            int digits1, digits2;
            if (node1 == null) {
                digits1 = 0;
            }
            else {
                digits1 = node1.digits;
                node1 = node1.higher_positions;
            }
            
            if(node2 == null) {
                digits2 = 0;
            }
            else {
                digits2 = node2.digits;
                node2 = node2.higher_positions;
            }
            
            int dif = digits1 - digits2 - borrow;
            // Test for borrow
            if (dif < 0) {
                dif += 10000;
                borrow = 1;
            }
            else {
                borrow = 0;
            }
            
            nodeDif.higher_positions = new IntegerNode(dif);
            nodeDif = nodeDif.higher_positions;
            ++intDif.numOfNodes;
        }
        
        // If there is a borrow left over, subtract it from the sum
        if (borrow == 1) {
            nodeDif.higher_positions = new IntegerNode(1);
            ++intDif.numOfNodes;
        }
        
        return intDif;
    }
    
    // Raw output for testing
    public String rawString() {
        StringBuilder output = new StringBuilder();
        IntegerNode cur = sign.higher_positions;
        while (cur != null) {
            output.append(String.format("%04d,", cur.digits));
            cur = cur.higher_positions;
        }
        return (sign.digits == -1 ? "-" : "" ) + output;
    }

    // Outputs a string representation of MyBigInteger
    @Override
    public String toString(){
        StringBuilder output = new StringBuilder();
        IntegerNode cur = sign.higher_positions;
        while (cur != null){
            // Only 0-pad trailing groups
            if(cur.higher_positions != null) {
                output.insert(0, String.format("%04d", cur.digits));
            }
            else {
                output.insert(0, Integer.toString(cur.digits));
            }
            cur = cur.higher_positions;
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
