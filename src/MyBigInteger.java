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

        // Parse num by 4 chars, convert to int, add node
        if (num.charAt(0) == '-'){}
        else{
            for(int i = 0; i < ( (num.length()%4==0) ? (num.length()/4) : ((num.length()/4) +1) ); i+=4){

            }
        }
    }

    public static MyBigInteger add(MyBigInteger int1, MyBigInteger int2){
        return null;
    }

}
