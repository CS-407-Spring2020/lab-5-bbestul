package c.sakshi.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class NoteActivity extends AppCompatActivity {

    public static ArrayList<Note> notes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        // 1) getting the name from the previous method
        Intent intent = getIntent();
        SharedPreferences sharedPreferences = getSharedPreferences("c.sakshi.lab5", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");

        TextView welcome = findViewById(R.id.WelcomeText);
        String fullWelcome = "Welcome " + username;
        welcome.setText(fullWelcome);

        // 2) Getting the SQL DB Instance
        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE,null);

        // 3) Create and add to the Notes Class Variable
        DBHelper dbHelper = new DBHelper(sqLiteDatabase);
        notes = dbHelper.readNotes(username);

        // 4) Create An ArrayList<String> object by iterating over notes object
        ArrayList<String> displayNotes = new ArrayList<>();
        for(Note note : notes){
            displayNotes.add(String.format("Title:%s\nDate:%s", note.getTitle(), note.getDate()));
        }

        // 5) user ListView to view display notes on screen
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, displayNotes);
        ListView listView = (ListView) findViewById(R.id.ListView);
        listView.setAdapter(adapter);

        // 6) add onItemClickListener for list view item
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Intent intent1 = new Intent(getApplicationContext(), NoteTextActivity.class);
                intent1.putExtra("noteid", position);
                intent1.putExtra("usernamePass", "test");
                startActivity(intent1);
            }

        });
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
                addNoteOnClick();
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

    public void addNoteOnClick(){
        Intent intent = new Intent(this, NoteTextActivity.class);
        startActivity(intent);
    }
}
