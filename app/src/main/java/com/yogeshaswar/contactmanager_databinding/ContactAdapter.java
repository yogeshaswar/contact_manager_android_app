package com.yogeshaswar.contactmanager_databinding;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    Context context;
    List<Contact> list;

    public ContactAdapter(Context context, List<Contact> list) {
        this.context = context;
        this.list = list;
    }

    public void setList(List<Contact> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.contact_card, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        holder.name.setText(list.get(position).getName());
        holder.email.setText(list.get(position).getEmail());
//        list.get(holder.getAdapterPosition())
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {
        TextView name, email;
        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_name);
            email = itemView.findViewById(R.id.tv_email);
        }
    }

}
