/*
 * Created by Lê Tấn Lộc on 13:37 01/03/2019
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 13:37 01/03/2019
 */

package app.b1605339.letanloc.eulerian_cycle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import app.b1605339.letanloc.eulerian_cycle.Graphic.DrawGraph;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout llEC = findViewById(R.id.llEC);

        DrawGraph drawGraph = new DrawGraph(this);
        llEC.addView(drawGraph);
    }
}
