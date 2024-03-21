package tech.drufontael.marketlist.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Locale;

import tech.drufontael.marketlist.R;
import tech.drufontael.marketlist.data.entities.Good;
import tech.drufontael.marketlist.data.listener.OnGoodEditListener;


public class MainActivity extends AppCompatActivity {

    private static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    private GoodsListViewModel mGoodsListViewModel;
    private final ViewHolder mViewHolder=new ViewHolder();

    private final OnGoodEditListener goodEditListener = new OnGoodEditListener() {
        @Override
        public void onEdit(int id, Good good) {
            mGoodsListViewModel.update(id, good);
            adapter.notifyDataSetChanged();
            double totalPriceValue = mGoodsListViewModel.calculateTotalPrice();
            String price = String.format(Locale.getDefault(), "%s %.2f", getString(R.string.currency), totalPriceValue);
            mViewHolder.mtextTotalPrice.setText(price);
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

        mViewHolder.mtextTotalPrice = findViewById(R.id.text_total_price);

        mGoodsListViewModel = new ViewModelProvider(this).get(GoodsListViewModel.class);
        mGoodsListViewModel.getList().observe(this, goodsList -> {

            //Collections.sort(goodsList);
            adapter.submitList(goodsList);
            String price = String.format(Locale.getDefault(), "%s %.2f", getString(R.string.currency),
                    mGoodsListViewModel.calculateTotalPrice());
            mViewHolder.mtextTotalPrice.setText(price);
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this,AddGoodActivity.class);
           startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
        });

    }

    private static class ViewHolder{
        TextView mtextTotalPrice;
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