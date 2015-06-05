package com.example.jose.utplqr.custom_dialog_fragments;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;

/**
 * Created by agua on 05/06/15.
 */
public class InvalidQRDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstancBundle) {
        Context context = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle("Error!")
                .setMessage("Código QR inválido.")
                .setPositiveButton("OK", null);

        AlertDialog dialog = builder.create();
        return dialog;
    }
}
