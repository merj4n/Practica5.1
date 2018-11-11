package net.iesseveroochoa.germanbeldamolina.practica5.practica5.activities;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import net.iesseveroochoa.germanbeldamolina.practica5.practica5.modelo.DiarioContract;
import net.iesseveroochoa.germanbeldamolina.practica5.practica5.modelo.DiarioDB;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DiaDiario implements Parcelable {
    private Date fecha;
    private int valoracionDia;
    private String resumen;
    private String contenido;
    private String fotoUri;
    private String latitud;
    private String longitud;

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getValoracionDia() {
        return valoracionDia;
    }

    public void setValoracionDia(int valoracionDia) {
        this.valoracionDia = valoracionDia;
    }

    public String getResumen() {
        return resumen;
    }

    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getFotoUri() {
        return fotoUri;
    }

    public void setFotoUri(String fotoUri) {
        this.fotoUri = fotoUri;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public DiaDiario(Date fecha, int valoracionDia, String resumen, String contenido, String fotoUri, String latitud, String longitud) {
        this.fecha = fecha;
        this.valoracionDia = valoracionDia;
        this.resumen = resumen;
        this.contenido = contenido;
        this.fotoUri = fotoUri;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public DiaDiario(Date fecha, int valoracionDia, String resumen, String contenido) {
        this.fecha = fecha;
        this.valoracionDia = valoracionDia;
        this.resumen = resumen;
        this.contenido = contenido;
    }

    public int getValoracionResumida(){
        if (this.valoracionDia<5){
            return 1;
        }else if(this.valoracionDia<8){
            return 2;
        }
        return 3;

    }

    public static DiaDiario cursorADiaDiario(Cursor c){
        DiaDiario diaDiario;
        diaDiario = new DiaDiario(DiarioDB.fechaBDtoFecha(c.getString(c.getColumnIndex(DiarioContract.DiaDiarioEntries.FECHA))),
                c.getInt(c.getColumnIndex(DiarioContract.DiaDiarioEntries.VALORACION)),
                c.getString(c.getColumnIndex(DiarioContract.DiaDiarioEntries.RESUMEN)),
                c.getString(c.getColumnIndex(DiarioContract.DiaDiarioEntries.CONTENIDO)));
        return diaDiario;
    }
    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(DiarioContract.DiaDiarioEntries.FECHA, DiarioDB.fechaToFechaDB(getFecha()));
        values.put(DiarioContract.DiaDiarioEntries.VALORACION, getValoracionDia());
        values.put(DiarioContract.DiaDiarioEntries.RESUMEN, getResumen());
        values.put(DiarioContract.DiaDiarioEntries.CONTENIDO, getContenido());
        return values;
    }

    @Override
    public String toString() {
        return "Fecha= " + DiarioDB.fechaToFechaDB(fecha) + "\n" +
                "Valoracion= " + valoracionDia + " Resumen= " + resumen + "\n" +
                "Contenido= " + contenido + "\n";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.fecha != null ? this.fecha.getTime() : -1);
        dest.writeInt(this.valoracionDia);
        dest.writeString(this.resumen);
        dest.writeString(this.contenido);
        dest.writeString(this.fotoUri);
        dest.writeString(this.latitud);
        dest.writeString(this.longitud);
    }

    protected DiaDiario(Parcel in) {
        long tmpFecha = in.readLong();
        this.fecha = tmpFecha == -1 ? null : new Date(tmpFecha);
        this.valoracionDia = in.readInt();
        this.resumen = in.readString();
        this.contenido = in.readString();
        this.fotoUri = in.readString();
        this.latitud = in.readString();
        this.longitud = in.readString();
    }

    public static final Parcelable.Creator<DiaDiario> CREATOR = new Parcelable.Creator<DiaDiario>() {
        @Override
        public DiaDiario createFromParcel(Parcel source) {
            return new DiaDiario(source);
        }

        @Override
        public DiaDiario[] newArray(int size) {
            return new DiaDiario[size];
        }
    };
}
