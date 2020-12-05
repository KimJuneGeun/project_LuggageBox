package com.example.luggagebox;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.text.SimpleDateFormat;
import java.util.Date;

public class QRcodeCreate extends AppCompatActivity {

    private ImageView btnBack;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newqr);

        //뒤로가기 기능
        btnBack = (ImageView) findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
            }
        });
        ImageView img_barcode;
        img_barcode = (ImageView)findViewById(R.id.img_barcode) ;
        //현재 시간 가져온다
        long now = System.currentTimeMillis();
        //Date 형식으로 고친다.
        Date mDate = new Date(now);
        //날짜 시간을 가져오고 싶은 형태로 가져올 수 있다.
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String getTime = simpleDate.format(mDate);

        // TODO : QR코드를 읽었을떄, 문자열값
        String value = getTime;
        // ex : 01063313034
        // ex : 300,000,000
        createQRcode(img_barcode, value);

    }
    public void createQRcode(ImageView img, String text) {

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE, 200, 200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            img.setImageBitmap(bitmap);
        } catch (Exception e) {
        }
    }
}
