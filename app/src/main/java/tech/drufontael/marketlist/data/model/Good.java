package tech.drufontael.marketlist.data.model;

public class Good implements Comparable<Good> {
    private int mId;
    private final String mGoodName;
    private Double mPrice;
    private Integer mQuantity;
    private Boolean mActive;


    public Good(String goodName, Double price, Integer quantity) {
        this.mGoodName = goodName;
        this.mPrice = price;
        this.mQuantity = quantity;
        this.mActive =true;

    }

    public int getId() {
        return mId;
    }

    public String getGoodName() {
        return mGoodName;
    }

    public Double getPrice() {
        return mPrice;
    }

    public Integer getQuantity() {
        return mQuantity;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public void setPrice(Double price) {
        this.mPrice = price;
    }

    public void setQuantity(Integer quantity) {
        this.mQuantity = quantity;
    }

    public Boolean isActive() {
        return mActive;
    }

    public void setActive(Boolean active) {
        this.mActive = active;
    }

    public Double getTotalItemPrice(){return this.mPrice*this.mQuantity;}


    @Override
    public int compareTo(Good o) {
        return this.mGoodName.compareTo(o.mGoodName);
    }
}
