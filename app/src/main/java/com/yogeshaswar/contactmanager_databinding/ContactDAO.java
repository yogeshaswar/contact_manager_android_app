package com.yogeshaswar.contactmanager_databinding;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Ignore;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ContactDAO {
    // methods
    @Insert
    void addContact(Contact contact);
    @Delete
    void deleteContact(Contact contact);
    @Update
    void updateContact(Contact contact);
    @Query("SELECT * FROM contacts ")
    List<Contact> getContacts();
}
