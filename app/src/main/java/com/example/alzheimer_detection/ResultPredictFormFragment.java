package com.example.alzheimer_detection;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.fragment.app.DialogFragment;

public class ResultPredictFormFragment extends DialogFragment {
    static ResultPredictFormFragment newInstance(int num) {
        ResultPredictFormFragment f = new ResultPredictFormFragment();

        return f;
    }
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.dialog_result, null))
                // Add action buttons
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(getActivity(), PredictList.class);
                        startActivity(intent);
                        dialog.dismiss();

                    }
                });
        return builder.create();
    }
}
