package net.iesseveroochoa.germanbeldamolina.practica5.practica5.activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import net.iesseveroochoa.germanbeldamolina.practica5.practica5.R;
import net.iesseveroochoa.germanbeldamolina.practica5.practica5.modelo.DiarioDB;

import java.util.Calendar;

public class EdicionDiaActivity extends AppCompatActivity {

    public static final String EXTRA_DIA = "net.iesseveroochoa.germanbeldamolina.practica5.practica5.activities.dia";
    private Spinner spn_valoracion;
    private Button bt_calendario;
    private TextView tv_fecha;
    private EditText et_resumendia;
    private EditText et_cuentadia;
    private int anyo, mes, dia;

    /**
     * Metodo que se ejecuta en la creacion de la actividad EdicionDiaActivity del que seleccionaremos
     * fecha, resumen, valoracion y las notas diarias.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edicion_dia);

        spn_valoracion = findViewById(R.id.spn_valorarvida);
        bt_calendario =findViewById(R.id.bt_calendario);
        tv_fecha = findViewById(R.id.tv_fecha);
        et_resumendia = findViewById(R.id.et_resumendia);
        et_cuentadia = findViewById(R.id.et_cuentadia);
        spn_valoracion.setSelection(5);

        /**
         * Metodo que actua al presionar el boton de calendario y nos muestra la fecha del dia
         * actual de forma inicial.
         */
        bt_calendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                anyo = c.get(Calendar.YEAR);
                mes = c.get(Calendar.MONTH);
                dia = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(EdicionDiaActivity.this, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                String fecha = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                                tv_fecha.setText(fecha);
                            }
                        }, anyo, mes, dia);
                datePickerDialog.show();
            }
        });
    }

    /**
     * Llamamos al menu para guardar que contiene el boton de guardado.
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_guardar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Metodo que cuando pulsamos el boton de guardar almacena los datos en la base de datos
     * si falta algun dato por completar nos mostrara un dialog advirtiendonos de ello.
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.it_guardar:
            if(comprobarDatos()) {
                Intent guardar = getIntent();
                String text = spn_valoracion.getSelectedItem().toString();
                DiaDiario nuevoDia = new DiaDiario(
                        DiarioDB.fechaBDtoFecha(tv_fecha.getText().toString()),
                        Integer.parseInt(text),
                        et_resumendia.getText().toString(),
                        et_cuentadia.getText().toString());
                guardar.putExtra(EXTRA_DIA,(Parcelable) nuevoDia);//envio
                setResult(RESULT_OK,guardar);
                finish();
            }else {
                AlertDialog.Builder alerta = new AlertDialog.Builder(EdicionDiaActivity.this);
                alerta.setTitle(R.string.aviso);
                alerta.setMessage(R.string.mensaje_aviso);
                alerta.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alerta.show();
            }

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Metodo creado para comprobar que ningun campo esta sin rellenar a la hora de guardar los
     * datos en la base de datos.
     * @return
     */
    private boolean comprobarDatos() {
        if(spn_valoracion.getSelectedItem().toString().isEmpty())
            return false;
        else if(et_resumendia.getText().toString().isEmpty())
            return false;
        else if (et_cuentadia.getText().toString().isEmpty())
            return false;
        else if(tv_fecha.getText().toString().equals(getString(R.string.fecha)))
            return false;
        else {
            return true;
        }
    }
}
