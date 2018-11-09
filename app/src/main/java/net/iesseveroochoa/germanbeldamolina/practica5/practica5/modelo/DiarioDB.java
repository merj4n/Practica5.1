package net.iesseveroochoa.germanbeldamolina.practica5.practica5.modelo;

import android.content.ContentValues;
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
    private final static String SQL_CREATE="CREATE TABLE IF NOT EXIST"+
            DiaDiarioEntries.TABLE_NAME+" ("+
            DiaDiarioEntries.ID + " integer primary key autoincrement," +
            DiaDiarioEntries.FECHA+" TEXT UNIQUE NOT NULL, "+
            DiaDiarioEntries.VALORACION+" INTEGER NOT NULL,"+
            DiaDiarioEntries.RESUMEN+" TEXT NOT NULL,"+
            DiaDiarioEntries.CONTENIDO+" TEXT NOT NULL,"+
            DiaDiarioEntries.FOTOURI+" TEXT NOT NULL,"+
            DiaDiarioEntries.LATITUD+" TEXT NOT NULL,"+
            DiaDiarioEntries.LONGITUD+" TEXT NOT NULL,";

    private final static String SQL_DELETE_TABLE="DROP TABLE IF EXISTS "+DiaDiarioEntries.TABLE_NAME;

    DBHelper dbH;
    SQLiteDatabase db;

    public DiarioDB(Context context){
        dbH=new DBHelper(context);
    }

    public void open(){
        db=dbH.getWritableDatabase();
    }
    public void close(){
        db.close();
    }
    public void borraDia(DiaDiario dia){
        db.delete(DiaDiarioEntries.TABLE_NAME,"fecha="+fechaToFechaDB(dia.getFecha()),null);

    }
    public void anyadeActualizaDia(DiaDiario dia){
        ContentValues registrodia = new ContentValues();
        registrodia.put(DiaDiarioEntries.FECHA,fechaToFechaDB(dia.getFecha()));
        registrodia.put(DiaDiarioEntries.VALORACION,dia.getValoracionDia());
        registrodia.put(DiaDiarioEntries.RESUMEN,dia.getResumen());
        registrodia.put(DiaDiarioEntries.CONTENIDO,dia.getContenido());

        db.insert(DiaDiarioEntries.TABLE_NAME,null,registrodia);
    }
    public Cursor obtenDiario(String ordenadoPor){
        return db.query(DiaDiarioEntries.TABLE_NAME,null,null,null,null,null,ordenadoPor);
    }
    //PENDIENTE DE LOS MERLOS
    public int valoraVida(){
        return 0;
    }
    public static DiaDiario cursorADiaDiario(Cursor c){
        DiaDiario diaDiario;
        diaDiario = new DiaDiario(fechaBDtoFecha(c.getString(c.getColumnIndex(DiaDiarioEntries.FECHA))),
                c.getInt(c.getColumnIndex(DiaDiarioEntries.VALORACION)),
                c.getString(c.getColumnIndex(DiaDiarioEntries.RESUMEN)),
                c.getString(c.getColumnIndex(DiaDiarioEntries.CONTENIDO)));
        return diaDiario;
    }
    public void cargaDatosPrueba(){
        anyadeActualizaDia(new DiaDiario(new Date(2008,3,2),9,"A","1"));
        anyadeActualizaDia(new DiaDiario(new Date(2010,3,2),9,"A","1"));
        anyadeActualizaDia(new DiaDiario(new Date(2021,3,3),9,"A","1"));
        anyadeActualizaDia(new DiaDiario(new Date(2044,3,2),1,"A","1"));
        anyadeActualizaDia(new DiaDiario(new Date(2045,3,5),9,"A","1"));
        anyadeActualizaDia(new DiaDiario(new Date(2046,3,7),9,"A","1"));
        anyadeActualizaDia(new DiaDiario(new Date(2047,3,22),9,"A","1"));
    }

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

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }

    }
}
