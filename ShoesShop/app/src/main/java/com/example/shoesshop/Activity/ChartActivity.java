package com.example.shoesshop.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shoesshop.Model.Order;
import com.example.shoesshop.Model.Product;
import com.example.shoesshop.Model.ProductInCart;
import com.example.shoesshop.R;
import com.example.shoesshop.SharedPrefManager;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        PieChart pieChart = findViewById(R.id.piechart);
        ImageView imvBack = findViewById(R.id.back);
        TextView total_revenue_textview = findViewById(R.id.total_revenue_textview);

        ArrayList<PieEntry> visitors = new ArrayList<>();

        Integer totalPrice = 0;
        List<Order> listOrderShop = SharedPrefManager.getInstance(getApplicationContext()).getOrderData();
        List<Product> productList = SharedPrefManager.getInstance(getApplicationContext()).getData();

        // Tạo một Map để lưu trữ thông tin doanh thu của từng sản phẩm
        Map<String, Integer> productRevenueMap = new HashMap<>();

        for (Order o : listOrderShop) {
            for (Product p : productList) {
                for (ProductInCart pCart : o.getOrderItems()) {
                    if (p.getId().equals(pCart.getProductId())) {
                        int revenue = pCart.getQuantity() * p.getPrice();
                        totalPrice += revenue;

                        // Lưu thông tin doanh thu của sản phẩm vào Map
                        if (productRevenueMap.containsKey(p.getId())) {
                            int currentRevenue = productRevenueMap.get(p.getId());
                            productRevenueMap.put(p.getId(), currentRevenue + revenue);
                        } else {
                            productRevenueMap.put(p.getId(), revenue);
                        }
                        break;
                    }
                }
            }
        }

        // In thông tin doanh thu của từng sản phẩm
        for (Product p : productList) {
            if (productRevenueMap.containsKey(p.getId())) {
                int revenue = productRevenueMap.get(p.getId());
//                System.out.println("Doanh thu của sản phẩm " + p.getName() + ": " + revenue);
                visitors.add(new PieEntry(revenue, p.getName()));
            }
        }

        PieDataSet pieDataSet = new PieDataSet(visitors, "Visitors");
        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(16f);

        PieData pieData = new PieData(pieDataSet);

        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterText("Doanh thu");
        pieChart.animate();

        imvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        DecimalFormat decimalFormat = new DecimalFormat("#,###.##");
        String formattedTotalPrice = decimalFormat.format(getTotalPrice(listOrderShop));
        total_revenue_textview.setText("Tổng tiền: " + formattedTotalPrice +"đ");
    }

    private Integer getTotalPrice(List<Order> listOrder){
        Integer totalPrice = 0;
        List<Product> productList = SharedPrefManager.getInstance(getApplicationContext()).getData();

        for (Order o : listOrder){
            for (Product p : productList) {
                for (ProductInCart pCart : o.getOrderItems()) {
                    if (p.getId().equals(pCart.getProductId())) {
                        totalPrice += pCart.getQuantity() * p.getPrice();
                        break;
                    }
                }
            }
        }
        return totalPrice;
    }
}