package tech.drufontael.marketlist.data.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SavedList {
    private String name;
    private String date;


    public SavedList(String name, Date date) {
        this.name = name;
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        this.date = format.format(date);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
