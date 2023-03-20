package com.yogeshaswar.contactmanager_databinding;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.io.DataOutput;
@Database(entities = {Contact.class}, version = 1, exportSchema = false)
public abstract class ContactAppDatabase extends RoomDatabase {
    // dao
    public abstract ContactDAO contactDAO();

    // built database
    private static ContactAppDatabase INSTANCE;
    private static String DATABASE_NAME = "contact.db";
    public static synchronized ContactAppDatabase getDB(Context context) {
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context, ContactAppDatabase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }
}
