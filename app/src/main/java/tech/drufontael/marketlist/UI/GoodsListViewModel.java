package tech.drufontael.marketlist.UI;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import tech.drufontael.marketlist.data.entities.Good;
import tech.drufontael.marketlist.data.repository.GoodsListRepository;

public class GoodsListViewModel extends AndroidViewModel {
    private final GoodsListRepository repository;
    private final LiveData<List<Good>> list;
    public GoodsListViewModel(@NonNull Application application) {
        super(application);
        repository=new GoodsListRepository(application);
        list= repository.getList();
    }

    public LiveData<List<Good>> getList(){return list;}

    public void insert(Good good){
        repository.insertGood(good);
    }

    public void update(int id, Good good){
        repository.updateGood(id,good);
    }

    public double calculateTotalPrice() {
        return this.list.getValue().stream().filter(Good::isActive)
                .mapToDouble(Good::getTotalItemPrice).sum();
    }
}
