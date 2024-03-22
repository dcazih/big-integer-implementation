public class MyBigInteger {

    IntegerNode sign;

    public MyBigInteger(String num){

        // Create first node
        sign = new IntegerNode(0);
        
    }

    public static MyBigInteger add(MyBigInteger int1, MyBigInteger int2){
        return null;
    }

}
class IntegerNode {

    int digits; // 4 digits in the current node
    IntegerNode higher_positions; // digits in the higher

    public IntegerNode(int digits){
        this.digits = digits;
        higher_positions = null;
    }

}