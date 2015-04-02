package com.dop54321.classex2appactivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    TextView tvHitScore;
    TextView tvBoolScore;
    Button btGuess;
    Button btNewGame;
    Button btExit;
    EditText etUserGuess;

    BoolPgiaLogicClass game;
    private int numOfTries=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initiateClasses();
        setButtonsEvent();
        setWinEvent();
        setNewGame();

    }

    private void setWinEvent() {
        game.setWhatHapandWhenWin(new BoolPgiaLogicClass.WhatHapandWhenWin() {
            @Override
            public void youWin() {

                String dialogTitle = "You Win";
                String dialogMessage = "You Win!!! Number of tries: "+MainActivity.this.numOfTries;
                String dialogButtonText = "Start new game";
                AlertDialog alertDialog = getAlertDialog(dialogTitle, dialogMessage);
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, dialogButtonText,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                setNewGame();
                            }
                        });
                alertDialog.show();
            }
        });
        //
    }

    private AlertDialog getAlertDialog(String dialogTitle, String dialogMessage) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(dialogTitle);
        alertDialog.setMessage(dialogMessage);
        return alertDialog;
    }

    private void setButtonsEvent() {

        //set what happens when new game button is clicked
        btNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.numOfTries=0;
                setNewGame();
            }
        });

        //set what happens when guess button is clicked
        btGuess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userGuessedNumber = etUserGuess.getText().toString();
                int hitsCount = 0;
                int boolsCount = 0;
                try {
                    hitsCount = game.howManyHits(userGuessedNumber);
                    boolsCount = game.howManyBools(userGuessedNumber);
                } catch (IllegalArgumentException iae) {
                    AlertDialog errorDialog = getAlertDialog("Illegal Argument", "The number you enter is not legal");
                    errorDialog.setButton(DialogInterface.BUTTON_POSITIVE,"Try again",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //do nothing
                        }
                    });
                    errorDialog.show();
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                tvBoolScore.setText(String.valueOf(boolsCount));
                tvHitScore.setText(String.valueOf(hitsCount));
                MainActivity.this.numOfTries++;
            }
        });
        btExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dialogTitle = "Game Over!";
                String dialogMessage = "Game Over.. \nThe correct number was: "+MainActivity.this.game.getThisNumber().getThisNumber();
                String dialogButtonText = "see you next time..";
                AlertDialog alertDialog = getAlertDialog(dialogTitle, dialogMessage);
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, dialogButtonText,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                alertDialog.show();
            }
        });
    }

    private void setNewGame() {
        game.StartNewGame();
        tvBoolScore.setText("0");
        tvHitScore.setText("0");
    }

    private void initiateClasses() {
        tvHitScore = (TextView) findViewById(R.id.tvHitScore);
        tvBoolScore = (TextView) findViewById(R.id.tvBoolScore);
        btGuess = (Button) findViewById(R.id.btGuess);
        btNewGame = (Button) findViewById(R.id.btNewGame);
        btExit= (Button) findViewById(R.id.btExit);
        etUserGuess = (EditText) findViewById(R.id.etUserGuess);
        game = new BoolPgiaLogicClass();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
