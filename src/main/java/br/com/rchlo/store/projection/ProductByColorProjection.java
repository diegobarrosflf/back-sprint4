package br.com.rchlo.store.projection;

import br.com.rchlo.store.domain.Color;

public interface ProductByColorProjection {
    Color getColor();
    int getAmount();


    default String getColorDescription() {
        return getColor().getDescription();
    }
}
