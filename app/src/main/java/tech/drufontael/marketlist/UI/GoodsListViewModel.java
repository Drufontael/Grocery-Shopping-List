package tech.drufontael.marketlist.UI;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import tech.drufontael.marketlist.data.model.Good;
import tech.drufontael.marketlist.data.model.GoodsList;
import tech.drufontael.marketlist.data.repository.GoodsListRepository;

public class GoodsListViewModel extends AndroidViewModel {
    private final GoodsListRepository repository;
    private MutableLiveData<GoodsList> mGoodsList=new MutableLiveData<>();
    public final LiveData<GoodsList> goodsList=mGoodsList;
    public GoodsListViewModel(@NonNull Application application) {
        super(application);
        repository=new GoodsListRepository(application);

    }

    public void getList(){
        mGoodsList.setValue(repository.getList());
    }

    public void insert(Good good){
        repository.insertGood(good);
    }

    public void update(int id, Good good){
        repository.updateGood(id,good);
    }


    public void delete(int id){
        repository.remove(id);
    }
}
