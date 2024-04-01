public class Main {

    public static void main(String[] args){
        MyBigInteger num1 = new MyBigInteger("987654321");
        MyBigInteger num2 = new MyBigInteger("123456789" );

        MyBigInteger sum = MyBigInteger.add(num1, num2);

        System.out.printf("%s\n%s +\n--------------\n%s\n", num1, num2, sum);
    }

}
