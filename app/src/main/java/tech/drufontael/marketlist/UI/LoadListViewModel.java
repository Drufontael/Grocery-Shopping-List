package tech.drufontael.marketlist.UI;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import tech.drufontael.marketlist.data.model.SavedList;
import tech.drufontael.marketlist.data.repository.GoodsListRepository;

public class LoadListViewModel extends AndroidViewModel {

    private GoodsListRepository mRepository;
    private MutableLiveData<List<SavedList>> mSavedLists=new MutableLiveData<>();
    public LiveData<List<SavedList>> savedLists=mSavedLists;


    public LoadListViewModel(@NonNull Application application) {
        super(application);
        this.mRepository=new GoodsListRepository(application);
    }

    public void getList(){
        mSavedLists.setValue(mRepository.getSavedLists());
    }
}
