package com.ademkk.parcmetre;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.ademkk.parcmetre.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TicketDialog extends AppCompatDialogFragment {
    private TextView dateTimeDisplay;
    private TextView endDate;
    private float money;


    TicketDialog(float money) {
        super();
        this.money = money;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {



        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_ticket, null);


        float timeToAdd = (int) (money / 0.010);
        dateTimeDisplay = (TextView) view.findViewById(R.id.currentdate);
        endDate = (TextView) view.findViewById(R.id.endDate);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy à HH:mm", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());

        Calendar date = Calendar.getInstance();
        long timeInSecs = date.getTimeInMillis();
        Date afterAddingMins = new Date((long) (timeInSecs + (timeToAdd * 60 * 1000)));
        String endDateandTime = sdf.format(afterAddingMins);

        dateTimeDisplay.setText("De: " + currentDateandTime);
        endDate.setText("A: " + endDateandTime);

        builder.setView(view)
                .setTitle("Ticket")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        return builder.create();
    }
}
