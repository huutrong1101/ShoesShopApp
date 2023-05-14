package com.example.shoesshop.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shoesshop.Activity.ProductDetailActivity;
import com.example.shoesshop.Model.Product;
import com.example.shoesshop.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SearchItemAdapter extends RecyclerView.Adapter<SearchItemAdapter.SearchItemViewHolder>{
    List<Product> mListProducts;

    Context context;


    public void setFilteredList(List<Product> filteredList){
        this.mListProducts = filteredList;
        notifyDataSetChanged();
    }

    public SearchItemAdapter(List<Product> mListProducts, Context context){
        this.mListProducts = mListProducts;
        this.context = context;
    }

    @NonNull
    @Override
    public SearchItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search, parent, false);
        return new SearchItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchItemViewHolder holder, int position) {
        Product product = mListProducts.get(position);

        if (product == null){
            return;
        }

        if (product.getImage() != null){
            int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(product.getImage(), "drawable", holder.itemView.getContext().getPackageName());
            Glide.with(context)
                    .load(product.getImage())
                    .into(holder.imgItem);
        }

        holder.name.setText(product.getName());
        holder.price.setText(String.valueOf(product.getPrice()+" đ"));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductDetailActivity.class);
                intent.putExtra("id", product.getId());
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mListProducts == null) {
            return 0;
        } else {
            return mListProducts.size();
        }
    }

    public class SearchItemViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView imgItem;
        private TextView name, price;

        public SearchItemViewHolder(@NonNull View itemView) {
            super(itemView);

            imgItem = itemView.findViewById(R.id.img_user);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
        }
    }
}
