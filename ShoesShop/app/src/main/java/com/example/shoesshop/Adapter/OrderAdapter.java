package com.example.shoesshop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shoesshop.Model.Product;
import com.example.shoesshop.R;
import com.example.shoesshop.SharedPrefManager;
import com.example.shoesshop.Model.ProductInCart;

import java.text.DecimalFormat;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    Context context;
    private List<ProductInCart> shoeCartList;

    private TextView cartActivityTotalPriceTv;


    public OrderAdapter(List<ProductInCart> shoeCartList, Context context, TextView cartActivityTotalPriceTv) {
        this.shoeCartList = shoeCartList;
        this.context = context;
        this.cartActivityTotalPriceTv = cartActivityTotalPriceTv;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.each_order_item, parent, false);
        return new OrderViewHolder(view);
    }

    public Product getProductByID(ProductInCart pCart) {
        List<Product> productList = SharedPrefManager.getInstance(context.getApplicationContext()).getData();
        for (Product product : productList) {
            if (product.getId().equals(pCart.getProductId())) {
                return product;
            }
        }
        return null;
    }

    private void updateTotalPrice(List<ProductInCart> listProductCart) {
        double totalPrice = 0;
        List<Product> productList = SharedPrefManager.getInstance(context.getApplicationContext()).getData();
        for (Product p : productList) {
            for (ProductInCart pCart : listProductCart) {
                if (p.getId().equals(pCart.getProductId())) {
                    totalPrice += pCart.getQuantity() * p.getPrice();
                    break;
                }
            }
        }
        DecimalFormat decimalFormat = new DecimalFormat("#,###.##");
        String formattedTotalPrice = decimalFormat.format(totalPrice);
        cartActivityTotalPriceTv.setText(formattedTotalPrice);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        ProductInCart pCart = shoeCartList.get(position);

        String quantityString = String.valueOf(pCart.getQuantity());

        Product shoeCart = getProductByID(pCart);

        int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(shoeCart.getImage(), "drawable", holder.itemView.getContext().getPackageName());
        Glide.with(context)
                .load(shoeCart.getImage())
                .into(holder.shoeImageView);

        holder.shoeNameTv.setText(shoeCart.getName());
        holder.shoeBrandNameTv.setText(shoeCart.getBrand());
        holder.shoeQuantity.setText(quantityString);
        holder.shoePriceTv.setText(String.valueOf(shoeCart.getPrice()));
    }

    @Override
    public int getItemCount() {
        if (shoeCartList == null) {
            return 0;
        } else {
            return shoeCartList.size();
        }
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {

        private TextView shoeNameTv, shoeBrandNameTv, shoePriceTv, shoeQuantity;
        private ImageView shoeImageView;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);

            shoeNameTv = itemView.findViewById(R.id.eachCartItemName);
            shoeBrandNameTv = itemView.findViewById(R.id.eachCartItemBrandNameTv);
            shoePriceTv = itemView.findViewById(R.id.eachCartItemPriceTv);
            shoeImageView = itemView.findViewById(R.id.eachCartItemIV);
            shoeQuantity = itemView.findViewById(R.id.eachCartItemQuantityTV);
        }
    }
}
