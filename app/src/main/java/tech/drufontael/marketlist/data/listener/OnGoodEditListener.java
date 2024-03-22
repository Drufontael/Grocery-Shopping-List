package tech.drufontael.marketlist.data.listener;

import tech.drufontael.marketlist.data.model.Good;

public interface OnGoodEditListener {
    void onEdit(int id, Good good);

    void remove(int id);
}
