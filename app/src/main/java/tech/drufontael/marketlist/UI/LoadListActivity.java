package tech.drufontael.marketlist.UI;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import tech.drufontael.marketlist.R;
import tech.drufontael.marketlist.data.listener.OnSavedListAction;

public class LoadListActivity extends AppCompatActivity {

    private RecyclerView mRecycleViewSavedLists;
    private SavedListAdapter savedListAdapter=new SavedListAdapter();
    private LoadListViewModel viewModel;
    private final OnSavedListAction listener=new OnSavedListAction() {
        @Override
        public void load(String name) {
            viewModel.loadList(name);
            finish();
        }

        @Override
        public void remove(String name) {
            viewModel.deleteList(name);
            viewModel.getSavedLists();
        }
    };

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

        listeners();

    }

    private void listeners() {
        savedListAdapter.attachListener(listener);

    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.getSavedLists();
    }
}