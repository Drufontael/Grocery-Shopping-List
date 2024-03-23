package tech.drufontael.marketlist.data.dao;

import java.util.List;

import tech.drufontael.marketlist.data.model.Good;
import tech.drufontael.marketlist.data.model.GoodsList;
import tech.drufontael.marketlist.data.model.SavedList;

public interface GoodsListDao {
    GoodsList loadList(String name);
    void saveList(String name);
    void addGood(Good good);
    void deleteGood(int id);
    void updateGood(int id,Good good);
    void deleteList(String name);
    List<SavedList> showLists();


}
