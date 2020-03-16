package edu.pdx.cs410j.sytov.airlineandroidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private Intent airMan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.airMan = new Intent(this, AirlineManager.class);
    }

    public void printReadme(View view) {
        setContentView(R.layout.readme);
    }

    public void mainMenu(View view) {
        setContentView(R.layout.activity_main);
    }

    public void goToAddFlightMenu(View view) {
//        Intent intent = new Intent(this, AirlineManager.class);
        startActivity(this.airMan);
    }
}
