/*
 * Created by Lê Tấn Lộc on 13:37 01/03/2019
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 13:37 01/03/2019
 */

package app.b1605339.letanloc.eulerian_cycle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import app.b1605339.letanloc.eulerian_cycle.Graphic.DrawGraph;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout llEC = findViewById(R.id.llEC);

        final DrawGraph drawGraph = new DrawGraph(this);
        llEC.addView(drawGraph);
        Button btnSaveFile = findViewById(R.id.btnExportFile);
        btnSaveFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText etFileName = (EditText) findViewById(R.id.etFileName);
                if (etFileName.getText().toString().isEmpty()) {
                    etFileName.setError("Nhập tên file");
                } else {
                    if (drawGraph.saveFile(etFileName.getText().toString())) {
                        Toast.makeText(MainActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
