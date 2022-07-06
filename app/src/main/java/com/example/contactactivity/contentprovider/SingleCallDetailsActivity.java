package com.example.contactactivity.contentprovider;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.contactactivity.R;
import com.squareup.picasso.Picasso;

public class SingleCallDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_call_details);

        TextView tvCallerName = findViewById(R.id.tv_call_name);
        TextView tvCallerNumber = findViewById(R.id.tv_user_number);
        ImageView ivCallerImage = findViewById(R.id.iv_full_call_image);

        String callerName = getIntent().getStringExtra("name");
        String callerNumber = getIntent().getStringExtra("number");
        String callerImage = getIntent().getStringExtra("callerImage");
        Uri uriImage = Uri.parse(callerImage);
        tvCallerName.setText(callerName);
        tvCallerNumber.setText(callerNumber);
        ivCallerImage.setImageResource(R.drawable.ic_call_default);
        ivCallerImage.setImageURI(uriImage);
//        Picasso.get().load(uriImage).placeholder(R.drawable.ic_call_default).into(ivCallerImage);
    }
}