package tech.drufontael.marketlist.UI;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import tech.drufontael.marketlist.R;
import tech.drufontael.marketlist.data.listener.OnSavedListAction;
import tech.drufontael.marketlist.data.model.SavedList;

public class SavedListViewHold extends RecyclerView.ViewHolder {

    private final TextView mTextListName;
    private final TextView mTextDate;
    private final FloatingActionButton mBtnLoadList;
    private final FloatingActionButton mBtnClearLis;

    public SavedListViewHold(@NonNull View itemView) {
        super(itemView);
        mTextListName = itemView.findViewById(R.id.text_list_name);
        mTextDate = itemView.findViewById(R.id.text_date);
        mBtnLoadList = itemView.findViewById(R.id.btn_load_list);
        mBtnClearLis = itemView.findViewById(R.id.btn_clear_list);

    }

    public void bind(SavedList list, OnSavedListAction listener) {
        mTextListName.setText(list.getName());
        mTextDate.setText(list.getDate());

        mBtnLoadList.setOnClickListener(v -> {
            listener.load(list.getName());
        });

        mBtnClearLis.setOnClickListener(v -> {
            new AlertDialog.Builder(itemView.getContext())
                    .setTitle(R.string.remover_mercadoria)
                    .setMessage(R.string.deseja_realmente_remover_lista)
                    .setPositiveButton(R.string.sim, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            listener.remove(list.getName());
                        }
                    })
                    .setNeutralButton(R.string.nao, null)
                    .show();
        });


    }

    public static SavedListViewHold create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.saved_list, parent, false);
        return new SavedListViewHold(view);
    }
}
