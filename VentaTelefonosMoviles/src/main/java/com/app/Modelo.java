package com.app;

/**
 *
 */
public enum Modelo {

    ALCATEL_3X_4CAM(Fabricante.ALCATEL),
    APPLE_IPHONE(Fabricante.APPLE),
    GOOGLE_PIXEL_5(Fabricante.GOOGLE_PIXEL),
    LG_W41(Fabricante.LG),
    MOTOROLA_EDGE_30_PRO(Fabricante.MOTOROLA),
    NOKIA_XR20(Fabricante.NOKIA),
    SAMSUNG_52(Fabricante.SAMSUNG);

    private final Fabricante fabricante;

    Modelo(Fabricante inFab) {
        fabricante = inFab;
    }

    /**
     * @return the fabricante
     */
    public Fabricante getFabricante() {
        return fabricante;
    }

}
