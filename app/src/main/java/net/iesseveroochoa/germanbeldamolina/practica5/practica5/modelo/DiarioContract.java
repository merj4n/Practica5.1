package net.iesseveroochoa.germanbeldamolina.practica5.practica5.modelo;

import android.provider.BaseColumns;

public class DiarioContract {
    public static class DiaDiarioEntries {
        public static final String TABLE_NAME = "diadiario";
        public static final String ID = BaseColumns._ID;//esta columna es necesaria para Android
        public static final String FECHA="fecha";
        public static final String VALORACION="valoracion";

        public static final String RESUMEN = "resumen";
        public static final String CONTENIDO = "contenido";
        public static final String FOTOURI = "fotouri";
        public static final String LATITUD = "latitud";
        public static final String LONGITUD = "longitud";
    }
}
