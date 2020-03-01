package c.sakshi.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String usernameKey = "username";
        SharedPreferences sharedPreferences = getSharedPreferences("c.sakshi.lab5", Context.MODE_PRIVATE);

        if (!sharedPreferences.getString(usernameKey, "").equals("")){
            // Someone logged in
            Intent intent = new Intent(this, NoteActivity.class);
            String username = sharedPreferences.getString("username", "");
            intent.putExtra("USERNAME", username);

            startActivity(intent);
        } else {
            setContentView(R.layout.activity_main);
        }
    }

    public void noteActivitySwitch(View view){
        // Starting the intent
        Intent intent = new Intent(this, NoteActivity.class);

        // Adding the username to the intent so it is passed through the app
        EditText usernameEditText = (EditText) findViewById(R.id.username);
        String username = usernameEditText.getText().toString();
        intent.putExtra("USERNAME", username);

        // Creating shared preference
        SharedPreferences sharedPreferences = getSharedPreferences("c.sakshi.lab5", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("username", username).apply();

        startActivity(intent);
    }

}
