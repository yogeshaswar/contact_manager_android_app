package com.yogeshaswar.contactmanager_databinding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Toast;

import com.yogeshaswar.contactmanager_databinding.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding activityMainBinding;
    static RecyclerView rvContacts;
    ContactAdapter contactAdapter;
    ContactAppDatabase db;
    List<Contact> contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        ClickHandler handler = new ClickHandler(this);
        activityMainBinding.setClickHandler(handler);

        // recycler view
        rvContacts = findViewById(R.id.rv_contacts);
        rvContacts.setLayoutManager(new LinearLayoutManager(this));
        rvContacts.setHasFixedSize(true);
        db = ContactAppDatabase.getDB(this);
//        contacts = db.contactDAO().getContacts();
        contacts = new ArrayList<>();
        loadContacts();
        contactAdapter = new ContactAdapter(this , contacts);
        rvContacts.setAdapter(contactAdapter);

        // delete on swipe left
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Contact contact  = contacts.get(viewHolder.getAdapterPosition());
                deleteContact(contact);
            }
        }).attachToRecyclerView(rvContacts);


    }

    public class ClickHandler {
        Context context;
        public ClickHandler(Context context) {
            this.context = context;
        }

        public void fabClickHandler(View view) {
            Intent intent = new Intent(MainActivity.this, NewContact.class);
            startActivity(intent);
        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode==1 && resultCode==RESULT_OK) {
//            String name = data.getStringExtra("NAME");
//            String email = data.getStringExtra("EMAIL");
//            Contact contact = new Contact(name, email);
//            AddNewContact(contact);
//        }
//    }

    private void AddNewContact(Contact contact) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                // background
                db.contactDAO().addContact(contact);
                // post background
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        loadContacts();
                        contactAdapter.notifyDataSetChanged();
                    }
                });
            }
        });

    }

    private void deleteContact(Contact contact) {
        Toast.makeText(this, "delete", Toast.LENGTH_SHORT).show();
        db.contactDAO().deleteContact(contact);
//        loadContacts();
        contactAdapter.setList(db.contactDAO().getContacts());

    }

    private void loadContacts() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                // back - contacts list - update
                contacts.addAll(db.contactDAO().getContacts());
                // post
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        // adapter notify
                        contactAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }
}