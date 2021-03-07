package com.example.minerstats.Minero;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.minerstats.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MineroAdapter extends RecyclerView.Adapter<MineroAdapter.MineroViewHolder> {

    private final ArrayList<Minero> mMineros;
    private Context mContext;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Minero minero);
    }

    // Adapter pels mineros
    public MineroAdapter(Context context, ArrayList<Minero> myDataset, OnItemClickListener listener) {
        mMineros = myDataset;
        mContext = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MineroAdapter.MineroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_minero_adapter, parent, false);
        MineroViewHolder vh = new MineroViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(MineroViewHolder holder, int position) {
        holder.bindMinero(mMineros.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return mMineros.size();
    }

    public void eliminaMinero(int position) {
        mMineros.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mMineros.size());
    }

    public void retornaMinero(Minero minero, int position) {
        mMineros.add(position, minero);
        // notify item added by position
        notifyItemInserted(position);
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MineroViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        @BindView(R.id.nomMinero)
        TextView mNom;
        @BindView(R.id.qtyGPU)
        TextView mQtyGPU;
        @BindView(R.id.crypto)
        TextView mCrypto;
        @BindView(R.id.ipMinero)
        TextView mIp;

        private Context mContext;

        public MineroViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            mContext = itemView.getContext();
            itemView.setTag(this);
        }

        public void bindMinero(final Minero minero, final OnItemClickListener listener) {
            mNom.setText(minero.getNom());
            mQtyGPU.setText(""+minero.getQtyGPU());
            mCrypto.setText(minero.getId_crypto());
            mIp.setText(minero.getIp_minero());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(minero);
                }
            });
        }
    }
}