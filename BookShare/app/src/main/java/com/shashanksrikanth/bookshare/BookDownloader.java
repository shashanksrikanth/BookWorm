package com.shashanksrikanth.bookshare;

import android.net.Uri;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class BookDownloader implements Runnable{
    // Calls Google Books API for a given ISBN and processes the results

    String isbn;
    String query = "https://www.googleapis.com/books/v1/volumes?q=isbn:";
    DonorBookPage bookPage;

    public BookDownloader(String isbn, DonorBookPage bookPage) {
        this.isbn = isbn;
        this.bookPage = bookPage;
    }

    @Override
    public void run() {
        Uri.Builder builder = Uri.parse(query + isbn).buildUpon();
        String urlToUse = builder.toString();
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(urlToUse);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return;
            }
            InputStream is = conn.getInputStream();
            BufferedReader reader = new BufferedReader((new InputStreamReader(is)));
            String line;
            while ((line = reader.readLine()) != null) sb.append(line).append('\n');
        }
        catch (Exception e) {
            return;
        }
        processData(sb.toString());
    }

    public void processData(String string) {

    }
}
