public class Main {
    public static void gotNeed(String got, String need) {
        System.out.printf("Got : %s\nNeed: %s\n", got, need);
    }
    
    public static boolean sumTest(int testno, String str1, String str2, String strSum) {
        MyBigInteger num1 = new MyBigInteger(str1);
        MyBigInteger num2 = new MyBigInteger(str2);
        MyBigInteger sum = new MyBigInteger(strSum);
        
        System.out.printf("Test %d: ", testno);
        
        if (!num1.toString().equals(str1)) {
            System.out.println("FAIL: num1 not equal to str1");
            gotNeed(num1.toString(), str1);
            return false;
        }
        if (!num2.toString().equals(str2)) {
            System.out.println("FAIL: num2 not equal to str2");
            gotNeed(num2.toString(), str2);
            return false;
        }
        if (!sum.toString().equals(strSum)) {
            System.out.println("FAIL: sum not equal to strSum");
            gotNeed(sum.toString(), strSum);
            return false;
        }
        
        System.out.println("PASS");
        return true;
    }
    public static void main(String[] args){
        // Test 0
        // Test toString
        String num0 = "123456789";
        System.out.printf("'%s' = %s\n", num0, new MyBigInteger(num0));
        
        // Test 1
        // Proves algorithm's capability of taking in much longer numbers then given test cases.
        sumTest(1,
            "987293740836598436593649536495643953296594631596425654321",
            "123456789987293740836598436593649536495643953296594631596425654321987293740836598436593649536495643953296594631596425654321987293740836598436593649536495643953296594631596425654321",
            "12345678998729374083659843659364953649564395329659463159642565432198729374083659843659364953649564395329659463159642565432198729374083659843659364953649564395329659463159642565432297458749673196873187299072991287906593189263192851308642"
        );
        
        // Test 2 (from project description)
        sumTest(2,
            "18364000098463281009282",
            "-9382361766839928276166829",
            "-9363997766741464995157547"
        );

        // Test 3 (from project description)
        sumTest(3,
            "839947462729219484028272",
            "-839947462729219484028000",
            "272"
        );

        // Test 4 (from project description)
        sumTest(4,
            "-25634837829208474747382992822",
            "-6382927634646483929283733883",
            "-32017765463854958676666726705"
        );

        // Test 5
        // Proves algorithm's ability to take in smaller positive numbers
        sumTest(5,
            "593720",
            "75982",
            "669702"
        );

        // Test 6
        // Proves algorithm's ability to take in smaller negative numbers
        sumTest(6,
            "-45002",
            "-654",
            "-45656"
        );

        // Test 7
        // Proves algorithm's ability to take in a smaller positive and negative number
        sumTest(7,
            "75740",
            "-190740",
            "-115000"
        );
    }
}
