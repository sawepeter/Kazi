package com.example.ajira.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ajira.R;
import com.example.ajira.model.JobUpdateResponse;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {

    List<JobUpdateResponse> cartModelList = new ArrayList<>();
    private Context context;
    JobUpdateResponse jobs;

    public SearchAdapter(List<JobUpdateResponse> cartModelList, Context context) {
        this.cartModelList = cartModelList;
        this.context = context;
    }

    public void setCartModelList(List<JobUpdateResponse> cartModelList) {
        this.cartModelList = cartModelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pending_job_item,parent,false);
        context = parent.getContext();
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView cart_title, cart_price_per_piece,cart_total,item_count;
        private ImageView cart_image, cart_delete,img_btn_subtract,img_btn_add;

        public MyViewHolder(@NonNull View view) {
            super(view);
           /* cart_image = view.findViewById(R.id.cart_image);
            cart_title = view.findViewById(R.id.cart_title);
            cart_price_per_piece = view.findViewById(R.id.cart_price_per_piece);
            item_count = view.findViewById(R.id.item_count);
            cart_total = view.findViewById(R.id.cart_total);
            cart_delete = view.findViewById(R.id.cart_delete);
            img_btn_subtract = view.findViewById(R.id.img_btn_subtract);
            img_btn_add = view.findViewById(R.id.img_btn_add);*/
        }
    }
}
