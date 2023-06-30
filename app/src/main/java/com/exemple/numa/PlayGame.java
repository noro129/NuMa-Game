package com.exemple.numa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class PlayGame extends AppCompatActivity {

    TextView num;

    int numWidthHeight,numTextSizeInSp;

    int levelChosen;
    LinearLayout columnsResults,rowsResults,matrixToFill;

    ArrayList<Integer> columns_results,rows_results;
    ArrayList<ArrayList<Integer>> matrix,solution_matrix,removed_numbers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);


        levelChosen = NuMa_Matrix.BEGINNER;
        if(levelChosen==4){
            numWidthHeight=45;
            numTextSizeInSp=30;
        }

        removed_numbers = new ArrayList<>();
        for(int i=0 ; i<levelChosen ; i++){
            ArrayList<Integer> _row = new ArrayList<>();
            for(int j=0 ; j<levelChosen ; j++){
                _row.add(0);
            }
            removed_numbers.add(_row);
        }

        NuMa_Matrix.FillMatrix(levelChosen);
        columns_results = NuMa_Matrix.getColumns_results();
        rows_results = NuMa_Matrix.getRows_results();
        matrix = NuMa_Matrix.getMatrix();
        solution_matrix = NuMa_Matrix.getSolution_matrix();

        columnsResults = findViewById(R.id.columnsResults);
        rowsResults = findViewById(R.id.rowsResults);
        matrixToFill = findViewById(R.id.matrixToFill);

        for(Integer i : columns_results){
            num = new TextView(this);
            num.setText(String.valueOf(i));
            textViewModifsOnNum(2,2);
            columnsResults.addView(num);
        }

        for(Integer i : rows_results){
            num = new TextView(this);
            num.setText(String.valueOf(i));
            textViewModifsOnNum(2,2);
            rowsResults.addView(num);
        }

        for(int i=0 ; i<levelChosen ; i++){
            ArrayList<Integer> row = matrix.get(i);
            int j=0;
            LinearLayout aRow = new LinearLayout(this);
            aRow.setOrientation(LinearLayout.HORIZONTAL);
            for(Integer in : row){
                num = new TextView(this);
                num.setText(String.valueOf(in));
                textViewModifsOnNum(1,1);
                setNumClickabale(this,num,i,j);
                aRow.addView(num);
                j++;
            }
            matrixToFill.addView(aRow);
        }

    }

    public void textViewModifsOnNum(int color_option, int back_option){
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
        if(back_option==1){
            num.setBackgroundResource(R.drawable.single_node);
        }else{
            num.setBackgroundResource(R.drawable.single_node);
        }

    }

    public void setNumClickabale(Context context,TextView numb,int i,int j){
        numb.setClickable(true);
        numb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(removed_numbers.get(i).get(j)==0){
                    numb.setTextColor(ContextCompat.getColor(context,R.color.foreground3));
                    removed_numbers.get(i).set(j,1);
                }else{
                    numb.setTextColor(ContextCompat.getColor(context,R.color.foreground1));
                    removed_numbers.get(i).set(j,0);
                }

            }
        });
    }
    public int dpToPx(int dp) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}