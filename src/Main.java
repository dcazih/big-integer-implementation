public class Main {

    public static void main(String[] args){


        MyBigInteger num1 = new MyBigInteger("987654321");
        MyBigInteger num2 = new MyBigInteger("123456789" );

        MyBigInteger sum = MyBigInteger.add(num1, num2);

        System.out.printf("%s\n%s +\n--------------\n%s\n", num1, num2, sum);


        //System.out.println(MyBigInteger.add(sum, num2));
        //System.out.println(num1.sign.higher_positions.digits);

        //System.out.printf("Is %s greater than %s: %s ", num1, num2, num1.greaterThanOrEqual(num2));
        //System.out.println(MyBigInteger.add(num1, num2));
    }

}
