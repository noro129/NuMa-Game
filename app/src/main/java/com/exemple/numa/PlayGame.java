package com.exemple.numa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class PlayGame extends AppCompatActivity implements View.OnClickListener {

    NuMa_Matrix game;

    TextView num;

    int numWidthHeight,numTextSizeInSp;

    int levelChosen;
    LinearLayout columnsResults,rowsResults,matrixToFill,columnsResultsCurrent,rowsResultsCurrent;

    Button back,solve,oneLine,reset;
    boolean oneLineUsed,congrats;
    int i_row,i_column;

    ArrayList<Integer> columns_results,rows_results;
    ArrayList<ArrayList<Integer>> matrix,solution_matrix,removed_numbers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_play_game);

        oneLineUsed=false;
        congrats=true;
        i_column=0;


        levelChosen = stringToInt(this.getIntent().getStringExtra("levelChosen"));

        if (levelChosen==NuMa_Matrix.BEGINNER){
            numWidthHeight=45;
            numTextSizeInSp=28;
        } else if (levelChosen==NuMa_Matrix.EASY){
            numWidthHeight=40;
            numTextSizeInSp=22;
        } else if (levelChosen==NuMa_Matrix.MEDIUM){
            numWidthHeight=35;
            numTextSizeInSp=18;
        } else if (levelChosen==NuMa_Matrix.HARD) {
            numWidthHeight=30;
            numTextSizeInSp=16;
        }

        removed_numbers = new ArrayList<>();
        for(int i=0 ; i<levelChosen ; i++){
            ArrayList<Integer> _row = new ArrayList<>();
            for(int j=0 ; j<levelChosen ; j++){
                _row.add(0);
            }
            removed_numbers.add(_row);
        }

        game = new NuMa_Matrix();
        game.FillMatrix(levelChosen);
        columns_results = game.getColumns_results();
        rows_results = game.getRows_results();
        matrix = game.getMatrix();
        solution_matrix = game.getSolution_matrix();


        columnsResults = findViewById(R.id.columnsResults);
        rowsResults = findViewById(R.id.rowsResults);
        matrixToFill = findViewById(R.id.matrixToFill);
        columnsResultsCurrent = findViewById(R.id.columnsResultsCurrent);
        rowsResultsCurrent = findViewById(R.id.rowsResultsCurrent);
        back = findViewById(R.id.back);
        solve = findViewById(R.id.solve);
        oneLine = findViewById(R.id.oneLine);
        reset = findViewById(R.id.reset);

        back.setOnClickListener(this);
        solve.setOnClickListener(this);
        oneLine.setOnClickListener(this);
        reset.setOnClickListener(this);



        for(Integer i : columns_results){
            num = new TextView(this);
            textViewModifsOnNum(num,String.valueOf(i),2);
            columnsResults.addView(num);
        }

        for(Integer i : rows_results){
            num = new TextView(this);
            textViewModifsOnNum(num,String.valueOf(i),2);
            rowsResults.addView(num);
        }

        for(int i=0 ; i<levelChosen ; i++){
            TextView row_sum = new TextView(this);
            int sum = 0;
            ArrayList<Integer> row = matrix.get(i);
            int j=0;
            LinearLayout aRow = new LinearLayout(this);
            aRow.setOrientation(LinearLayout.HORIZONTAL);
            for(Integer in : row){
                sum+=in;
                num = new TextView(this);
                textViewModifsOnNum(num,String.valueOf(in),1);
                setNumClickFunction(this,num,i,j);
                aRow.addView(num);
                j++;
            }
            textViewModifsOnNum(row_sum,""+sum,2);
            rowsResultsCurrent.addView(row_sum);
            matrixToFill.addView(aRow);
        }

        for(int j =0 ; j<levelChosen ; j++){
            TextView column_sum = new TextView(this);
            int sum = 0;
            for (ArrayList<Integer> row : matrix){
                sum+=row.get(j);
            }
            textViewModifsOnNum(column_sum, ""+sum,2);
            columnsResultsCurrent.addView(column_sum);
        }

        for(int i=0 ; i<levelChosen ; i++){
            if(checkForRow(i)){
                trueForRow(i);
                if(checkGameFinished()){
                    gameFinished();
                }
            }else{
                wrongForRow(i);
            }
        }
        for(int j=0 ; j<levelChosen ; j++){
            if(checkForColumn(j)){
                trueForColumn(j);
                if(checkGameFinished()){
                    gameFinished();
                }
            }else{
                wrongForColumn(j);
            }
        }

    }

    public void textViewModifsOnNum(TextView num,String text,int color_option){
        num.setText(text);
        num.setId(View.generateViewId());
        num.setTextSize(TypedValue.COMPLEX_UNIT_SP, numTextSizeInSp);
        num.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        num.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(dpToPx(numWidthHeight), dpToPx(numWidthHeight));
        num.setLayoutParams(layoutParams);
        if(color_option==1){
            num.setTextColor(ContextCompat.getColor(this,R.color.foreground1));
        }else{
            num.setTextColor(ContextCompat.getColor(this,R.color.foreground2));
        }
        num.setBackgroundResource(R.drawable.single_node);
    }

    public void setNumClickFunction(Context context,TextView numb,int i,int j){
        numb.setClickable(true);
        numb.setSoundEffectsEnabled(false);
        numb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView rowT = (TextView)rowsResultsCurrent.getChildAt(i);
                TextView columnT = (TextView)columnsResultsCurrent.getChildAt(j);
                if(removed_numbers.get(i).get(j)==0){
                    numb.setTextColor(ContextCompat.getColor(context,R.color.foreground3));
                    rowT.setText((stringToInt(rowT.getText().toString())-matrix.get(i).get(j))+"");
                    columnT.setText((stringToInt(columnT.getText().toString())-matrix.get(i).get(j))+"");
                    removed_numbers.get(i).set(j,1);
                }else{
                    rowT.setText((stringToInt(rowT.getText().toString())+matrix.get(i).get(j))+"");
                    columnT.setText((stringToInt(columnT.getText().toString())+matrix.get(i).get(j))+"");
                    numb.setTextColor(ContextCompat.getColor(context,R.color.foreground1));
                    removed_numbers.get(i).set(j,0);
                }
                if(checkForRow(i)){
                    trueForRow(i);
                    if(checkGameFinished()){
                        gameFinished();
                    }
                }else{
                    wrongForRow(i);
                }
                if(checkForColumn(j)){
                    trueForColumn(j);
                    if(checkGameFinished()){
                        gameFinished();
                    }
                }else{
                    wrongForColumn(j);
                }
            }
        });
    }

    public boolean checkForRow(int row){
        TextView txt = (TextView) rowsResultsCurrent.getChildAt(row);
        TextView eqlText = (TextView) rowsResults.getChildAt(row);
        if(txt.getText().toString().equals(eqlText.getText().toString())){
            return true;
        }
        return false;
    }
    public void trueForRow(int row){
        LinearLayout ll = (LinearLayout) matrixToFill.getChildAt(row);
        for(int i = 0 ; i<levelChosen ; i++){
            ((TextView)ll.getChildAt(i)).setBackgroundResource(R.drawable.single_node2);
            if(removed_numbers.get(row).get(i)==1){
                ((TextView)ll.getChildAt(i)).setTextColor(ContextCompat.getColor(this,R.color.foreground3));
            }else{
                ((TextView)ll.getChildAt(i)).setTextColor(ContextCompat.getColor(this,R.color.foreground4));
            }
        }
    }
    public void wrongForRow(int row){
        LinearLayout ll = (LinearLayout) matrixToFill.getChildAt(row);
        for(int i = 0 ; i<levelChosen ; i++){
            if(checkForColumn(i)){
               continue;
            }
            ((TextView)ll.getChildAt(i)).setBackgroundResource(R.drawable.single_node);
            if(removed_numbers.get(row).get(i)==1){
                ((TextView)ll.getChildAt(i)).setTextColor(ContextCompat.getColor(this,R.color.foreground3));
            }else{
                ((TextView)ll.getChildAt(i)).setTextColor(ContextCompat.getColor(this,R.color.foreground1));
            }
        }
    }
    public boolean checkForColumn(int column){
        TextView txt = (TextView) columnsResultsCurrent.getChildAt(column);
        TextView eqlText = (TextView) columnsResults.getChildAt(column);
        if(txt.getText().toString().equals(eqlText.getText().toString())){
            return true;
        }
        return false;
    }
    public void trueForColumn(int column){
        for(int i = 0 ; i<levelChosen ; i++){
            LinearLayout ll = (LinearLayout) matrixToFill.getChildAt(i);
            ((TextView)ll.getChildAt(column)).setBackgroundResource(R.drawable.single_node2);
            if(removed_numbers.get(i).get(column)==1){
                ((TextView)ll.getChildAt(column)).setTextColor(ContextCompat.getColor(this,R.color.foreground3));
            }else{
                ((TextView)ll.getChildAt(column)).setTextColor(ContextCompat.getColor(this,R.color.foreground4));
            }
        }
    }
    public void wrongForColumn(int column){
        for(int i = 0 ; i<levelChosen ; i++){
            LinearLayout ll = (LinearLayout) matrixToFill.getChildAt(i);
            if(checkForRow(i)){
                continue;
            }
            ((TextView)ll.getChildAt(column)).setBackgroundResource(R.drawable.single_node);
            if(removed_numbers.get(i).get(column)==1){
                ((TextView)ll.getChildAt(column)).setTextColor(ContextCompat.getColor(this,R.color.foreground3));
            }else{
                ((TextView)ll.getChildAt(column)).setTextColor(ContextCompat.getColor(this,R.color.foreground1));
            }
        }
    }
    public int dpToPx(int dp) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public int stringToInt(String str){
        int integer = 0;
        for(int i = 0 ; i<str.length() ; i++){
            switch(str.charAt(i)){
                case '0' :
                    integer = integer*10;
                    break;
                case '1' :
                    integer = integer*10 + 1;
                    break;
                case '2' :
                    integer = integer*10 + 2;
                    break;
                case '3' :
                    integer = integer*10 + 3;
                    break;
                case '4' :
                    integer = integer*10 + 4;
                    break;
                case '5' :
                    integer = integer*10 + 5;
                    break;
                case '6' :
                    integer = integer*10 + 6;
                    break;
                case '7' :
                    integer = integer*10 + 7;
                    break;
                case '8' :
                    integer = integer*10 + 8;
                    break;
                case '9' :
                    integer = integer*10 + 9;
                    break;
            }
        }
        return integer;
    }

    public boolean checkGameFinished(){
        for(int i=0 ; i<levelChosen ; i++){
            TextView txt = (TextView) rowsResultsCurrent.getChildAt(i);
            TextView eqlText = (TextView) rowsResults.getChildAt(i);
            if(!(txt.getText().toString().equals(eqlText.getText().toString()))){
                return false;
            }
        }
        for(int i=0 ; i<levelChosen ; i++){
            TextView txt = (TextView) columnsResultsCurrent.getChildAt(i);
            TextView eqlText = (TextView) columnsResults.getChildAt(i);
            if(!(txt.getText().toString().equals(eqlText.getText().toString()))){
                return false;
            }
        }
        return true;
    }

    public void gameFinished(){
        if(congrats){
            finish();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.oneLine:
                if(oneLineUsed){
                    if(i_row==levelChosen){
                        showColumn(i_column);
                    }else{
                        showRow(i_row);
                    }
                    oneLine.setVisibility(View.INVISIBLE);
                    break;
                }
                for(i_row=0 ; i_row<levelChosen ; i_row++){
                    if(!checkForRow(i_row)){
                        showRow(i_row);
                        break;
                    }
                }
                if(i_row==levelChosen){
                    for(i_column=0 ; i_column<levelChosen ; i_column++){
                        if(!checkForColumn(i_column)){
                            showColumn(i_column);
                            break;
                        }
                    }
                }
                oneLineUsed=true;
                oneLine.setVisibility(View.INVISIBLE);
                break;
            case R.id.reset:
                oneLine.setVisibility(View.VISIBLE);
                for(int i=0 ; i<levelChosen ; i++){
                    for(int j=0 ; j<levelChosen ; j++){
                        if(removed_numbers.get(i).get(j)==1){
                            ((LinearLayout)matrixToFill.getChildAt(i)).getChildAt(j).performClick();
                        }
                    }
                }
                break;
            case R.id.solve:
                oneLine.setVisibility(View.INVISIBLE);
                reset.setVisibility(View.INVISIBLE);
                congrats=false;
                for(int i=0 ; i<levelChosen ; i++){
                    for(int j=0 ; j<levelChosen ; j++){
                        if(solution_matrix.get(i).get(j)==removed_numbers.get(i).get(j)){
                            ((LinearLayout)matrixToFill.getChildAt(i)).getChildAt(j).performClick();
                        }
                    }
                }
                break;
        }
    }

    public void showRow(int row){
        for(int j=0 ; j<levelChosen ; j++){
            if(solution_matrix.get(row).get(j)==removed_numbers.get(row).get(j)){
                ((LinearLayout)matrixToFill.getChildAt(row)).getChildAt(j).performClick();
            }
        }
    };
    public void showColumn(int column){
        for(int j=0 ; j<levelChosen ; j++){
            if(solution_matrix.get(j).get(column)==removed_numbers.get(j).get(column)){
                ((LinearLayout)matrixToFill.getChildAt(j)).getChildAt(column).performClick();
            }
        }
    };
}