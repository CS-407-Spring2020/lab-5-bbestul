package c.sakshi.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class NoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        Intent intent = getIntent();
        String username = intent.getStringExtra("USERNAME");
        TextView welcome = findViewById(R.id.WelcomeText);
        String fullWelcome = "Welcome " + username;
        welcome.setText(fullWelcome);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.logout:
                logoutOnClick();
                return true;
            case R.id.addnote:
                //adding more info here
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void logoutOnClick(){
        Intent intent = new Intent(this,MainActivity.class);
        SharedPreferences sharedPreferences = getSharedPreferences("c.sakshi.lab5", Context.MODE_PRIVATE);
        sharedPreferences.edit().remove("username").apply();
        startActivity(intent);
    }
}
