package tech.drufontael.marketlist.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import tech.drufontael.marketlist.data.dao.GoodsListDao;
import tech.drufontael.marketlist.data.entities.Good;
import tech.drufontael.marketlist.data.entities.GoodsList;

public class GoodsListRepository {
    private final GoodsListDao dao;
    private LiveData<GoodsList> list;

    public GoodsListRepository(Application application){
        GoodsListDb db=GoodsListDb.getInstance(application);
        dao= db.dao;
    }

    public LiveData<List<Good>> getList(){
        return dao.loadList("padrao");
    }

    public void insertGood(Good good){
        GoodsListDb.databaseWriteExecutor.execute(()->{
            dao.addGood(good);
        });

    }

    public void updateGood(int id,Good good){


        GoodsListDb.databaseWriteExecutor.execute(()->{
        dao.updateGood(id,good);
        });

    }

}
