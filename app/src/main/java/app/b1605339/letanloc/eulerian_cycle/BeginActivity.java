/*
 * Created by Lê Tấn Lộc on 15:10 18/01/2019
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 03:11 18/01/2019
 */

package app.b1605339.letanloc.eulerian_cycle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class BeginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_begin);

        Button btnEulerianCycle = (Button) findViewById(R.id.btnEulerianCycle);
        btnEulerianCycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText etSoDinh = (EditText) findViewById(R.id.etSoDinh);
                Graph graph = new Graph(Integer.parseInt(etSoDinh.getText().toString()));

                graph.addEdge(3, 4);
                graph.addEdge(4, 2);
                graph.addEdge(2, 4);
                graph.addEdge(3, 3);
            }
        });



        /*EditText etDinh2 = (EditText) findViewById(R.id.etDinh2);
        ArrayList<TextView> tvList1 = new ArrayList<TextView>();
        ArrayList<TextView> tvList2 = new ArrayList<TextView>();
        etDinh2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(etDinh2.getText().toString.equals("")){
                    LinearLayout llDinh1 = (LinearLayout) findViewById(R.id.llDinh1);
                    EditText etSoDinhNew = new EditText(BeginActivity.this);
                    llDinh1.addView(etSoDinhNew);
                    etSoDinhNew.setText(String.valueOf(etSoDinhNew.getId()));

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/
    }
}
