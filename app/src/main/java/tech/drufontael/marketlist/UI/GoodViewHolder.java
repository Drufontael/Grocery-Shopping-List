package tech.drufontael.marketlist.UI;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Locale;

import tech.drufontael.marketlist.R;
import tech.drufontael.marketlist.data.entities.Good;
import tech.drufontael.marketlist.data.listener.OnGoodEditListener;

public class GoodViewHolder extends RecyclerView.ViewHolder {

    private final CheckBox mGoodName;
    private final TextView mGoodTotalPrice;
    private final EditText mPrice;
    private final EditText mQuantity;

    public GoodViewHolder(@NonNull View itemView) {
        super(itemView);
        this.mGoodName=itemView.findViewById(R.id.text_good_name);
        this.mGoodTotalPrice=itemView.findViewById(R.id.text_good_total_price);
        this.mPrice=itemView.findViewById(R.id.edit_price);
        this.mQuantity=itemView.findViewById(R.id.edit_quantity);
    }

    public void bind(Good good, OnGoodEditListener goodEditListener){

        this.mGoodName.setText(good.getGoodName());
        this.mGoodName.setChecked(good.isActive());
        String price=String.format(Locale.US,"%.2f",
                good.getPrice());
        this.mPrice.setText(price);
        this.mQuantity.setText(String.valueOf(good.getQuantity()));
        String totalPrice=String.format(Locale.US,"%s %.2f",itemView.getContext().getString(R.string.currency),
                good.getTotalItemPrice());
        this.mGoodTotalPrice.setText(totalPrice);

        this.mPrice.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE){
                good.setPrice(Double.parseDouble(mPrice.getText().toString()));
                goodEditListener.onEdit(good.getId(),good);
            }
            return false;
        });
        this.mQuantity.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE){
                good.setQuantity(Integer.parseInt(mQuantity.getText().toString()));
                goodEditListener.onEdit(good.getId(),good);
            }
            return false;
        });
        this.mGoodName.setOnClickListener(v -> {
            CheckBox checkBox=v.findViewById(R.id.text_good_name);
            good.setActive(checkBox.isChecked());
            goodEditListener.onEdit(good.getId(),good);
        });
        this.mGoodName.setOnLongClickListener(v -> {
            new AlertDialog.Builder(itemView.getContext())
                    .setTitle(R.string.remover_mercadoria)
                    .setMessage(R.string.msg_remover_mercadoria)
                    .setPositiveButton(R.string.sim, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            goodEditListener.remove(good.getId());
                        }
                    })
                    .setNeutralButton(R.string.nao,null)
                    .show();
            return false;

        });
    }

    static GoodViewHolder create(ViewGroup parent){
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.good,parent,false);
        return new GoodViewHolder(view);
    }
}
