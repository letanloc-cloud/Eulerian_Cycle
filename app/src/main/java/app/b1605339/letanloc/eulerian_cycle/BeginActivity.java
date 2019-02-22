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
import android.widget.Toast;

import java.util.ArrayList;

public class BeginActivity extends AppCompatActivity {
    private Graph graph = new Graph(0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_begin);


        final Button btnSaveGraphAsFile = (Button) findViewById(R.id.btnSaveGraphAsFile);
        btnSaveGraphAsFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText etTenFile = (EditText) findViewById(R.id.etTenFile);
                if (etTenFile.getText().toString().isEmpty()) {
                    etTenFile.setError("Nhập tên file");
                } else {
                    EditText etMaTran = (EditText) findViewById(R.id.etMaTran);
                    String fileName = etTenFile.getText().toString();
                    String fileContent = etMaTran.getText().toString();
                    FileOperations fop = new FileOperations();
                    Boolean write = fop.write(fileName, fileContent);

                    if (write) {
                        Toast.makeText(BeginActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(BeginActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        final EditText etDinh1 = (EditText) findViewById(R.id.etDinh1);
        final EditText etDinh2 = (EditText) findViewById(R.id.etDinh2);

        Button btnThem = (Button) findViewById(R.id.btnThem);
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText etSoDinh = (EditText) findViewById(R.id.etSoDinh);
                if (etSoDinh.getText().toString().isEmpty()) {
                    etSoDinh.setError("Nhập số đỉnh");
                    //Toast.makeText(BeginActivity.this, "Nhập số đỉnh", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    int sodinh = Integer.parseInt(etSoDinh.getText().toString());
                    if (sodinh != graph.getNumberOfVertices()) {
                        graph = new Graph(sodinh);
                        String maxtrix = new String("");
                        for (int i = 0; i < graph.getNumberOfVertices(); i++) {
                            for (int j = 0; j < graph.getNumberOfVertices(); j++) {
                                maxtrix = maxtrix + graph.printEdge(i, j) + " ";
                            }
                            maxtrix = maxtrix + System.getProperty("line.separator");
                        }
                        EditText etMaTran = (EditText) findViewById(R.id.etMaTran);
                        etMaTran.setText(maxtrix);
                    }
                }

                if (etDinh1.getText().toString().isEmpty()) {
                    etDinh1.setError("Nhập đỉnh 1");
                    return;
                }
                if (etDinh2.getText().toString().isEmpty()) {
                    etDinh2.setError("Nhập đỉnh 2");
                    return;
                }

                Integer x = Integer.parseInt(etDinh1.getText().toString());
                Integer y = Integer.parseInt(etDinh2.getText().toString());

                if (x < 0) {
                    etDinh1.setError("Đỉnh phải lớn hơn hoặc bằng 0");
                    return;
                }
                if (y < 0) {
                    etDinh2.setError("Đỉnh phải lớn hơn hoặc bằng 0");
                    return;
                }

                if (x >= graph.getNumberOfVertices()) {
                    etDinh1.setError("Đỉnh phải nhỏ hơn số đỉnh");
                    return;
                }
                if (y >= graph.getNumberOfVertices()) {
                    etDinh2.setError("Đỉnh phải nhỏ hơn số đỉnh");
                    return;
                }

                graph.addEdge(x, y);
                etDinh1.setText("");
                etDinh2.setText("");
                String maxtrix = new String("");
                for (int i = 0; i < graph.getNumberOfVertices(); i++) {
                    for (int j = 0; j < graph.getNumberOfVertices(); j++) {
                        maxtrix = maxtrix + graph.printEdge(i, j) + " ";
                    }
                    maxtrix = maxtrix + System.getProperty("line.separator");
                }
                EditText etMaTran = (EditText) findViewById(R.id.etMaTran);
                etMaTran.setText(maxtrix);

                TextView tvKetQua = (TextView) findViewById(R.id.tvKetQua);
                tvKetQua.setText("");
                tvKetQua.setText(graph.getEulerianCycle());

                TextView tvSoMienLienThong = (TextView) findViewById(R.id.tvSoMienLienThong);
                tvSoMienLienThong.setText("");
                tvSoMienLienThong.setText(graph.countConnectedComponents() + "");
            }
        });

        Button btnXoa = (Button) findViewById(R.id.btnXoa);
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText etSoDinh = (EditText) findViewById(R.id.etSoDinh);
                if (etSoDinh.getText().toString().isEmpty()) {
                    etSoDinh.setError("Nhập số đỉnh");
                    //Toast.makeText(BeginActivity.this, "Nhập số đỉnh", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    int sodinh = Integer.parseInt(etSoDinh.getText().toString());
                    if (sodinh != graph.getNumberOfVertices()) {
                        graph = new Graph(sodinh);
                        String maxtrix = new String("");
                        for (int i = 0; i < graph.getNumberOfVertices(); i++) {
                            for (int j = 0; j < graph.getNumberOfVertices(); j++) {
                                maxtrix = maxtrix + graph.printEdge(i, j) + " ";
                            }
                            maxtrix = maxtrix + System.getProperty("line.separator");
                        }
                        EditText etMaTran = (EditText) findViewById(R.id.etMaTran);
                        etMaTran.setText(maxtrix);
                    }
                }

                if (etDinh1.getText().toString().isEmpty()) {
                    etDinh1.setError("Nhập đỉnh 1");
                    return;
                }
                if (etDinh2.getText().toString().isEmpty()) {
                    etDinh2.setError("Nhập đỉnh 2");
                    return;
                }

                Integer x = Integer.parseInt(etDinh1.getText().toString());
                Integer y = Integer.parseInt(etDinh2.getText().toString());

                if (x < 0) {
                    etDinh1.setError("Đỉnh phải lớn hơn hoặc bằng 0");
                    return;
                }
                if (y < 0) {
                    etDinh2.setError("Đỉnh phải lớn hơn hoặc bằng 0");
                    return;
                }

                if (x >= graph.getNumberOfVertices()) {
                    etDinh1.setError("Đỉnh phải nhỏ hơn số đỉnh");
                    return;
                }
                if (y >= graph.getNumberOfVertices()) {
                    etDinh2.setError("Đỉnh phải nhỏ hơn số đỉnh");
                    return;
                }

                graph.removeEdge(x, y);
                etDinh1.setText("");
                etDinh2.setText("");

                String maxtrix = new String("");
                for (int i = 0; i < graph.getNumberOfVertices(); i++) {
                    for (int j = 0; j < graph.getNumberOfVertices(); j++) {
                        maxtrix = maxtrix + graph.printEdge(i, j) + " ";
                    }
                    maxtrix = maxtrix + System.getProperty("line.separator");
                }
                EditText etMaTran = (EditText) findViewById(R.id.etMaTran);
                etMaTran.setText(maxtrix);

                TextView tvKetQua = (TextView) findViewById(R.id.tvKetQua);
                tvKetQua.setText("");
                tvKetQua.setText(graph.getEulerianCycle());

                TextView tvSoMienLienThong = (TextView) findViewById(R.id.tvSoMienLienThong);
                tvSoMienLienThong.setText("");
                tvSoMienLienThong.setText(graph.countConnectedComponents() + "");
            }
        });

    }
}
