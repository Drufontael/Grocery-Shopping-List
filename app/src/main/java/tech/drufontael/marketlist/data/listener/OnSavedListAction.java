package tech.drufontael.marketlist.data.listener;

public interface OnSavedListAction {
    void load(String name);
    void remove(String name);
}
