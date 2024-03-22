package tech.drufontael.marketlist.data.dao;

import java.util.List;

import tech.drufontael.marketlist.data.entities.Good;
import tech.drufontael.marketlist.data.entities.GoodsList;

public interface GoodsListDao {
    GoodsList loadList(String name);
    void saveList(String name, List<Good> goods);
    void addGood(Good good);
    void deleteGood(int id);
    void updateGood(int id,Good good);
    void deleteList(String name);


}
