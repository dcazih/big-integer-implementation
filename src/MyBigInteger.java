public class MyBigInteger {
    class IntegerNode {
        int digits; // 4 digits in the current node
        IntegerNode higher_positions; // digits in the higher

        public IntegerNode(int digits){
            this.digits = digits;
            higher_positions = null;
        }
    }

    IntegerNode sign;

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
        }
    }

    public static MyBigInteger add(MyBigInteger int1, MyBigInteger int2){
        return null;
    }

}
