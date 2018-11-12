package net.iesseveroochoa.germanbeldamolina.practica5.practica5.activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import net.iesseveroochoa.germanbeldamolina.practica5.practica5.R;
import net.iesseveroochoa.germanbeldamolina.practica5.practica5.modelo.DiarioContract;
import net.iesseveroochoa.germanbeldamolina.practica5.practica5.modelo.DiarioDB;

public class MainActivity extends AppCompatActivity {
    private static TextView tv_diario;
    private static DiarioDB diario;
    private static String ordenacionActual=DiarioContract.DiaDiarioEntries.FECHA;
    private final static int OPTION_REQUEST_ANYADIR =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_diario = findViewById(R.id.tvPrincipal);
        diario= new DiarioDB(MainActivity.this);
        diario.cargaDatosPrueba();
        mostrarDatos(ordenacionActual);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.it_menu_add:
                Intent intent_menu = new Intent(this,EdicionDiaActivity.class);
                startActivityForResult(intent_menu, OPTION_REQUEST_ANYADIR);
                return true;
            case R.id.it_ordenar:
                alerdDialogLista();
                return true;
            case R.id.it_borrar:
                Cursor c = diario.obtenDiario(ordenacionActual);
                if(c.moveToNext()){
                    DiaDiario borrar = new DiaDiario(DiarioDB.fechaBDtoFecha(c.getString(1)),c.getInt(2),c.getString(3),c.getString(4));
                    diario.borraDia(borrar);
                    mostrarDatos(ordenacionActual);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void alerdDialogLista(){
        DialogoSeleccion lista = new DialogoSeleccion();
        lista.show(getSupportFragmentManager(),"lista");

    }
    public static void mostrarDatos(String ordenadoPor){
        Cursor c = diario.obtenDiario(ordenadoPor);
        DiaDiario dia;
        tv_diario.setText("");//limpiamos el campo de texto
        //Nos aseguramos de que existe al menos un registro
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                dia=DiaDiario.cursorADiaDiario(c);
                //podéis sobrecargar toString en DiaDiario para mostrar los datos
                tv_diario.append(dia.toString());
            } while(c.moveToNext());
        }
    }

    public static class DialogoSeleccion extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(R.string.ordenarpor).setItems(R.array.lista, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {
                    if (item==0){
                        mostrarDatos(DiarioContract.DiaDiarioEntries.FECHA);
                        ordenacionActual=DiarioContract.DiaDiarioEntries.FECHA;
                    }else if(item==1){
                        mostrarDatos(DiarioContract.DiaDiarioEntries.VALORACION);
                        ordenacionActual=DiarioContract.DiaDiarioEntries.VALORACION;
                    }else {
                        mostrarDatos(DiarioContract.DiaDiarioEntries.RESUMEN);
                        ordenacionActual=DiarioContract.DiaDiarioEntries.RESUMEN;
                    }
                }
            });
            return builder.create();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            DiaDiario nuevoDia= data.getParcelableExtra(EdicionDiaActivity.EXTRA_DIA);
            diario.anyadeActualizaDia(nuevoDia);
            mostrarDatos(ordenacionActual);
        }
    }
}
