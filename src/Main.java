public class Main {

    public static void main(String[] args){

        // Test 1
        // Proves algorithm's capability of taking in much longer numbers then given test cases.
        MyBigInteger num1 = new MyBigInteger("987293740836598436593649536495643953296594631596425654321");
        MyBigInteger num2 = new MyBigInteger("123456789987293740836598436593649536495643953296594631596425654321987293740836598436593649536495643953296594631596425654321987293740836598436593649536495643953296594631596425654321987293740836598436593649536495643953296594631596425654321" );
        
        MyBigInteger sum1 = MyBigInteger.add(num1, num2);

        System.out.printf("""
                %s
                %s +
                --------------
                The sum is %s
                """, num1, num2, sum1);

        // Test 2 (from project description)
        MyBigInteger num3 = new MyBigInteger("18364000098463281009282");
        MyBigInteger num4 = new MyBigInteger("-9382361766839928276166829" );

        MyBigInteger sum2 = MyBigInteger.add(num3, num4);

        System.out.printf("""
                %s
                %s +
                --------------
                The sum is %s
                """, num3, num4, sum2);

        // Test 3 (from project description)
        MyBigInteger num5 = new MyBigInteger("839947462729219484028272");
        MyBigInteger num6 = new MyBigInteger("-839947462729219484028000" );

        MyBigInteger sum3 = MyBigInteger.add(num5, num6);

        System.out.printf("""
                %s
                %s +
                --------------
                The sum is %s
                """, num5, num6, sum3);

        // Test 4 (from project description)
        MyBigInteger num7 = new MyBigInteger("-25634837829208474747382992822");
        MyBigInteger num8 = new MyBigInteger("-6382927634646483929283733883" );

        MyBigInteger sum4 = MyBigInteger.add(num7, num8);

        System.out.printf("""
                %s
                %s +
                --------------
                The sum is %s
                """, num7, num8, sum4);

        // Test 5
        // Proves algorithm's ability to take in smaller positive numbers
        MyBigInteger num9 = new MyBigInteger("593720");
        MyBigInteger num10 = new MyBigInteger("75982" );

        MyBigInteger sum5 = MyBigInteger.add(num9, num10);

        System.out.printf("""
                %s
                %s +
                --------------
                The sum is %s
                """, num9, num10, sum5);

        // Test 6
        // Proves algorithm's ability to take in smaller negative numbers
        MyBigInteger num11 = new MyBigInteger("-45002");
        MyBigInteger num12 = new MyBigInteger("-654" );

        MyBigInteger sum6 = MyBigInteger.add(num11, num12);

        System.out.printf("""
                %s
                %s +
                --------------
                The sum is %s
                """, num11, num12, sum6);

        // Test 7
        // Proves algorithm's ability to take in a smaller positive and negative number
        MyBigInteger num13 = new MyBigInteger("75740");
        MyBigInteger num14 = new MyBigInteger("-190740" );

        MyBigInteger sum7 = MyBigInteger.add(num13, num14);

        System.out.printf("""
                %s
                %s +
                --------------
                The sum is %s
                """, num13, num14, sum7);
    }
}
