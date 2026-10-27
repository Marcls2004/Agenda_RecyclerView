package com.MarcL.agenda_recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    private List<Contact> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    ContactAdapter(Context context, List<Contact> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_row, parent, false);
        // Le pasamos el listener actual al crear el ViewHolder
        return new ViewHolder(view, mClickListener);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Contact contacto = mData.get(position);
        holder.tvContactName.setText(contacto.getName());
        holder.tvContactPhone.setText(contacto.getPhone());
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvContactName;
        TextView tvContactPhone;
        // Guardamos una referencia local al listener
        private final ItemClickListener listener;

        ViewHolder(View itemView, ItemClickListener listener) {
            super(itemView);
            this.listener = listener;
            tvContactName = itemView.findViewById(R.id.tvContactName);
            tvContactPhone = itemView.findViewById(R.id.tvContactPhone);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            // Ahora usamos el listener local de la clase estática
            if (listener != null) {
                listener.onItemClick(view, getAbsoluteAdapterPosition());
            }
        }
    }

    // convenience method for getting data at click position
    Contact getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
