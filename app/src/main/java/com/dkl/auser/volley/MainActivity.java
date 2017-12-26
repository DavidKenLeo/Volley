package com.dkl.auser.volley;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import org.xml.sax.XMLReader;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class MainActivity extends AppCompatActivity {
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img = (ImageView) findViewById(R.id.imageView);

    }
    public void clickDown(View v)
    {
        final ProgressBar pb = (ProgressBar)findViewById(R.id.progressBar);
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {

                    URL url = new URL("https://upload.wikimedia.org/wikipedia/commons/3/30/Amazona_aestiva_-upper_body-8a_%281%29.jpg");
//                    URL url = new URL("http://images.all-free-download.com/images/graphiclarge/butterfly_flower_01_hd_pictures_166973.jpg");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    ByteArrayOutputStream os = new ByteArrayOutputStream();
                    byte b[] = new byte[1024];
                    int readSize;
                    while ((readSize = is.read(b)) != -1)
                    {
                        os.write(b, 0, readSize);
                    }
                    byte result[] = os.toByteArray();
                    final Bitmap bmp = BitmapFactory.decodeByteArray(result, 0, result.length);

                    Log.d("NET", "Image Finish");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            img.setImageBitmap(bmp);
                            pb.setVisibility(View.INVISIBLE);

                        }
                    });
                    is.close();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }
}
