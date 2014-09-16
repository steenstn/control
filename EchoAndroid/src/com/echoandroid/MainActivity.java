package com.echoandroid;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity
    extends Activity {

    Socket socket;
    String hostIp = "192.168.0.140";
    int portNumber = 4444;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        // then you use
        hostIp = prefs.getString("ip", "192.168.0.140");
        TextView serverResponse = (TextView) findViewById(R.id.textViewServerResponse);
        serverResponse.setText("Connecting to " + hostIp);
        new Thread(new ClientThread()).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    public void sendCommand(View v) throws IOException {
        int id = v.getId();
        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
        switch (id) {
        case R.id.buttonPause:
            out.println("p");
            break;
        case R.id.buttonBack:
            out.println("b");
            break;
        case R.id.buttonNext:
            out.println("n");
            break;
        case R.id.buttonVolumeUp:
            out.println("u");
            break;
        case R.id.buttonVolumeDown:
            out.println("d");
            break;
        }

        new ReadServer().execute();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        DialogFragment newFragment;
        switch (item.getItemId()) {
        case R.id.menu_settings:
            intent = new Intent(this, PreferencesActivity.class);
            startActivity(intent);
            return true;
        }
        return false;
    }

    class ReadServer
        extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... arg0) {
            String result;
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                result = in.readLine();
            } catch (IOException e) {
                result = e.getMessage();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            TextView serverResponse = (TextView) findViewById(R.id.textViewServerResponse);
            serverResponse.setText("Server response: " + result);
        }
    }

    class ClientThread
        implements Runnable {

        @Override
        public void run() {

            try {
                InetAddress serverAddr = InetAddress.getByName(hostIp);
                socket = new Socket(serverAddr, portNumber);
                new ReadServer().execute();

            } catch (UnknownHostException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }

    }

}
