package com.ademkk.parcmetre;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener, View.OnDragListener {
    private static final String LOGCAT = null;
    TextView moneyOutput;
    float money = 0;
    String draggedView = "";
    MediaPlayer playerCoin;
    MediaPlayer playerPrint;
    Button greenBtn;
    Button redBtn;
    ImageButton settingsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);



        setContentView(R.layout.activity_main);
        findViewById(R.id.five100m).setOnTouchListener(this);
        findViewById(R.id.dinar).setOnTouchListener(this);
        findViewById(R.id.fivedinars).setOnTouchListener(this);
        findViewById(R.id.moneySlot).setOnDragListener(this);
        greenBtn = findViewById(R.id.greenbutton);
        greenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (money != 0) {
                    playPrint();
                    openTicket();
                }
                else {
                    Toast.makeText(MainActivity.this, "Vous devez insérer l'argent!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        redBtn = findViewById(R.id.redbutton);
        redBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                money = 0;
                moneyOutput = findViewById(R.id.moneyOutput);
                moneyOutput.setText("OFF");
                Toast.makeText(MainActivity.this, "Transaction annulée", Toast.LENGTH_SHORT).show();
            }
        });

        settingsBtn = findViewById(R.id.settings);
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this, SettingsActivity.class);
                MainActivity.this.startActivity(myIntent);
            }
        });


    }




    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
            view.startDrag(null, shadowBuilder, view, 0);
            draggedView = getResources().getResourceEntryName(view.getId()).toString();
            view.setVisibility(View.INVISIBLE);
            return true;
        }
        else {
            draggedView = getResources().getResourceEntryName(view.getId()).toString();
            view.setVisibility(View.VISIBLE);
            return false;
        }
    }

    @Override
    public boolean onDrag(View view, DragEvent dragevent) {
        int action = dragevent.getAction();

        if (action == DragEvent.ACTION_DROP) {
            moneyOutput = findViewById(R.id.moneyOutput);
            switch (draggedView) {
                case "dinar":
                    money++;
                    break;
                case "fivedinars":
                    money+=5;
                    break;
                case "five100m":
                    money+=.5;
            }
            this.playInsertCoin();
            moneyOutput.setText(String.format("%.03f", money) + " DT");
        }
        return true;
    }

    public void playInsertCoin(){
            if (playerCoin == null) {
                    playerCoin = MediaPlayer.create(this, R.raw.insertcoin);
            }
            playerCoin.start();
    }

    public void playPrint(){
        if (playerPrint == null) {
                playerPrint = MediaPlayer.create(this, R.raw.print);
        }
        playerPrint.start();
    }



    public void openTicket(){
        TicketDialog ticketDialog = new TicketDialog(this.money);
        ticketDialog.show(getSupportFragmentManager(), "Ticket");
    }
}