package com.example.a08112024;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditBookActivity extends AppCompatActivity {

    private EditText editTextName, editTextAuthor;
    private Button updateButton, deleteButton;
    private DataBaseHelper dbHelper;
    private int bookId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editbook);

        editTextName = findViewById(R.id.editTextName);
        editTextAuthor = findViewById(R.id.editTextAuthor);
        updateButton = findViewById(R.id.update);
        deleteButton = findViewById(R.id.delete);

        dbHelper = new DataBaseHelper(this);

        bookId = getIntent().getIntExtra("book_id", -1);
        String bookName = getIntent().getStringExtra("book_name");
        String bookAuthor = getIntent().getStringExtra("book_author");

        editTextName.setText(bookName);
        editTextAuthor.setText(bookAuthor);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateBook();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteBook();
            }
        });
    }

    private void updateBook() {
        String bookName = editTextName.getText().toString().trim();
        String bookAuthor = editTextAuthor.getText().toString().trim();

        if (bookName.isEmpty() || bookAuthor.isEmpty()) {
            Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
            return;
        }

        long result = dbHelper.updateBook(bookId, bookName, bookAuthor);

        if (result > 0) {
            Toast.makeText(this, "Книга обновлена", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(EditBookActivity.this, MainActivity.class));
            finish();
        } else {
            Toast.makeText(this, "Ошибка обновления книги", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteBook() {
        int result = dbHelper.deleteBookById(bookId);

        if (result > 0) {
            Toast.makeText(this, "Книга удалена", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(EditBookActivity.this, MainActivity.class));
            finish();
        } else {
            Toast.makeText(this, "Ошибка удаления книги", Toast.LENGTH_SHORT).show();
        }
    }
}
