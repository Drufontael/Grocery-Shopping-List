package tech.drufontael.marketlist.data.listener;

import tech.drufontael.marketlist.data.entities.Good;

public interface OnGoodEditListener {
    void onEdit(int id, Good good);
}
