package tech.drufontael.marketlist.data.entities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class GoodsList {
    private String mListName;
    private Date mDate;
    private List<Good> mGoods;
    private int mGoodId;

    public GoodsList(String listName, List<Good> goods) {
        this.mListName=listName;
        this.mGoods = new ArrayList<>(goods);
        int id=1;
        for (Good g:this.mGoods){
            g.setId(id);
            id++;
        }
        this.mDate= Calendar.getInstance().getTime();
        this.mGoodId=id;
    }

    public String getListName() {
        return mListName;
    }

    public Date getDate() {
        return mDate;
    }

    public List<Good> getGoods() {
        return mGoods;
    }

    public void addGood(Good good){

        good.setId(this.mGoodId);
        this.mGoodId++;
        this.mGoods.add(good);
    }

    public Double getTotalPrice(){
        return this.mGoods.stream().filter(Good::isActive)
                .mapToDouble(Good::getTotalItemPrice).sum();
    }
}
