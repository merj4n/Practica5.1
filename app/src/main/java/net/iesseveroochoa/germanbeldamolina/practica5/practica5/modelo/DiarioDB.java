package net.iesseveroochoa.germanbeldamolina.practica5.practica5.modelo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import net.iesseveroochoa.germanbeldamolina.practica5.practica5.activities.DiaDiario;
import net.iesseveroochoa.germanbeldamolina.practica5.practica5.modelo.DiarioContract.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DiarioDB {
    private final static int DB_VERSION = 1;
    private final static String DB_NOMBRE = "diario.db";
    private final static String SQL_CREATE="CREATE TABLE IF not exists "+
            DiaDiarioEntries.TABLE_NAME+" ("+
            DiaDiarioEntries.ID + " integer primary key autoincrement," +
            DiaDiarioEntries.FECHA+" TEXT UNIQUE NOT NULL, "+
            DiaDiarioEntries.VALORACION+" INTEGER NOT NULL,"+
            DiaDiarioEntries.RESUMEN+" TEXT NOT NULL,"+
            DiaDiarioEntries.CONTENIDO+" TEXT NOT NULL,"+
            DiaDiarioEntries.FOTOURI+" TEXT,"+
            DiaDiarioEntries.LATITUD+" TEXT,"+
            DiaDiarioEntries.LONGITUD+" TEXT)";

    private final static String SQL_DELETE_TABLE="DROP TABLE IF EXISTS "+DiaDiarioEntries.TABLE_NAME;

    DBHelper dbH;
    SQLiteDatabase db;

    /**
     * Metodo que define el DBHelper
     * @param context
     */
    public DiarioDB(Context context){
        dbH=new DBHelper(context);
    }

    /**
     * Metodo para establecer la conexion con la base de datos.
     */
    public void open(){
        db=dbH.getWritableDatabase();
    }

    /**
     * Metodo que cierra la conexion con la base de datos.
     */
    public void close(){
        db.close();
    }

    /**
     * Metodo al que le pasamos un dia para borrarlo de la base de datos y ejecuta la sentencia
     * necesaria.
     * @param dia
     */
    public void borraDia(DiaDiario dia){
        open();
        db.delete(DiaDiarioEntries.TABLE_NAME,"fecha ='"+fechaToFechaDB(dia.getFecha())+"'",null);
        close();
    }

    /**
     * Metodo que inserta un dia en la base de datos con su sentencia pertinente.
     * @param dia
     */
    public void anyadeActualizaDia(DiaDiario dia){
        open();
        db.insert(DiaDiarioEntries.TABLE_NAME,null,dia.toContentValues());
        close();
    }

    /**
     * Metodo que nos devuelve el diario contenido en la base de datos ordenado por el valor del
     * String que le pasemos.
     * @param ordenadoPor
     * @return
     */
    public Cursor obtenDiario(String ordenadoPor){
        open();
        return db.query(DiaDiarioEntries.TABLE_NAME,null,null,null,null,null,ordenadoPor);
    }
    public int valoraVida(){
        return 0;
    }

    /**
     * Metodo en el que cargo los datos de prueba con los que vamos a trabajar.
     */
    public void cargaDatosPrueba(){
        anyadeActualizaDia(new DiaDiario(DiarioDB.fechaBDtoFecha("2018-10-20"),5,"D","1"));
        anyadeActualizaDia(new DiaDiario(DiarioDB.fechaBDtoFecha("2013-12-17"),2,"B","1"));
        anyadeActualizaDia(new DiaDiario(DiarioDB.fechaBDtoFecha("2014-5-12"),9,"X","1"));
        anyadeActualizaDia(new DiaDiario(DiarioDB.fechaBDtoFecha("2015-1-10"),1,"C","1"));
    }

    /**
     * Conversion de tipos de fecha en este caso para obtener la fecha en formato apto para la base
     * de datos.
     * @param f
     * @return
     */
    public static Date fechaBDtoFecha(String f){
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");
        Date fecha=null;
        try {
            fecha= formatoDelTexto.parse(f);

        } catch (ParseException ex) {

            ex.printStackTrace();

        }
        return fecha;
    }

    /**
     * Igual que el metodo anterior pero obteniendo la fecha en formato String.
     * @param fecha
     * @return
     */
    public static String fechaToFechaDB(Date fecha){
        DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        return f.format(fecha);
    }

    public class DBHelper extends SQLiteOpenHelper{


        public DBHelper(Context context) {
            super(context, DB_NOMBRE, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }

    }
}
