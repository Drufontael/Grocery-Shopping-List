package tech.drufontael.marketlist.data.repository;

import android.content.Context;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import tech.drufontael.marketlist.data.dao.GoodsListDao;
import tech.drufontael.marketlist.data.model.Good;
import tech.drufontael.marketlist.data.model.GoodsList;
import tech.drufontael.marketlist.data.model.SavedList;
import tech.drufontael.marketlist.util.GoodsListFile;

public class GoodsListDb {

    private static volatile GoodsListDb INSTANCE;
    private static final String FILE_NAME = "goodslists_v2.json";
    private static String ATUAL_LIST="padrao";
    private static List<GoodsList> mMainList;
    private static GoodsList inUse;
    private static Context CONTEXT;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    GoodsListDao dao = new GoodsListDao() {
        @Override
        public GoodsList loadList(String name) {
            if(name!=null) ATUAL_LIST=name;
            inUse = mMainList.stream()
                    .filter(x -> x.getListName().equals(ATUAL_LIST))
                    .findFirst().orElse(getPadrao().get(0));
            return inUse;
        }

        @Override
        public void saveList(String name) {
            if (mMainList.stream().noneMatch(x -> x.getListName().equals(name))) {
                List<Good> goods = new ArrayList<>(inUse.getGoods());
                GoodsList newList = new GoodsList(name, goods);
                mMainList.add(newList);
            }
            saveGoodsList();
        }

        @Override
        public void addGood(Good good) {
            inUse.addGood(good);
            Collections.sort(inUse.getGoods());
            saveGoodsList();
        }

        @Override
        public void deleteGood(int id) {
            List<Good> goods = inUse.getGoods();
            for (Good g : goods) {
                if (g.getId() == id) {
                    goods.remove(g);
                    break;
                }
            }
            saveGoodsList();
        }

        @Override
        public void updateGood(int id, Good good) {
            List<Good> goods = inUse.getGoods();
            for (Good g : goods) {
                if (g.getId() == id) {
                    g.setPrice(good.getPrice());
                    g.setQuantity(good.getQuantity());
                    g.setActive(good.isActive());
                    break;
                }

            }
            saveGoodsList();
            loadList(ATUAL_LIST);
        }

        @Override
        public void deleteList(String name) {
            if (!inUse.getListName().equals(name)) {
                for (GoodsList l : mMainList) {
                    if (l.getListName().equals(name)) {
                        mMainList.remove(l);
                        break;
                    }
                }
            }
            saveGoodsList();
        }

        @Override
        public List<SavedList> showLists() {
            List<SavedList> savedLists = new ArrayList<>();
            for (GoodsList l : mMainList) {
                savedLists.add(new SavedList(l.getListName(), l.getDate()));
            }
            return savedLists;

        }
    };


    private GoodsListDb(Context context) {
        CONTEXT = context;

        mMainList = loadListFromfile(context);

    }

    public static GoodsListDb getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (GoodsListDb.class) {
                if (INSTANCE == null) {
                    INSTANCE = new GoodsListDb(context);
                }
            }
        }
        return INSTANCE;
    }

    private List<GoodsList> loadListFromfile(Context context) {

        try {
            return GoodsListFile.loadListFromFile(context, FILE_NAME);
        } catch (IOException e) {
            return getPadrao();
        }
    }


    private void saveGoodsList() {
        try {
            GoodsListFile.saveListToFile(CONTEXT, FILE_NAME, mMainList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private List<GoodsList> getPadrao() {
        Good g1 = new Good("Sabão em pó", 20.00, 1);
        Good g2 = new Good("Amaciante", 15.0, 1);
        Good g3 = new Good("Desinfetante", 10.0, 1);
        Good g4 = new Good("Sabonete", 3.50, 4);
        Good g5 = new Good("Detergente", 1.99, 3);
        Good g6 = new Good("Seleta", 4.5, 3);
        Good g7 = new Good("Sazon bacon", 2.40, 2);
        Good g8 = new Good("Sazon Nordeste", 5.0, 1);
        Good g9 = new Good("Maionese", 15.0, 1);
        Good g10 = new Good("Barbecue", 11.00, 1);
        Good g11 = new Good("Café", 19.0, 1);
        Good g12 = new Good("Farofa", 10.0, 2);
        Good g13 = new Good("Tapioca", 8.65, 1);
        Good g14 = new Good("Calabresa", 9.90, 1);
        Good g15 = new Good("Filé de frango", 19.0, 1);
        GoodsList goodsList = new GoodsList("padrao", Arrays.asList(g1, g2, g3, g4, g5, g6, g7, g8, g9, g10, g11, g12, g13, g14, g15));
        List<GoodsList> list = new ArrayList<>();
        list.add(goodsList);
        //dao.saveList();
        return list;
    }

}
