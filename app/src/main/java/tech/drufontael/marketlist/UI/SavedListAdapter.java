package tech.drufontael.marketlist.UI;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import tech.drufontael.marketlist.data.listener.OnSavedListAction;
import tech.drufontael.marketlist.data.model.SavedList;

public class SavedListAdapter extends RecyclerView.Adapter<SavedListViewHold> {

    private List<SavedList> mList=new ArrayList<>();
    private OnSavedListAction listener;
    @NonNull
    @Override
    public SavedListViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return SavedListViewHold.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull SavedListViewHold holder, int position) {
        holder.bind(mList.get(position),listener);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void attachList(List<SavedList> list){
        this.mList=list;
        notifyDataSetChanged();
    }

    public void attachListener(OnSavedListAction listener) {
        this.listener=listener;

    }
}
