package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class MainActivity extends AppCompatActivity {
    private TextView workingsTV,resultsTV;
    private static String workings="";
    String formula = "";
    String tempFormula = "";
    static Double result = null;
    private static String nWorkings;
    private Button logButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTextViews();

        logButton=findViewById(R.id.logbutton);
        logButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                lnOnClick();
                return true;
            }
        });
    }

    private void initTextViews()
    {
        workingsTV = (TextView)findViewById(R.id.workingTextView);
        resultsTV = (TextView)findViewById(R.id.resultTextView);
    }

    private void setWorkings(String givenValue)
    {
        if (workings.length()>83){
            return;
        }

        workings = workings + givenValue;
        replaceFunction(workings);
//        nWorkings = workings.replace("Math.PI", "π")
//                .replace("Math.log(", "ln(")
//                .replace("Math.log10(", "log(")
//                .replace("Math.sqrt(", "√(");
//        workingsTV.setText(nWorkings);
    }


    public void equalOnClick(View view)
    {

        ScriptEngine engine = new ScriptEngineManager().getEngineByName("rhino");
        checkForPowerOf();

        if (formula.isEmpty())return;
        try {
            result = (double)engine.eval(formula);

        } catch (ScriptException e)
        {
            Toast.makeText(this, "Invalid Input", Toast.LENGTH_SHORT).show();
            resultsTV.setText("="+"Error");
        }

        if(result != null){
            String formatterResult=String.format("%.8g",result).replace("e+0","e+").replace("e-0","e-");
            resultsTV.setText("="+formatterResult);
        }

    }

    private void checkForPowerOf()
    {
        ArrayList<Integer> indexOfPowers = new ArrayList<>();
        for(int i = 0; i < workings.length(); i++)
        {
            if (workings.charAt(i) == '^')
                indexOfPowers.add(i);
        }

        formula = workings;
        tempFormula = workings;
        for(Integer index: indexOfPowers)
        {
            changeFormula(index);
        }
        formula = tempFormula;
    }

    private void changeFormula(Integer index) {
        String numberLeft = "";
        String numberRight = "";

        // Ensure index + 1 is within bounds
        if (index + 1 < workings.length() && workings.charAt(index + 1) == '(') {
            int closingParenthesisIndex = findClosingParenthesis(index + 1);
            if (closingParenthesisIndex != -1) {
                numberRight = workings.substring(index + 1, closingParenthesisIndex + 1);
            } else {
                // If closing parenthesis is missing, add it automatically
                numberRight = workings.substring(index + 1) + ")"; // Add closing parenthesis at the end
            }
        } else {
            for (int i = index + 1; i < workings.length(); i++) {
                if (isNumeric(workings.charAt(i)))
                    numberRight += workings.charAt(i);
                else
                    break;
            }
        }

        // Ensure index - 1 is within bounds
        if (index - 1 >= 0 && workings.charAt(index - 1) == ')') {
            int openingParenthesisIndex = findOpeningParenthesis(index - 1);
            if (openingParenthesisIndex != -1) {
                numberLeft = workings.substring(openingParenthesisIndex, index);
            } else {
                // If opening parenthesis is missing, add it automatically
                numberLeft = "(" + workings.substring(0, index) + ")"; // Add opening parenthesis at the start
            }
        } else {
            for (int i = index - 1; i >= 0; i--) {
                if (isNumeric(workings.charAt(i)))
                    numberLeft = workings.charAt(i) + numberLeft;
                else
                    break;
            }
        }

        // Replace the "^" operation with Math.pow
        String original = numberLeft + "^" + numberRight;
        String changed = "Math.pow(" + numberLeft + "," + numberRight + ")";
        tempFormula = tempFormula.replace(original, changed);
    }



    private int findClosingParenthesis(int openIndex) {
        int count = 0;
        for (int i = openIndex; i < workings.length(); i++) {
            if (workings.charAt(i) == '(') count++;
            if (workings.charAt(i) == ')') count--;
            if (count == 0) return i;
        }
        return -1; // No matching closing parenthesis found
    }


    private int findOpeningParenthesis(int closeIndex) {
        int count = 0;
        for (int i = closeIndex; i >= 0; i--) {
            if (workings.charAt(i) == ')') count++;
            if (workings.charAt(i) == '(') count--;
            if (count == 0) return i;
        }
        return -1; // No matching opening parenthesis found
    }

    private boolean isNumeric(char c)
    {
        if((c <= '9' && c >= '0') || c == '.')
            return true;

        return false;
    }


    public void clearOnClick(View view)
    {
        workingsTV.setText("");
        workings = "";
        result=null;
        resultsTV.setText("0");
    }


    public void leftBracketsOnClick(View view)
    {
        setWorkings("(");
    }
    public void rightBracketsOnClick(View view){
        setWorkings(")");
    }


    public void backSpaceOnClick(View view){
        if (workings!=null && !workings.isEmpty()){

            ///specialFunctions will be remove at once on backspace clicked
            String[] specialFunctions = {"Math.log10(", "Math.sqrt(", "Math.log(", "Math.PI"};

            for (String func : specialFunctions) {
                if (workings.endsWith(func)) {
                    // Remove the entire function name at once
                    workings = workings.substring(0, workings.length() - func.length());
                    replaceFunction(workings);

//                    nWorkings = workings.replace("Math.PI", "π")
//                            .replace("Math.log(", "ln(")
//                            .replace("Math.log10(", "log(")
//                            .replace("Math.sqrt(", "√(");
//                    workingsTV.setText(nWorkings);

                   /// workingsTV.setText(workings); // Update UI
                    if (workings.isEmpty() | workings.length()==0){
                        result=null;
                        resultsTV.setText("0");
                    }
                    return; // Exit after removing one match
                }
            }

            workings=workings.substring(0,workings.length()-1);
            replaceFunction(workings);
//            nWorkings = workings.replace("Math.PI", "π")
//                    .replace("Math.log(", "ln(")
//                    .replace("Math.log10(", "log(")
//                    .replace("Math.sqrt(", "√(");
//            workingsTV.setText(nWorkings);

            if (workings.isEmpty() | workings.length()==0){
                result=null;
                resultsTV.setText("0");
            }
        }
    }

    public void replaceFunction(String workings){
        nWorkings = workings.replace("Math.PI", "π")
                .replace("Math.log(", "ln(")
                .replace("Math.log10(", "log(")
                .replace("Math.sqrt(", "√(");
        workingsTV.setText(nWorkings);
    }

    public void powerOfOnClick(View view)
    {
        setWorkings("^");
    }


    public void divisionOnClick(View view)
    {
        setWorkings("/");
    }

    public void sevenOnClick(View view)
    {
        setWorkings("7");
    }

    public void eightOnClick(View view)
    {
        setWorkings("8");
    }

    public void nineOnClick(View view)
    {
        setWorkings("9");
    }

    public void timesOnClick(View view)
    {
        setWorkings("*");
    }

    public void fourOnClick(View view)
    {
        setWorkings("4");
    }

    public void fiveOnClick(View view)
    {
        setWorkings("5");
    }

    public void sixOnClick(View view)
    {
        setWorkings("6");
    }

    public void minusOnClick(View view)
    {
        setWorkings("-");
    }

    public void oneOnClick(View view)
    {
        setWorkings("1");
    }

    public void twoOnClick(View view)
    {
        setWorkings("2");
    }

    public void threeOnClick(View view)
    {
        setWorkings("3");
    }

    public void plusOnClick(View view)
    {
        setWorkings("+");
    }

    public void decimalOnClick(View view)
    {
        setWorkings(".");
    }

    public void zeroOnClick(View view)
    {
        setWorkings("0");
    }

    public void rootOnClick(View view) {
        setWorkings("Math.sqrt(");
    }

    public void percentageOnClick(View view) {
        if (workings.length() == 0) return; // Do nothing if empty

            ScriptEngine engine = new ScriptEngineManager().getEngineByName("rhino");
            checkForPowerOf();

            if (formula.isEmpty())return;
            try {
                result = (double)engine.eval(formula);

                if(result != null){
                    result = result / 100.0; // Convert to percentage

                    // Replace the last number with its percentage value
                    workings = String.valueOf(result);

                    workingsTV.setText(workings);

                    String formatterResult=String.format("%.8g",result).replace("e+0","e+").replace("e-0","e-");
                    resultsTV.setText("="+formatterResult);
                }
            } catch (ScriptException e)
            {
                Toast.makeText(this, "Invalid Input", Toast.LENGTH_SHORT).show();
                resultsTV.setText("="+"Error");
            }



    }


    public void logOnClick(View view) {
        setWorkings("Math.log10(");
    }
    public void lnOnClick() {
        setWorkings("Math.log(");
    }
    public void paiOnClick(View view) {
        setWorkings("Math.PI");
    }

}