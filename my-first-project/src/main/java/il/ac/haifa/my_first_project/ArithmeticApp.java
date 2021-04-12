package il.ac.haifa.my_first_project;
import java.util.*;
import java.text.DecimalFormat;

public class ArithmeticApp {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter expression:");
        String input = sc.nextLine();
        input = DeleteSpaces(input);
        double value = EvaluateExpression(input);
        System.out.print("The value of expression " + input + " is: ");
        System.out.format("%.5f", value);
        System.out.println();
    }


    private static ArrayList<String> SplitExpression(String expression) {
        ArrayList<String> exp = new ArrayList<>();

        boolean hasNegativeOp = false;
        for (int i = 0; i < expression.length(); i++) {
            char current = expression.charAt(i);
            char next = 'w';
            if (i + 1 < expression.length()) {
                next = expression.charAt(i + 1);
            }

            if (current == '(' || current == ')') {
                exp.add(Character.toString(current));
            }
            else if (i > 0 /* operations may only appear after first index*/ &&
                    (current == '*' || current == '/' || current == '+' || current == '-')) {
                exp.add(Character.toString(current));
                if (next == '-') {
                    ++i;
                    hasNegativeOp = true;
                }
            }
            else {
                int startOfNumber = i;
                int endOfNumber;
                for (endOfNumber = i; endOfNumber < expression.length(); ++endOfNumber) {
                    char character = expression.charAt(endOfNumber);
                    if (endOfNumber > 0 && /* operations may only appear after first index*/
                            (character == '/' || character == '*' || character == '-' || character == '+' || character == ')' || character == '(')) {
                        break;
                    }
                }

                String number = expression.substring(startOfNumber, endOfNumber);
                if (hasNegativeOp) {
                    hasNegativeOp = false;
                    number = "-" + number;
                }
                exp.add(number);
                i = endOfNumber - 1; // so that the larger for loop can continue to the next number
            }

        }

        // interpret the (-10.0+3) correctly, as the code above doesn't consider the possibility of (-
        int i = 0;
        while (i + 1 < exp.size()) {
            String current = exp.get(i);
            String next = exp.get(i + 1);

            if (current.equals("(") && next.equals("-")) { // if expression looks like "(-52.0 ...."
                String temp = exp.get(i + 2); // saves the number (lets say 52.0)
                exp.remove(i + 2); // removes the number
                exp.remove(i + 1); // removes the unary "-"
                exp.add(i + 1, "-" + temp); // creates -52.0
            }
            i++;
        }
        return exp;
    }


    private static String DeleteSpaces(String exp) {        // function that gets expression and deletes all the spaces in it
        String newExp = "";
        String temp;
        for (int i = 0; i < exp.length(); i++) {
            temp = String.valueOf(exp.charAt(i));        // getting the char and casting it to string
            if (!temp.equals(" ")) {
                newExp = newExp.concat(temp);            // concatinating strings
            }
        }
        return newExp;
    }


    private static double EvaluateExpression(String expression) {
        ArrayList<String> splitExpression = SplitExpression(expression);
        return EvaluateExpressionHelper(splitExpression);
    }


    private static double EvaluateExpressionHelper(ArrayList<String> exp) {
        if (!ExpressionIsSurroundedWithBrackets(exp)) {
            exp.add(0, "(");
            exp.add(exp.size(), ")");
        }
        String str;
        int i;
        int start = -1;
        int end = -1;
        double expVal;
        int counter;
        ArrayList<String> expression = new ArrayList<>();
        while(exp.size() > 1){
            for(i = 0; i < exp.size(); i++){
                str = exp.get(i);
                if(str.equals("(")){
                    start = i;
                }
                if(str.equals(")")){
                    end = i;
                    break;
                }
            }
            for(i = start + 1; i < end; i++){
                str = exp.get(i);
                expression.add(str);
            }
            expVal = GetExpressionValue(expression);        // get expression value
            counter = end - start - 1;
            while(counter != 0){
                exp.remove(start + 1);
                counter--;
            }
            exp.add(start + 1, Double.toString(expVal));
            exp.remove(start + 2);      // remove brackets
            exp.remove(start);
            while(expression.size() != 0){
                expression.remove(0);
            }

        }
        return Double.parseDouble(exp.get(0));
    }


    private static boolean ExpressionIsSurroundedWithBrackets(ArrayList<String> exp) {
        if (!exp.get(0).equals("(")) return false;

        int counter = 1;
        int i;
        for(i = 1; counter > 0; ++i) {
            if (exp.get(i).equals("(")) ++counter;
            if (exp.get(i).equals(")")) --counter;
        }

        return i == exp.size();
    }

    private static void PerformOperationAndUpdateExpression(ArrayList<String> exp, int operationIndex)
    {
        String operation = exp.get(operationIndex);
        double left = Double.parseDouble(exp.get(operationIndex - 1));
        double right = Double.parseDouble(exp.get(operationIndex + 1));
        double result = EvaluateOperation(left, operation, right);
        exp.set(operationIndex, String.valueOf(result));
        exp.remove(operationIndex + 1);
        exp.remove(operationIndex - 1);
    }

    private static double EvaluateOperation(double left, String operation, double right) {
        if (operation.equals("*")) return left * right;
        if (operation.equals("/")) return left / right;
        if (operation.equals("+")) return left + right;
        return left - right;
    }

    private static double GetExpressionValue(ArrayList<String> exp){      // function that gets expression without brackets and returns its value

        // give preference to * and /, thus they have their own while loop
        int counter = -1;
        while(counter != 0) {
            counter = 0;
            for (int i = 0; i < exp.size(); i++) {
                String current = exp.get(i);
                if (current.equals("*") || current.equals("/")) {
                    PerformOperationAndUpdateExpression(exp, i);
                    counter++;
                    break;
                }
            }
        }

        // order between + and - doesn't matter, but secondary to * and /, thus they have their own while loop
        counter = -1;
        while(counter != 0) {
            counter = 0;
            for (int i = 0; i < exp.size(); i++) {
                String current = exp.get(i);
                if (current.equals("+") || current.equals("-")) {
                    PerformOperationAndUpdateExpression(exp, i);
                    counter++;
                    break;
                }
            }
        }
        return Double.parseDouble(exp.get(0));
    }
}