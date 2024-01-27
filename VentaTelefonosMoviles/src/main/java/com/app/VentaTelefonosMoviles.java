package com.app;

/**
 *
 */
public class VentaTelefonosMoviles {

    public static void main(String[] args) {

        Inventario inventario = new Inventario();

        // Agregar los registros al inventario
        InventarioItem item = new InventarioItem(new Telefono("AL1", Fabricante.ALCATEL, Modelo.ALCATEL_3X_4CAM, "1"),
                300, 200, "cámara cuádruple con IA de 16 MP", PlanLlamada.PREPAGO, PlanTextoTipo.PREMIUM, PlanDatosTipo.PREMIUM);
        item.addRecarga(Recarga.A);
        inventario.add(item);

        inventario.add(new InventarioItem(new Telefono("AL2", Fabricante.ALCATEL, Modelo.ALCATEL_3X_4CAM, "2"),
                300, 200, "cámara cuádruple con IA de 16 MP", PlanLlamada.POSTPAGO, PlanTextoTipo.GOLD, PlanDatosTipo.GOLD));

        item = new InventarioItem(new Telefono("AL3", Fabricante.ALCATEL, Modelo.ALCATEL_3X_4CAM, "3"),
                300, 200, "cámara cuádruple con IA de 16 MP", PlanLlamada.PREPAGO, PlanTextoTipo.SILVER, PlanDatosTipo.SILVER);
        item.addRecarga(Recarga.B);
        item.addRecarga(Recarga.C);
        inventario.add(item);

        // Agregar los registros al inventario
        item = new InventarioItem(new Telefono("SS1", Fabricante.SAMSUNG, Modelo.SAMSUNG_52, "4"),
                500, 350, " pantalla FHD + Super AMOLED, que alcanza los 800 nits", PlanLlamada.PREPAGO, PlanTextoTipo.PREMIUM, PlanDatosTipo.PREMIUM);
        item.addRecarga(Recarga.C);
        inventario.add(item);

        inventario.add(new InventarioItem(new Telefono("SS2", Fabricante.SAMSUNG, Modelo.SAMSUNG_52, "5"),
                500, 350, " pantalla FHD + Super AMOLED, que alcanza los 800 nits", PlanLlamada.POSTPAGO, PlanTextoTipo.GOLD, PlanDatosTipo.GOLD));

        item = new InventarioItem(new Telefono("SS3", Fabricante.SAMSUNG, Modelo.SAMSUNG_52, "6"),
                500, 350, " pantalla FHD + Super AMOLED, que alcanza los 800 nits", PlanLlamada.PREPAGO, PlanTextoTipo.SILVER, PlanDatosTipo.SILVER);
        item.addRecarga(Recarga.A);
        item.addRecarga(Recarga.C);
        inventario.add(item);

        System.out.print("\n\n\n\n");

        System.out.println("**********************************************");
        System.out.println("Venta de teléfonos móviles y planes de servicios");

        // Reportes
        System.out.println("\nCantidad total de teléfonos en inventario: " + inventario.cantTotalTelefonos());
        System.out.println("Cantidad total de teléfonos plan postpago: " + inventario.cantTotalPrePago());
        System.out.println("Cantidad total de teléfonos plan prepago: " + inventario.cantTotalPostPago());

        System.out.println("\nCantidad total de teléfonos plan texto primium: " + inventario.cantTotalTextoPremium());
        System.out.println("Cantidad total de teléfonos plan texto silver: " + inventario.cantTotalTextoSilver());
        System.out.println("Cantidad total de teléfonos plan texto gold: " + inventario.cantTotalTextoGold());

        System.out.println("\nCantidad total de teléfonos plan datos primium: " + inventario.cantTotalDatosPremium());
        System.out.println("Cantidad total de teléfonos plan datos silver: " + inventario.cantTotalDatosSilver());
        System.out.println("Cantidad total de teléfonos plan datos gold: " + inventario.cantTotalDatosGold());

        System.out.println("\nConsumo total de teléfonos: " + inventario.consumoTotal());
        System.out.println("Consumoto total de prepago: " + inventario.consumoPrePago());
        System.out.println("Consumoto total de postpago: " + inventario.consumoPostPago());

        System.out.println("Fin del programa");
    }
}
