package com.Murok.inventoryapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.Murok.inventoryapp.database.MainDatabase;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.InventoryViewHolder>
{
    private List<Item> products;
    private OnItemListener mOnItemListener;
    private MainDatabase mainDatabase;
    private static final String LOG_TAG = "ItemAdapter";


    public ItemAdapter(List<Item> products , OnItemListener onItemListener , MainDatabase mainDatabase , Context context )
    {
        this.products = products;
        this.mOnItemListener = onItemListener;
        this.mainDatabase = mainDatabase;

    }

    @Override
    public ItemAdapter.InventoryViewHolder onCreateViewHolder(ViewGroup parent, int i)
    {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        return new InventoryViewHolder( itemView, mOnItemListener );
    }

    @Override
    public void onBindViewHolder(ItemAdapter.InventoryViewHolder bookViewHolder, int i)
    {
        String productName = products.get(i).getName();
        bookViewHolder.product_name.setText(productName);

        int productQuantity = products.get(i).getQuantity();
        bookViewHolder.product_quantity.setText(productQuantity + " Available");

        double productPrice = products.get(i).getPrice();
        bookViewHolder.product_price.setText( "$" + productPrice );

        String rating = products.get(i).getRating();
        bookViewHolder.product_rating.setText("Rating " + rating);



    }

    @Override
    public int getItemCount() {
        return products!=null ? products.size() : 0;
    }

    public class InventoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public TextView product_name;
        public TextView product_quantity;
        public TextView product_price;
        public TextView product_rating;


        OnItemListener onItemListener;

        public InventoryViewHolder(View view, OnItemListener onItemListener) {
            super(view);
            product_name = view.findViewById(R.id.name_tag);
            product_quantity = view.findViewById(R.id.availability_tag);
            product_price = view.findViewById(R.id.price_tag);
            product_rating = view.findViewById(R.id.rating_text);


            this.onItemListener = onItemListener;

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onItemListener.onItemClick(getAdapterPosition());
        }
    }


    public interface OnItemListener {
        void onItemClick (int i);
    }

}

