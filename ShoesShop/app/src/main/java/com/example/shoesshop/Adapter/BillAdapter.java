package com.example.shoesshop.Adapter;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoesshop.Activity.BillDetailActivity;
import com.example.shoesshop.Model.Order;
import com.example.shoesshop.Model.Product;
import com.example.shoesshop.Model.ProductInCart;
import com.example.shoesshop.R;
import com.example.shoesshop.SharedPrefManager;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.BillHolder> {

    private Context mContext;
    private List<Order> mListOrder;

    public BillAdapter(Context mContext, List<Order> mListOrder) {
        this.mContext = mContext;
        this.mListOrder = mListOrder;
    }

    @Override
    public BillHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.each_item_bill, parent, false);
        return new BillHolder(view);
    }

    @Override
    public void onBindViewHolder(BillHolder holder, int position) {
        Order order = mListOrder.get(position);

        Integer totalPrice = 0;
        List<Product> productList = SharedPrefManager.getInstance(mContext.getApplicationContext()).getData();


        //FORMAT DATE
        String isoTime = order.getDateOrdered();
        SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        isoFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = null;
        try {
            date = isoFormat.parse(isoTime);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        SimpleDateFormat vnFormat = new SimpleDateFormat("dd/MM/yyyy");
        vnFormat.setTimeZone(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
        String vnDate = vnFormat.format(date);

        // FIND TOTALPRICE
        for (Product p : productList) {
            for (ProductInCart pCart : order.getOrderItems()) {
                if (p.getId().equals(pCart.getProductId())) {
                    totalPrice += pCart.getQuantity() * p.getPrice();
                    break;
                }
            }
        }
        DecimalFormat decimalFormat = new DecimalFormat("#,###.##");
        String formattedTotalPrice = decimalFormat.format(totalPrice);

        holder.customerNameTv.setText(order.getName());
        holder.phoneNumberTv.setText(order.getPhone());
        holder.dateTv.setText(vnDate);
        holder.totalAmountTv.setText(formattedTotalPrice + " đ");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, BillDetailActivity.class);
                intent.putExtra("id", order.getId());
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        if(mListOrder == null){
            return 0;
        }else {
            return mListOrder.size();
        }
    }

    public class BillHolder extends RecyclerView.ViewHolder{

        TextView customerNameTv, phoneNumberTv, totalAmountTv, dateTv;
        View divider;

        public BillHolder(View itemView) {
            super(itemView);

            customerNameTv = (TextView) itemView.findViewById(R.id.bill_customer_name);
            phoneNumberTv = (TextView) itemView.findViewById(R.id.bill_phone_number);
            totalAmountTv = (TextView) itemView.findViewById(R.id.bill_total_amount);
            dateTv = (TextView) itemView.findViewById(R.id.bill_date);
            divider = itemView.findViewById(R.id.bill_divider);

        }
    }
}
