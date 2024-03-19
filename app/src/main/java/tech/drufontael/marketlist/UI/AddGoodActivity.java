package tech.drufontael.marketlist.UI;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import tech.drufontael.marketlist.R;

public class AddGoodActivity extends AppCompatActivity {
    private final ViewHolder mViewHolder=new ViewHolder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_good);

        mViewHolder.mEditGoodName=findViewById(R.id.edit_good_name);
        mViewHolder.mEditNewGoodPrice=findViewById(R.id.edit_new_good_price);
        mViewHolder.mEditNewGoodQuantity=findViewById(R.id.edit_new_good_quantity);
        mViewHolder.mBtnAddGood=findViewById(R.id.btn_add_good);
        mViewHolder.mBtnCancel=findViewById(R.id.btn_cancel);

        mViewHolder.mBtnAddGood.setOnClickListener(view ->{
            Intent replayIntent=new Intent();
            if(TextUtils.isEmpty(mViewHolder.mEditGoodName.getText())){
                setResult(RESULT_CANCELED,replayIntent);
            }else {
                if (TextUtils.isEmpty(mViewHolder.mEditNewGoodPrice.getText()))
                    mViewHolder.mEditNewGoodPrice.setText("0.0");
                if (TextUtils.isEmpty(mViewHolder.mEditNewGoodQuantity.getText()))
                    mViewHolder.mEditNewGoodQuantity.setText("0");
                Bundle bundle=new Bundle();
                bundle.putString("goodName",mViewHolder.mEditGoodName.getText().toString());
                bundle.putDouble("price",Double.parseDouble(mViewHolder.mEditNewGoodPrice.getText().toString()));
                bundle.putInt("quantity",Integer.parseInt(mViewHolder.mEditNewGoodQuantity.getText().toString()));
                replayIntent.putExtras(bundle);
                setResult(RESULT_OK,replayIntent);
            }
            finish();
        });

        mViewHolder.mBtnCancel.setOnClickListener(v -> finish());

    }

    private static class ViewHolder{
        EditText mEditGoodName;
        EditText mEditNewGoodPrice;
        EditText mEditNewGoodQuantity;
        Button mBtnAddGood;
        Button mBtnCancel;
    }
}