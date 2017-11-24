package com.example.ashish.pushnotificationsender;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class SendDetailsToServer extends AsyncTask<String, Void, String> {

    private Context context;
    private Activity activity;
    private AlertDialog alertDialog;
    private ProgressBar progressBar;
    private Button sendButton;

    SendDetailsToServer(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;

        progressBar = (ProgressBar) activity.findViewById(R.id.progressBar);
        sendButton = (Button) activity.findViewById(R.id.send_button);
    }

    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Status");
        progressBar.setVisibility(View.VISIBLE);
        sendButton.setVisibility(View.INVISIBLE);
    }

    @Override
    protected String doInBackground(String... params) {

        String login_url = "https://ashishlakhmani.000webhostapp.com/php_files/notification.php";
        try {
            String message = params[0];
            String title = params[1];
            String picture = params[2];
            URL url = new URL(login_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);

            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = URLEncoder.encode("message", "UTF-8") + "=" + URLEncoder.encode(message, "UTF-8") + "&" +
                    URLEncoder.encode("title", "UTF-8") + "=" + URLEncoder.encode(title, "UTF-8") + "&" +
                    URLEncoder.encode("picture", "UTF-8") + "=" + URLEncoder.encode(picture, "UTF-8");
            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "ISO-8859-1"));
            StringBuilder sb = new StringBuilder("");
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            return sb.toString();

        } catch (Exception e) {
            return "Exception: " + e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        alertDialog.setMessage(result);
        progressBar.setVisibility(View.INVISIBLE);
        sendButton.setVisibility(View.VISIBLE);
        alertDialog.show();
    }


}
