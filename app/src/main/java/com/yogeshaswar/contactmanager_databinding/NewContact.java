package com.yogeshaswar.contactmanager_databinding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.yogeshaswar.contactmanager_databinding.databinding.ActivityMainBinding;
import com.yogeshaswar.contactmanager_databinding.databinding.ActivityNewContactBinding;

import java.util.List;

public class NewContact extends AppCompatActivity {
    private ActivityNewContactBinding activityNewContactBinding;
    ContactAppDatabase db;
    Contact contact;
    EditText edtName, edtEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);
        // edt
        activityNewContactBinding = DataBindingUtil.setContentView(this, R.layout.activity_new_contact);
        NewContactClickHandler newClickHandler = new NewContactClickHandler(this);
        activityNewContactBinding.setNewClickHandler(newClickHandler);
//        activityNewContactBinding.setContact(new Contact(edtName.getText().toString(), edtEmail.getText().toString()));

        // edt values
        edtName = findViewById(R.id.edt_name);
        edtEmail = findViewById(R.id.edt_email);
    }

    public class NewContactClickHandler {
        Context context;

        public NewContactClickHandler(Context context) {
            this.context = context;
        }

        public void btnAddClicked(View view) {
            if((edtName.getText().toString()).equals("")) {
                Toast.makeText(NewContact.this, "contact cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(context, "Button Add Clicked", Toast.LENGTH_SHORT).show();
            db = ContactAppDatabase.getDB(context);
            ContactDAO contactDAO = db.contactDAO();
//            // TODO get entered name and email from Add Contact Activity using data binding
            contactDAO.addContact(new Contact(edtName.getText().toString(), edtEmail.getText().toString()));
            List<Contact> contacts = contactDAO.getContacts();
            MainActivity.rvContacts.setAdapter(new ContactAdapter(MainActivity.rvContacts.getContext() , contacts));

//
//            // with intent
            Intent intent = new Intent(NewContact.this, MainActivity.class);
//            intent.putExtra("NAME", contact.getName());
//            intent.putExtra("EMAIL", contact.getEmail());
            startActivity(intent);
            finish();

        }
    }
}


