package com.yogeshaswar.contactmanager_databinding;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "contacts")
public class Contact extends BaseObservable {
    @PrimaryKey(autoGenerate = true)
    int id;
    @ColumnInfo(name = "name")
    String name;
    @ColumnInfo(name = "email")
    String email;
    @Ignore
    public Contact() {
    }

    public Contact(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    @Ignore
    public Contact(String name, String email) {
        this.name = name;
        this.email = email;
    }
    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }
    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(BR.email);
    }
}
