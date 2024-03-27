public class MyBigInteger {
    
    private static class IntegerNode {

        public int digits; // 4 digits in the current node
        public IntegerNode higher_positions; // digits in the higher

        public IntegerNode(int digits){
            this.digits = digits;
            higher_positions = null;
        }
        
        public IntegerNode(IntegerNode other) {
            digits = other.digits;
        }
    }

    public IntegerNode sign;

    public MyBigInteger(int num){
        // Check the sign
        if(num == 0) {
            sign = new IntegerNode(0);
        }
        else if(num > 0) {
            sign = new IntegerNode(1);
        }
        else {
            sign = new IntegerNode(-1);
            num = -num;
        }
        
        // Chunk the input into 4-digit numbers
        auto cur = sign;
        while(num > 0) {
            cur.higher_positions = new IntegerNode(num % 10000);
            cur = cur.higher_positions;
            num /= 10000;
        }
    }
    
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
        
        // Chunk the input into 4-digit numbers
        IntegerNode cur = sign;
        for(; i + 4 < num.length(); i += 4) {
            cur.higher_positions = new IntegerNode(
                Integer.getInteger(num.substring(i, i + 4))
            );
            cur = cur.higher_positions;
        }
        // Add the remaining digits
        if(i < num.length()) {
            cur.higher_positions = new IntegerNode(
                Integer.getInteger(num.substring(i))
            );
        }
    }
    
    public MyBigInteger(MyBigInteger other) {
        IntegerNode cur_other = other.sign;
        IntegerNode cur_self = new IntegerNode(cur_other);
        
        while(cur_other != null) {
            cur_self.higher_positions = new IntegerNode(cur_other);
            
            cur_other = cur_other.higher_positions;
            cur_self = cur_self.higher_positions;
        }
    }

    // Call using MyBigInteger.add(lhs, rhs)
    public static MyBigInteger add(MyBigInteger lhs, MyBigInteger rhs) {
        MyBigInteger result = new MyBigInteger(0);
        
        if(lhs.sign.digits != rhs.sign.digits) {
            /* TODO: mixed sign */
            // Probably use: a - b = -(b - a)
            // So we can use a borrowed subtraction function assuming the lhs is greater
            // This would also require a comparison function and a negation function
            // Maybe also compartmentalize the addition and subtraction functions
        }
        
        // Copy the sign
        result.sign.digits = lhs.sign.digits;
        
        IntegerNode lcur = lhs.sign.higher_positions;
        IntegerNode rcur = rhs.sign.higher_positions;
        IntegerNode cur = result.sign.higher_positions;
        
        // Add with carry until we run out of digits
        int carry = 0;
        while(lcur != null || rcur != null) {
            int sum = carry;
            if(lcur != null) {
                sum += cur1.digits;
                lcur = cur1.higher_positions;
            }
            if(rcur != null) {
                sum += rcur.digits;
                rcur = rcur.higher_positions;
            }
            
            carry = sum / 10000;
            sum %= 10000;
            
            cur.higher_positions = new IntegerNode(sum);
            cur = cur.higher_positions;
        }
        
        // Add the remaining carry
        if(carry > 0) {
            cur.higher_positions = new IntegerNode(carry);
        }
        
        return result;
    }

}