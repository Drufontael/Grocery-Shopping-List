package tech.drufontael.marketlist.UI;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Collections;
import java.util.Locale;

import tech.drufontael.marketlist.R;
import tech.drufontael.marketlist.data.listener.OnGoodEditListener;
import tech.drufontael.marketlist.data.model.Good;


public class MainActivity extends AppCompatActivity {

    private static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    private GoodsListViewModel mGoodsListViewModel;
    private final ViewHolder mViewHolder=new ViewHolder();

    private final OnGoodEditListener goodEditListener = new OnGoodEditListener() {
        @Override
        public void onEdit(int id, Good good) {
            mGoodsListViewModel.update(id, good);
            adapter.notifyDataSetChanged();
            double totalPriceValue = mGoodsListViewModel.goodsList.getValue().getTotalPrice();
            String price = String.format(Locale.getDefault(), "%s %.2f", getString(R.string.currency), totalPriceValue);
            mViewHolder.mTextTotalPrice.setText(price);
        }

        @Override
        public void remove(int id) {
            mGoodsListViewModel.delete(id);
            mGoodsListViewModel.getList();
            adapter.notifyDataSetChanged();
        }
    };
    final GoodsListAdapter adapter = new GoodsListAdapter(new GoodsListAdapter.GoodDiff(), goodEditListener);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recicle_view);


        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mViewHolder.mTextTotalPrice = findViewById(R.id.text_total_price);
        mViewHolder.mImageCart=findViewById(R.id.image_cart);
        mViewHolder.mTextListNameShow=findViewById(R.id.text_list_name_show);

        mGoodsListViewModel = new ViewModelProvider(this).get(GoodsListViewModel.class);
        mGoodsListViewModel.goodsList.observe(this, goodsList -> {

            Collections.sort(goodsList.getGoods());
            adapter.submitList(goodsList.getGoods());
            String price = String.format(Locale.getDefault(), "%s %.2f", getString(R.string.currency),
                    goodsList.getTotalPrice());
            mViewHolder.mTextTotalPrice.setText(price);
            mViewHolder.mTextListNameShow.setText(goodsList.getListName());
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this,AddGoodActivity.class);
           startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
        });

        mViewHolder.mImageCart.setOnClickListener(view->{
            Intent intent=new Intent(MainActivity.this,LoadListActivity.class);
            startActivity(intent);
        });

        mViewHolder.mImageCart.setOnLongClickListener(view->{
            EditText nameList=new EditText(this);
            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            nameList.setLayoutParams(layoutParams);
            new AlertDialog.Builder(this)
                    .setTitle("Salvar Nova Lista")
                    .setMessage("Digite um nome para a lista")
                    .setView(nameList)
                    .setCancelable(false)
                    .setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mGoodsListViewModel.saveList(nameList.getText().toString());
                            mGoodsListViewModel.loadList(nameList.getText().toString());
                            mGoodsListViewModel.getList();
                        }
                    })
                    .setNeutralButton("Cancelar",null)
                    .show();
            return false;
        });

    }

    private static class ViewHolder{
        TextView mTextTotalPrice;
        TextView mTextListNameShow;
        ImageView mImageCart;

    }

    @Override
    protected void onResume() {
        super.onResume();
        mGoodsListViewModel.getList();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Good newGood = new Good(data.getStringExtra("goodName"),data.getDoubleExtra("price",0.0),data.getIntExtra("quantity",0));
            mGoodsListViewModel.insert(newGood);
            adapter.notifyDataSetChanged();
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_IMMERSIVE
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            // Esconde nav bar e status bar
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
            );
        }
    }

}