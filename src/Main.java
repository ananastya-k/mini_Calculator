package src;
import java.util.*;

public class Main {

    public static Map<String,Integer> romanNum=new LinkedHashMap<>();

    static {
        romanNum.put("M",1000);
        romanNum.put("CM",900);
        romanNum.put("D",500);
        romanNum.put("CD",400);
        romanNum.put("C",100);
        romanNum.put("XC",90);
        romanNum.put("L",50);
        romanNum.put("XL",40);
        romanNum.put("X",10);
        romanNum.put("IX",9);
        romanNum.put("V",5);
        romanNum.put("IV",4);
        romanNum.put("I",1);
    }

    public static void main(String[] args)throws MyException{

        try {
            String expression = readALine();
            String[] tokens = parsingOnTokens(expression);
            char sign = expression.charAt(tokens[0].length());
            System.out.println(calculate(tokens, sign));
        }
        catch (MyException e){
            System.out.println("Incorrect input.");
        }catch (ArithmeticException e){
            System.out.println("Divide by zero? ");
        }
    }

    public static String readALine(){

        Scanner sc=new Scanner(System.in);
        return sc.nextLine();

    }

    public static String[] parsingOnTokens(String expr) throws MyException{

        expr=expr.replaceAll("\\s","");
        String[]tokens=expr.split("[+-/*]");

        if(tokens.length!=2) throw new MyException("Incorrect input");

        return tokens;
    }

    public static String calculate(String[] tokens,  char sign) throws MyException{


        if ( isArabic(tokens) ) {
            return calc(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]), sign)+"";
        } else if ( isRoman(tokens) ) {
            int result=calc(toArabic(tokens[0]), toArabic(tokens[1]),sign);
            return result>0 ? toRoman(result) : "There are no negative numbers in the romain numeric system (in the arabic system res = "+result+")";
        } else {
            throw new MyException("Incorrect input (different num systems)");
        }
    }
    public static int calc(int num1, int num2, char sign){

            return switch (sign) {
                case '+' -> num1 + num2;
                case '-' -> num1 - num2;
                case '*' -> num1 * num2;
                case '/' -> num1 / num2;
                default -> throw new IllegalStateException("Unexpected value: " + sign);
            };

    }

    public static boolean isArabic(String[] list){
        for(String s: list){
            if(!new Scanner(s).hasNextInt()) return false;
        }
        return true;
    }
    public static boolean isRoman(String[] list){
        for(String s: list){
            if(!romanNum.containsKey(s.charAt(0)+"")) return false;
        }
        return true;
    }
    public static int toArabic(String roman){
        int arabic=0;

        for(int i=0;i<roman.length();i++){

            if(i==(roman.length()-1) || romanNum.get(roman.charAt(i)+"")>=romanNum.get(roman.charAt(i+1)+""))
                arabic+=romanNum.get(roman.charAt(i)+"");
            else arabic-=romanNum.get(roman.charAt(i)+"");
        }
        return arabic;
    }
    public static String toRoman(int arabic){

        StringBuilder roman= new StringBuilder();

        for(Map.Entry<String,Integer> entry: romanNum.entrySet()){

            int cnt=arabic/ entry.getValue();
            arabic-=cnt*entry.getValue();
            roman.append(entry.getKey().repeat(cnt));
        }
        return roman.toString();
    }

}