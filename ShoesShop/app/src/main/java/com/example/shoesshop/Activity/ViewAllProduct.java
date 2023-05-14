package com.example.shoesshop.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import androidx.appcompat.widget.SearchView;
import android.widget.Toast;


import com.example.shoesshop.Adapter.SearchItemAdapter;
import com.example.shoesshop.Model.Product;
import com.example.shoesshop.Model.User;
import com.example.shoesshop.R;
import com.example.shoesshop.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;


public class ViewAllProduct extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Product> productList;
    private SearchItemAdapter searchItemAdapter;
    private SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_product);

        List<Product> productInShop = new ArrayList<>();

        User user = SharedPrefManager.getInstance(getApplicationContext()).getUser();

        productList = SharedPrefManager.getInstance(getApplicationContext()).getData();

        for (Product p  : productList){
            if (p.getShopId().equals(user.getId())){
                productInShop.add(p);
            }
        }

        searchView = findViewById(R.id.searchView);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText,productInShop);
                return true;
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        searchItemAdapter = new SearchItemAdapter(productInShop, ViewAllProduct.this);
        recyclerView.setAdapter(searchItemAdapter);
    }


    private void filterList(String text,List<Product> productInShop){
        List<Product> filteredListProduct = new ArrayList<>();
        for (Product p : productInShop){
            if (p.getName().toLowerCase().contains(text.toLowerCase())){
                filteredListProduct.add(p);
            }
        }
        if (filteredListProduct.isEmpty()){
            Toast.makeText(this, "Không có sản phẩm cần tìm", Toast.LENGTH_SHORT).show();
        }
        else {
            if(searchItemAdapter != null) {
                searchItemAdapter.setFilteredList(filteredListProduct);
            }
        }
    }
}