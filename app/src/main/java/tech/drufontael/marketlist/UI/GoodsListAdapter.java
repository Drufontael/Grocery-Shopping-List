package tech.drufontael.marketlist.UI;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import tech.drufontael.marketlist.data.entities.Good;
import tech.drufontael.marketlist.data.listener.OnGoodEditListener;

public class GoodsListAdapter extends ListAdapter<Good,GoodViewHolder> {

    private final OnGoodEditListener goodEditListener;


    protected GoodsListAdapter(@NonNull DiffUtil.ItemCallback<Good> diffCallback, OnGoodEditListener goodEditListener) {
        super(diffCallback);
        this.goodEditListener=goodEditListener;

    }

    @NonNull
    @Override
    public GoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return GoodViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull GoodViewHolder holder, int position) {
        Good current=getItem(position);
        holder.bind(current,goodEditListener);
    }

    public static class GoodDiff extends DiffUtil.ItemCallback<Good>{

        @Override
        public boolean areItemsTheSame(@NonNull Good oldItem, @NonNull Good newItem) {
            return oldItem==newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Good oldItem, @NonNull Good newItem) {
            return oldItem.getGoodName().equals(newItem.getGoodName());
        }
    }
}
