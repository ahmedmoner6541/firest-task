
package com.ahmedmoner.firesttask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ahmedmoner.firesttask.Adapter.ProductAdapter;
import com.ahmedmoner.firesttask.Model.Products;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ViewProductActivity extends AppCompatActivity {

    List<Products> productsList;
    ProductAdapter adapter;
    FirebaseFirestore db;
    RecyclerView rv_product;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = FirebaseFirestore.getInstance();

        progressBar =  findViewById(R.id.progressBar);

        rv_product = findViewById(R.id.recycler_menu);
        productsList = new ArrayList<>();
        progressBar.setVisibility(View.VISIBLE);
        rv_product.setLayoutManager(new GridLayoutManager(this, 2));



        adapter = new ProductAdapter(this, productsList);
        rv_product.setAdapter(adapter);
        db.collection("Products")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Products products = document.toObject(Products.class);
                                productsList.add(products);
                                adapter.notifyDataSetChanged();
                                progressBar.setVisibility(View.GONE);




                            }
                        } else {

                            Toast.makeText(ViewProductActivity.this, "error ", Toast.LENGTH_SHORT).show();
                             Log.w("TAG", "Error getting documents.", task.getException());
                         }
                    }
                });

    }
}