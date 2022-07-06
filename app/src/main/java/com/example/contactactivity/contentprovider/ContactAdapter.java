package com.example.contactactivity.contentprovider;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contactactivity.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.viewHolder> {

    private final Context context;
    private final ArrayList<ContactsModel> contactsList;

    public ContactAdapter(Context context, ArrayList<ContactsModel> contactsList) {
        this.context = context;
        this.contactsList = contactsList;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_contact_view, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        ContactsModel contactsModel = contactsList.get(position);

        holder.tvContactName.setText(contactsModel.getContactName());
        holder.tvContactNumber.setText(contactsModel.getContactNumber());
        if (contactsModel.getContactImage() == null)
            holder.ivContactImage.setImageResource(R.drawable.ic_call_default);
        else
            holder.ivContactImage.setImageURI(contactsModel.getContactImage());

        //Picasso.get().load(contactsModel.contactImage).placeholder(R.drawable.ic_call_default).into(holder.ivContactImage);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, SingleCallDetailsActivity.class);
            intent.putExtra("name", contactsModel.getContactName());
            intent.putExtra("number", contactsModel.getContactNumber());
            intent.putExtra("callerId", contactsModel.getContactId());
            //  intent.putExtra("callerImage", contactsModel.getContactImage().toString());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return contactsList.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivContactImage;
        private final TextView tvContactName;
        private final TextView tvContactNumber;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            ivContactImage = itemView.findViewById(R.id.iv_contact_image);
            tvContactName = itemView.findViewById(R.id.tv_contact_name);
            tvContactNumber = itemView.findViewById(R.id.tv_contact_number);

        }
    }
}
