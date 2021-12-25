package com.phuphuc.chonhinhgiongnhau;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.google.android.material.tabs.TabLayout;

import java.util.Collections;

public class ImageActivity extends Activity {

    TableLayout myTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        int soDong = 3;
        int soCot = 3;

        Collections.shuffle(MainActivity.imageNames);

        myTable = (TableLayout) findViewById(R.id.tableLayoutImages);
        for (int i = 0; i < soDong; i++) {
            TableRow row = new TableRow(this);
            for (int j = 0; j < soCot; j++) {
                ImageView image = new ImageView(this);
                float dip = 100;
                Resources r = getResources();
                int px = (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        dip,
                        r.getDisplayMetrics()
                );
                TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(px, px);
                image.setLayoutParams(layoutParams);

                int viTri = soCot * i + j;

                int idHinh = getResources().getIdentifier(MainActivity.imageNames.get(viTri), "drawable", getPackageName());
                image.setImageResource(idHinh);
                row.addView(image);

                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.putExtra("tenhinhchon", MainActivity.imageNames.get(viTri));
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });
            }
            myTable.addView(row);
        }

    }
}