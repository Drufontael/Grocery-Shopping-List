package tech.drufontael.marketlist.UI;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import tech.drufontael.marketlist.R;

public class LoadListActivity extends AppCompatActivity {

    private RecyclerView mRecycleViewSavedLists;
    private SavedListAdapter savedListAdapter=new SavedListAdapter();
    private LoadListViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_list);

        viewModel=new ViewModelProvider(this).get(LoadListViewModel.class);

        viewModel.savedLists.observe(this,savedLists -> {
            savedListAdapter.attachList(savedLists);
        });

        mRecycleViewSavedLists=findViewById(R.id.recicle_view_load_list);
        mRecycleViewSavedLists.setLayoutManager(new LinearLayoutManager(this));
        mRecycleViewSavedLists.setAdapter(savedListAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.getList();
    }
}