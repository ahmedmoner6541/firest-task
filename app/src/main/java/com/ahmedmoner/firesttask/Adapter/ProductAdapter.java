package com.ahmedmoner.firesttask.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.ahmedmoner.firesttask.Model.Products;
import com.ahmedmoner.firesttask.R;
import com.squareup.picasso.Picasso;

import java.util.List;


public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewAllVH>
          {


    Context context;
    List<Products> productsList;


    public ProductAdapter(Context context, List<Products> productsList) {
        this.context = context;
        this.productsList = productsList;
    }

    @NonNull
    @Override
    public ViewAllVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ViewAllVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ViewAllVH holder, int position) {


        /////////////////////////

        Picasso.get().load(productsList.get(position).getImage()).into(holder.iv_product);

        holder.tv_name_product.setText(productsList.get(position).getName());
        holder.tv_discound_product.setText(productsList.get(position).getPercentDiscount());
        holder.tv_discription_product.setText(productsList.get(position).getDescription());

        holder.tv_price_product.setText(productsList.get(position).getPrice() + "");
        holder.tv_oldPrice_product.setText(productsList.get(position).getOldPrice() + "");
        holder.tv_ratingBar_product.setText(productsList.get(position).getRating() + "");

        holder.ratingBar_product.setRating(Float.parseFloat(String.valueOf(productsList.get(position).getRating())));


    }


    @Override
    public int getItemCount() {
        return productsList.size();
    }

    public class ViewAllVH extends RecyclerView.ViewHolder {

        ImageView iv_product;

        TextView tv_name_product, tv_discound_product, tv_discription_product, tv_price_product, tv_oldPrice_product, tv_ratingBar_product;
        RatingBar ratingBar_product;



        public ViewAllVH(@NonNull View itemView) {
            super(itemView);

            iv_product = itemView.findViewById(R.id.iv_product_item);


            tv_name_product = itemView.findViewById(R.id.name_product_item);
            tv_discound_product = itemView.findViewById(R.id.discound_product_item);

            tv_discription_product = itemView.findViewById(R.id.description_product_item);
            tv_price_product = itemView.findViewById(R.id.price_product_item);

            tv_oldPrice_product = itemView.findViewById(R.id.oldPrice_product_item);
            tv_ratingBar_product = itemView.findViewById(R.id.tv_rating_product_item);
            ratingBar_product = itemView.findViewById(R.id.rating_product_item);


        }
    }



}