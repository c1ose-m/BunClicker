package com.example.bunclicker;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    EditText login;
    EditText password;
    TextView your;
    TextView left;
    TextView power;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = findViewById(R.id.login);
        password = findViewById(R.id.password);
        your = findViewById(R.id.your);
        left = findViewById(R.id.left);
        power = findViewById(R.id.power);
        db = getBaseContext().openOrCreateDatabase("bun_clicker.db", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS users (name TEXT, password TEXT, UNIQUE(name));");
        db.execSQL("CREATE TABLE IF NOT EXISTS goblins (name TEXT, increment INTEGER, cost INTEGER, UNIQUE(name));");
        db.execSQL("CREATE TABLE IF NOT EXISTS states (user TEXT REFERENCES users(name), yourBun INTEGER, leftBun INTEGER, power INTEGER, UNIQUE(user));");
        db.execSQL("INSERT OR IGNORE INTO goblins VALUES ('goblin', 5, 100), ('anuk', 25, 500), ('alotar', 50, 1000);");
    }

    public void clickLogin(View view) {
        String currentLogin = String.valueOf(login.getText());
        Cursor query = db.rawQuery("SELECT * FROM states WHERE user = '" + currentLogin + "';", null);
        if (query.moveToFirst()) {
            your.setText(String.valueOf(query.getInt(1)));
            left.setText(String.valueOf(query.getInt(2)));
            power.setText(String.valueOf(query.getInt(3)));
        }
        query.close();
    }

    public void clickSignin(View view) {
        String currentLogin = String.valueOf(login.getText());
        String currentPassword = String.valueOf(password.getText());
        db.execSQL("INSERT OR IGNORE INTO users VALUES ('" + currentLogin + "', '" + currentPassword + "');");
        your.setText("0");
        left.setText("1000000");
        power.setText("5");
        db.execSQL("INSERT OR IGNORE INTO states VALUES ('" + currentLogin + "', " + Integer.parseInt(String.valueOf(your.getText())) + ", " + Integer.parseInt(String.valueOf(left.getText())) + ", " + Integer.parseInt(String.valueOf(power.getText())) + ");");
    }

    public void clickBun(View view) {
        int current = Integer.parseInt(String.valueOf(power.getText()));
        int currentYour = Integer.parseInt(String.valueOf(your.getText()));
        int currentLeft = Integer.parseInt(String.valueOf(left.getText()));
        String currentLogin = String.valueOf(login.getText());
        your.setText(String.valueOf(currentYour + current));
        left.setText(String.valueOf(currentLeft - current));
        db.execSQL("UPDATE states SET yourBun = " + Integer.parseInt(String.valueOf(your.getText())) + ", leftBun = " + Integer.parseInt(String.valueOf(left.getText())) + ", power = " + Integer.parseInt(String.valueOf(power.getText())) + " WHERE user = '" + currentLogin + "';");
    }

    public void goblinClick(View view) {
        String currentLogin = String.valueOf(login.getText());
        Cursor query = db.rawQuery("SELECT * FROM goblins WHERE name = 'goblin';", null);
        int current = Integer.parseInt(String.valueOf(your.getText()));
        if (query.moveToFirst()) {
            if (current >= query.getInt(2)) {
                power.setText(String.valueOf(Integer.parseInt(String.valueOf(power.getText())) + query.getInt(1)));
                your.setText(String.valueOf(current - query.getInt(2)));
            }
        }
        query.close();
        db.execSQL("UPDATE states SET yourBun = " + Integer.parseInt(String.valueOf(your.getText())) + ", leftBun = " + Integer.parseInt(String.valueOf(left.getText())) + ", power = " + Integer.parseInt(String.valueOf(power.getText())) + " WHERE user = '" + currentLogin + "';");
    }

    public void anukClick(View view) {
        String currentLogin = String.valueOf(login.getText());
        Cursor query = db.rawQuery("SELECT * FROM goblins WHERE name = 'anuk';", null);
        int current = Integer.parseInt(String.valueOf(your.getText()));
        if (query.moveToFirst()) {
            if (current >= query.getInt(2)) {
                power.setText(String.valueOf(Integer.parseInt(String.valueOf(power.getText())) + query.getInt(1)));
                your.setText(String.valueOf(current - query.getInt(2)));
            }
        }
        query.close();
        db.execSQL("UPDATE states SET yourBun = " + Integer.parseInt(String.valueOf(your.getText())) + ", leftBun = " + Integer.parseInt(String.valueOf(left.getText())) + ", power = " + Integer.parseInt(String.valueOf(power.getText())) + " WHERE user = '" + currentLogin + "';");
    }

    public void alotarClick(View view) {
        String currentLogin = String.valueOf(login.getText());
        Cursor query = db.rawQuery("SELECT * FROM goblins WHERE name = 'alotar';", null);
        int current = Integer.parseInt(String.valueOf(your.getText()));
        if (query.moveToFirst()) {
            if (current >= query.getInt(2)) {
                power.setText(String.valueOf(Integer.parseInt(String.valueOf(power.getText())) + query.getInt(1)));
                your.setText(String.valueOf(current - query.getInt(2)));
            }
        }
        query.close();
        db.execSQL("UPDATE states SET yourBun = " + Integer.parseInt(String.valueOf(your.getText())) + ", leftBun = " + Integer.parseInt(String.valueOf(left.getText())) + ", power = " + Integer.parseInt(String.valueOf(power.getText())) + " WHERE user = '" + currentLogin + "';");
    }
}