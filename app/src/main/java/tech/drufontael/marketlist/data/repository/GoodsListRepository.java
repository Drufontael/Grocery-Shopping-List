package tech.drufontael.marketlist.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import tech.drufontael.marketlist.data.dao.GoodsListDao;
import tech.drufontael.marketlist.data.model.Good;
import tech.drufontael.marketlist.data.model.GoodsList;
import tech.drufontael.marketlist.data.model.SavedList;

public class GoodsListRepository {
    private final GoodsListDao dao;
    private LiveData<GoodsList> list;

    public GoodsListRepository(Application application) {
        GoodsListDb db = GoodsListDb.getInstance(application);
        dao = db.dao;
    }

    public GoodsList loadList() {
        return dao.loadList(null);
    }
    public GoodsList loadList(String name) {
       return dao.loadList(name);
    }

    public void saveList(String name) {
        dao.saveList(name);
    }

    public void deleteList(String name){
        dao.deleteList(name);
    }

    public void insertGood(Good good) {
        GoodsListDb.databaseWriteExecutor.execute(() -> {
            dao.addGood(good);
        });

    }

    public void updateGood(int id, Good good) {

        GoodsListDb.databaseWriteExecutor.execute(() -> {
            dao.updateGood(id, good);
        });

    }

    public void remove(int id) {
        dao.deleteGood(id);
    }

    public List<SavedList> getSavedLists() {
        return dao.showLists();
    }



}
