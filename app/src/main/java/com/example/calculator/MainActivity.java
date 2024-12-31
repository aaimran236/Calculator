package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Reader;
import java.util.ArrayList;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class MainActivity extends AppCompatActivity {
    private TextView workingsTV,resultsTV;
    private static String workings="";
    String formula = "";
    String tempFormula = "";
    static boolean leftBracket = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTextViews();
    }

    private void initTextViews()
    {
        workingsTV = (TextView)findViewById(R.id.workingTextView);
        resultsTV = (TextView)findViewById(R.id.resultTextView);
    }

    private void setWorkings(String givenValue)
    {
            workings = workings + givenValue;
            workingsTV.setText(workings);

    }


    public void equalOnClick(View view)
    {
        Double result = null;
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("rhino");
        checkForPowerOf();

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

        // Handle parentheses or numeric extraction for numberRight
        if (workings.charAt(index + 1) == '(') {
            int closingParenthesisIndex = findClosingParenthesis(index + 1);
            if (closingParenthesisIndex != -1) {
                numberRight = workings.substring(index + 1, closingParenthesisIndex + 1);
            } else {
                Toast.makeText(this, "Invalid Input: Missing closing parenthesis", Toast.LENGTH_SHORT).show();
                return; // Exit early on invalid input
            }
        } else {
            for (int i = index + 1; i < workings.length(); i++) {
                if (isNumeric(workings.charAt(i)))
                    numberRight += workings.charAt(i);
                else
                    break;
            }
        }

        // Handle parentheses or numeric extraction for numberLeft
        if (workings.charAt(index - 1) == ')') {
            int openingParenthesisIndex = findOpeningParenthesis(index - 1);
            if (openingParenthesisIndex != -1) {
                numberLeft = workings.substring(openingParenthesisIndex, index);
            } else {
                Toast.makeText(this, "Invalid Input: Missing opening parenthesis", Toast.LENGTH_SHORT).show();
                return; // Exit early on invalid input
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
        resultsTV.setText("0");
        leftBracket = true;
    }



    public void bracketsOnClick(View view)
    {
        if(leftBracket)
        {
            setWorkings("(");
            leftBracket = false;
        }
        else
        {
            setWorkings(")");
            leftBracket = true;
        }
    }

    public void backSpaceOnClick(View view){
        if (workings!=null && !workings.isEmpty()){
            if (workings.length()>0){
                if (workings.charAt(workings.length()-1)=='(')leftBracket=true;
                if (workings.charAt(workings.length()-1)==')')leftBracket=false;
            }

            workings=workings.substring(0,workings.length()-1);
            workingsTV.setText(workings);


        }
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


}