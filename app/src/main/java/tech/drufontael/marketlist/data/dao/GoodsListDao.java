package tech.drufontael.marketlist.data.dao;

import androidx.lifecycle.LiveData;

import java.util.List;

import tech.drufontael.marketlist.data.entities.Good;

public interface GoodsListDao {
    LiveData<List<Good>> loadList(String name);
    void saveList(String name, List<Good> goods);
    void addGood(Good good);
    void deleteGood(int id);
    void updateGood(int id,Good good);
    void deleteList(String name);


}
