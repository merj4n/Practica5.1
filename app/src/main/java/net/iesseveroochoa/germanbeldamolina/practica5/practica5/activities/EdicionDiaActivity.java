package net.iesseveroochoa.germanbeldamolina.practica5.practica5.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import net.iesseveroochoa.germanbeldamolina.practica5.practica5.R;

public class EdicionDiaActivity extends AppCompatActivity {

    private Spinner spn_valoracion;
    private Button bt_calendario;
    private TextView tv_fecha;
    private EditText et_resumendia;
    private EditText et_cuentadia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edicion_dia);

        spn_valoracion = findViewById(R.id.spn_valorarvida);
        bt_calendario =findViewById(R.id.bt_calendario);
        tv_fecha = findViewById(R.id.tv_fecha);
        et_resumendia = findViewById(R.id.et_resumendia);
        et_cuentadia = findViewById(R.id.et_cuentadia);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_guardar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.it_guardar:
                //TODO implement
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
