package net.iesseveroochoa.germanbeldamolina.practica5.practica5.activities;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import net.iesseveroochoa.germanbeldamolina.practica5.practica5.R;
import net.iesseveroochoa.germanbeldamolina.practica5.practica5.modelo.DiarioContract;
import net.iesseveroochoa.germanbeldamolina.practica5.practica5.modelo.DiarioDB;

public class MainActivity extends AppCompatActivity {
    private TextView tv_diario;
    private DiarioDB diario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_diario = findViewById(R.id.tvPrincipal);
        diario= new DiarioDB(MainActivity.this);
        diario.cargaDatosPrueba();
        mostrarDatos();

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
                startActivity(intent_menu);
                return true;
            case R.id.it_ordenar:
                alerdDialogLista();
                return true;
            case R.id.it_borrar:
                Cursor c = diario.obtenDiario(DiarioContract.DiaDiarioEntries.FECHA);
                c.moveToFirst();
                DiaDiario borrar = new DiaDiario(DiarioDB.fechaBDtoFecha(c.getString(1)),c.getInt(2),c.getString(3),c.getString(4));
                diario.borraDia(borrar);
                mostrarDatos();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void alerdDialogLista(){
        DialogoSeleccion lista = new DialogoSeleccion();
        lista.show(getSupportFragmentManager(),"lista");

    }
    public void mostrarDatos(){
        Cursor c = diario.obtenDiario(DiarioContract.DiaDiarioEntries.FECHA);
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

}
