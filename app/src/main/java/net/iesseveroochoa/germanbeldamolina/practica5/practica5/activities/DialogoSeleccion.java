package net.iesseveroochoa.germanbeldamolina.practica5.practica5.activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import net.iesseveroochoa.germanbeldamolina.practica5.practica5.R;

public class DialogoSeleccion extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.ordenarpor).setItems(R.array.lista, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        if (item==0){
                            System.out.println("fecha");
                            //diario.obtenDiario("fecha");
                        }else if(item==1){
                            System.out.println("valoracion");
                            //diario.obtenDiario("valoracion");
                        }else {
                            System.out.println("contenido");
                            //diario.obtenDiario("contenido");
                        }
                    }
                });
        return builder.create();
    }
}
