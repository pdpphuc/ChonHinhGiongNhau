package com.phuphuc.chonhinhgiongnhau;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    TextView txtDiem;
    private int diem, diemCong = 10, diemTru = 10, diemGianLan = 15;
    ImageView imgMau, imgChon;
    public static ArrayList<String> imageNames;
    int REQUEST_CODE_IMAGE = 123;
    String tenHinhMau;
    CountDownTimer timer;
    SharedPreferences luuDiemSo;

    private void choiMoi() {
        Collections.shuffle(imageNames);
        tenHinhMau = imageNames.get(0);
        int idHinh = getResources().getIdentifier(tenHinhMau, "drawable", getPackageName());
        imgMau.setImageResource(idHinh);
    }

    private void LuuDiem() {
        SharedPreferences.Editor editor = luuDiemSo.edit();
        editor.putInt("diem", diem);
        editor.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        luuDiemSo = getSharedPreferences("DiemSoGame", MODE_PRIVATE);
        diem = luuDiemSo.getInt("diem", 100);
        timer = new CountDownTimer(2000, 2000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                choiMoi();
            }
        };
        String[] mangTen = getResources().getStringArray(R.array.list_name);
        imageNames = new ArrayList<>(Arrays.asList(mangTen));

        txtDiem = (TextView) findViewById(R.id.textViewDiemSo);
        imgMau = (ImageView) findViewById(R.id.imageViewHinhMau);
        imgChon = (ImageView) findViewById(R.id.imageViewHinhChon);
        imgChon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MainActivity.this, ImageActivity.class), REQUEST_CODE_IMAGE);
            }
        });

        txtDiem.setText(diem + "");
        choiMoi();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_IMAGE && resultCode == RESULT_OK && data != null) {
            String tenHinhChon = data.getStringExtra("tenhinhchon");
            int idHinhChon = getResources().getIdentifier(tenHinhChon, "drawable", getPackageName());
            imgChon.setImageResource(idHinhChon);
            if (tenHinhChon.equals(tenHinhMau)) {
                Toast.makeText(this, "Chính xác! Bạn được cộng " + diemCong + " điểm!", Toast.LENGTH_SHORT).show();
                diem += 10;
                timer.start();
            }
            else {
                Toast.makeText(this, "Sai rồi! Bạn bị trừ " + diemTru + " điểm!", Toast.LENGTH_SHORT).show();
                diem -= 10;
            }
            LuuDiem();
            txtDiem.setText(diem + "");
        }
        if (requestCode == REQUEST_CODE_IMAGE && resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "Bạn chưa chọn hình! Bị trừ " + diemGianLan + " điểm!", Toast.LENGTH_SHORT).show();
            diem -= diemGianLan;
            LuuDiem();
            txtDiem.setText(diem + "");
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.reload, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuReload) {
            choiMoi();
        }
        return super.onOptionsItemSelected(item);
    }
}