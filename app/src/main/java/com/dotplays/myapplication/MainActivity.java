package com.dotplays.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = findViewById(R.id.website);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()   // or .detectAll() for all detectable problems
                .penaltyLog()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .penaltyDeath()
                .build());

        String html = "<html>\n" +
                "\n" +
                "<h1>Hello</h1>\n" +
                "\n" +
                "<h2>Hello</h2>\n" +
                "<h6>Hello</h6>\n" +
                "\n" +
                "</html>";

        webView.loadData(html, "text/html", "utf-8");

        String url = "https://vnexpress.net";

        webView.loadUrl(url);

        webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);

                Log.e("%", newProgress + "");
            }
        });

        try {
            InputStream inputStream = getAssets().open("snake.html");
            String game = "";
            Scanner scanner = new Scanner(inputStream);
            while (scanner.hasNext()) {
                game += scanner.nextLine();
            }
            scanner.close();

            webView.loadData(game, "text/html", "utf-8");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadData(View view) {


        try {
            getData();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void getData() throws IOException {
        String link = "https://vnexpress.net";

        URL url = new URL(link);
        HttpURLConnection httpURLConnection = (HttpURLConnection)
                url.openConnection();

        InputStream inputStream = httpURLConnection.getInputStream();
        String data = "";
        Scanner scanner = new Scanner(inputStream);
        while (scanner.hasNext()) {
            data += scanner.nextLine();
        }
        scanner.close();
        Log.e("DATA", data);
    }
}
