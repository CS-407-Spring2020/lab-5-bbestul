package c.sakshi.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NoteTextActivity extends AppCompatActivity {
    int noteid = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_text);

        // Getting Intent
        Intent intent = getIntent();
        EditText editText = (EditText) findViewById(R.id.editText2);
        int idValue = intent.getIntExtra("noteid",-1 );
        noteid = idValue;

        if (noteid != -1){
            // Display content of note
            Note note = NoteActivity.notes.get(noteid);
            String noteContent = note.getContent();
            editText.setText(noteContent);
        }
    }

    public void saveButtonClick(View view){
        SharedPreferences sharedPreferences = getSharedPreferences("c.sakshi.lab5", Context.MODE_PRIVATE);

        // 1. Get edit text View and the content from it
        EditText editText = (EditText) findViewById(R.id.editText2);
        String content = editText.getText().toString();

        // 2. Initialize the SQLDatebase connection.
        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE,null);

        // 3. Initialize the DBHelper class.
        DBHelper dbHelper = new DBHelper(sqLiteDatabase);

        // 4. Set Username in the following variable, fetching it from shared preferences
        String username = sharedPreferences.getString("username", "");

        // 5. Save information to the DB
        String title;
        DateFormat dateformat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String date = dateformat.format(new Date());

        if (noteid == -1){ // Add Note
            title = "NOTE_" + (NoteActivity.notes.size() + 1);
            dbHelper.saveNotes(username, title, content, date);
        } else {
            title = "NOTE_" + ( noteid + 1);
            dbHelper.updateNotes(title, date, content, username);
        }

        // 6. Go back to the second activity
        Intent intent = new Intent(this, NoteActivity.class);
        startActivity(intent);
    }
}
