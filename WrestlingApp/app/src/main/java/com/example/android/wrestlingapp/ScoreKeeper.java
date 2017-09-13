package com.example.android.wrestlingapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static java.security.AccessController.getContext;

/**
 * Created by gobov on 4/4/2017.
 */

public class ScoreKeeper extends AppCompatActivity {

    // SCORE FOR TEAM A
    private int scoreTeamA = 0;

    // SCORE FOR TEAM B
    private int scoreTeamB = 0;

    // DEFAULT SCORE FOR TEAM A
    private int aSet = 0;

    // DEFAULT SCORE FOR TEAM B
    private int bSet = 0;

    // TO STORE THE VALUE OF PLAYER NAME
    private String stringNameValue;

    // FOR DISPLAYING THE COUNTER TIME AND THE NAME OF THE PLAYER
    private TextView counterTextView, playerNameTextView, setViewA, setViewB;

    // BUTTONS FOR TEAM A
    private Button aTakedown_2, aReversal_2, aEscape_1, aNearFall_2, aNearFall_3, aPenalty;

    // BUTTONS FOR TEAM B
    private Button bTakedown_2, bReversal_2, bEscape_1, bNearFall_2, bNearFall_3, bPenalty;

    // TOGGLEBUTTONS FOR ROUNDS
    private ToggleButton round1btn, round2btn, round3btn;

    // COUNTDOWN TIMER
    private CountDownTimer cTimer;

    // MEDIA PLAYER TO SOUND THE BEGINNING AND THE END OF THE ROUND
    private MediaPlayer mp;

    // FOR DISPLAYING WHO WINS IF THE TIMER RUNS OUT
    private Toast counterEnd;

    // SUBMIT SCORE BUTTON
    private Button submitFinalScore;


    // TO KEEEP THE SCORE UPON ROTATING
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        scoreTeamA = savedInstanceState.getInt("privScoreTeamA");
        scoreTeamB = savedInstanceState.getInt("privScoreTeamB");
        aSet = savedInstanceState.getInt("privSetScoreA", aSet);
        bSet = savedInstanceState.getInt("privSetScoreB", bSet);
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("privScoreTeamA", scoreTeamA);
        outState.putInt("privScoreTeamB", scoreTeamB);
        outState.putInt("privSetScoreA", aSet);
        outState.putInt("privSetScoreB", bSet);
    }

    @Override
    protected void onResume() {
        super.onResume();
        displayForTeamA(scoreTeamA);
        displayForTeamB(scoreTeamB);
        displaySetA(aSet);
        displaySetB(bSet);
    }

    // TEAM A - BEGINS THE CALCULATION METHODS FOR ADDING POINTS

    // ADD 1 POINT FOR TEAM A SCORE
    public void addOneForTeamA(View v) {

        if(scoreTeamA >= 14 ){
            return;
        } else if (scoreTeamA != 13) {
            scoreTeamA = scoreTeamA + 1;
        }else{
            scoreTeamA = scoreTeamA + 1;
            aSet += 1;
            displaySetA(aSet);
        }
        displayForTeamA(scoreTeamA);
    }

    // ADD 2 POINTS FOR TEAM A SCORE
    public void addTwoForTeamA(View v) {
        if (scoreTeamA >= 14){
            return;
        }

        if (scoreTeamA <= 13){
            scoreTeamA += 2;
        }

        if(scoreTeamA >= 14){
            if (aSet <= 1){
                aSet += 1;
            } else if (aSet == 3){
                return;
            }
            displaySetA(aSet);
        }
        displayForTeamA(scoreTeamA);
    }

    // ADD 3 POINTS FOR TEAM A SCORE
    public void addThreeForTeamA(View v) {
        if (scoreTeamA >= 14){
            return;
        }

        if (scoreTeamA <= 13){
            scoreTeamA += 3;
        }

        if(scoreTeamA >= 14){
            if (aSet <= 1){
                aSet += 1;
            } else if (aSet == 3){
                return;
            }
            displaySetA(aSet);
        }
        displayForTeamA(scoreTeamA);
    }

    // TEAM A - ENDS THE CALCULATION METHODS FOR ADDING POINTS


    // TEAM B - BEGINS THE CALCULATION METHODS FOR ADDING POINTS

    // ADDS 1 POINT FOR TEAM B SCORE
    public void addOneForTeamB(View v) {
        if(scoreTeamB >= 14 ){
            return;
        } else if (scoreTeamB != 13) {
            scoreTeamB = scoreTeamB + 1;
        } else{
            scoreTeamB = scoreTeamB + 1;
            bSet += 1;
            displaySetB(bSet);
        }
        displayForTeamB(scoreTeamB);
    }

    // ADDS 2 POINTS FOR TEAM B SCORE
    public void addTwoForTeamB(View v) {
        if (scoreTeamB >= 14){
            return;
        }

        if (scoreTeamB <= 13){
            scoreTeamB += 2;
        }

        if(scoreTeamB >= 14){
            if (bSet <= 1){
                bSet += 1;
            } else if (bSet == 3){
                return;
            }
            displaySetB(bSet);
        }
        displayForTeamB(scoreTeamB);
    }

    // ADDS 3 POINTS FOR TEAM B SCORE
    public void addThreeForTeamB(View v) {
        if (scoreTeamB >= 14){
            return;
        }

        if (scoreTeamB <= 13){
            scoreTeamB += 3;
        }

        if(scoreTeamB >= 14){
            if (bSet <= 1){
                bSet += 1;
            } else if (bSet == 3){
                return;
            }
            displaySetB(bSet);
        }
        displayForTeamB(scoreTeamB);
    }

    // TEAM B - ENDS THE CALCULATION METHODS FOR ADDING POINTS


    // ROUND RESET METHOD FOR BOTH TEAMS
    public void roundReset2 (View v) {
        scoreTeamA = 0;
        scoreTeamB = 0;
        displayForTeamA(scoreTeamA);
        displayForTeamB(scoreTeamB);
    }

    // FULL RESET
    public void resetBothTeams(View v) {
        scoreTeamA = 0;
        scoreTeamB = 0;
        bSet = 0;
        aSet = 0;
        displaySetB(scoreTeamB);
        displaySetA(scoreTeamA);
        displayForTeamA(scoreTeamA);
        displayForTeamB(scoreTeamB);
    }

    // DISPLAYS THE SCORE FOR TEAM A
    public void displayForTeamA(int score) {
        TextView scoreViewA = (TextView) findViewById(R.id.team_a_score);
        scoreViewA.setText(String.valueOf(score));
    }

    // DISPLAYS THE SCORE FOR TEAM B
    public void displayForTeamB(int score) {
        TextView scoreViewB = (TextView) findViewById(R.id.team_b_score);
        scoreViewB.setText(String.valueOf(score));
    }

    // DISPLAYS THE SET SCORE FOR TEAM A
    public void displaySetA (int scoreSetA) {
        setViewA = (TextView) findViewById(R.id.display_set_a);
        setViewA.setText(String.valueOf(scoreSetA));

        if (scoreSetA == 2){
            Toast winnerA = Toast.makeText(ScoreKeeper.this, getString(R.string.youWon), Toast.LENGTH_SHORT);
            winnerA.show();
        }
    }

    // DISPLAYS THE SET SCORE FOR TEAM B
    public void displaySetB (int scoreSet) {
        setViewB = (TextView) findViewById(R.id.display_set_b);
        setViewB.setText(String.valueOf(scoreSet));

        if (scoreSet == 2){
            Toast winnerB = Toast.makeText(ScoreKeeper.this, getString(R.string.youLost), Toast.LENGTH_SHORT);
            winnerB.show();
        }
    }

    // FOR DETERMINING WHETHER THE TIMER SHOULD STOP
    public boolean isPaused (){
        if (scoreTeamA >= 14 || scoreTeamB >= 14){
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_keeper);

        // GETTING ACCESS TO WRITEN FILE VALUES FROM SHARED PREFERENCES
        final SharedPreferences my_preferences = PreferenceManager.getDefaultSharedPreferences(this);

        // FINDING THE VIEW TO DISPLAY PLAYER'S NAME
        playerNameTextView = (TextView) findViewById(R.id.name_textView);

        // GETTING THE STRING VALUE FROM SHARED PREFERENCES
        stringNameValue = my_preferences.getString("name_value", "");

        // CHECKING IF THERE IS ANY NAME INPUT
        if (stringNameValue.contentEquals("")){
            playerNameTextView.setText(getString(R.string.stringNameDefaultValue));
        } else {
            playerNameTextView.setText(stringNameValue);
        }

        // FINDING THE VIEW TO DISPLAY THE COUNTER TIME
        counterTextView = (TextView) findViewById(R.id.counter_text_view);

        // INSTANTIATING THE MEDIA PLAYER
        mp = MediaPlayer.create(this, (R.raw.bell));

        // FINDING THE VIEWS FOR BUTTONS TEAM A
        aTakedown_2 = (Button) findViewById(R.id.A_takedown_2_btn);
        aReversal_2 = (Button) findViewById(R.id.A_reversal_2_btn);
        aEscape_1 = (Button) findViewById(R.id.A_escape_1_btn);
        aNearFall_2 = (Button) findViewById(R.id.A_nearfall_2_btn);
        aNearFall_3 = (Button) findViewById(R.id.A_nearfall_3_btn);
        aPenalty = (Button) findViewById(R.id.A_penalty_btn);

        // CREATING AN ARRAY LIST OF BUTTONS TEAM A FOR EASIER ACCESS TO DATA
        final ArrayList<Button> buttonsTeamA = new ArrayList<Button>();
        buttonsTeamA.add(aTakedown_2);
        buttonsTeamA.add(aReversal_2);
        buttonsTeamA.add(aEscape_1);
        buttonsTeamA.add(aNearFall_2);
        buttonsTeamA.add(aNearFall_3);
        buttonsTeamA.add(aPenalty);

        // SETTING THE TEXT IN TEAM A FOR EACH BUTTON IN A GROUP
        buttonsTeamA.get(0).setText(getString(R.string.takedown));
        buttonsTeamA.get(1).setText(getString(R.string.reversal));
        buttonsTeamA.get(2).setText(getString(R.string.escape));
        buttonsTeamA.get(3).setText(getString(R.string.nearFall2));
        buttonsTeamA.get(4).setText(getString(R.string.nearFall3));
        buttonsTeamA.get(5).setText(getString(R.string.penaltyA));


        // FINDING THE VIEWS FOR BUTTONS TEAM B
        bTakedown_2 = (Button) findViewById(R.id.B_takedown_2_btn);
        bReversal_2 = (Button) findViewById(R.id.B_reversal_2_btn);
        bEscape_1 = (Button) findViewById(R.id.B_escape_1_btn);
        bNearFall_2 = (Button) findViewById(R.id.B_nearfall_2_btn);
        bNearFall_3 = (Button) findViewById(R.id.B_nearfall_3_btn);
        bPenalty = (Button) findViewById(R.id.B_penalty_btn);

        // CREATING AN ARRAY LIST OF BUTTONS TEAM B FOR EASIER ACCESS TO DATA
        final ArrayList<Button> buttonsTeamB = new ArrayList<Button>();
        buttonsTeamB.add(bTakedown_2);
        buttonsTeamB.add(bReversal_2);
        buttonsTeamB.add(bEscape_1);
        buttonsTeamB.add(bNearFall_2);
        buttonsTeamB.add(bNearFall_3);
        buttonsTeamB.add(bPenalty);

        // SETTING THE TEXT FOR TEAM B FOR EACH BUTTON IN A GROUP
        buttonsTeamB.get(0).setText(getString(R.string.takedown));
        buttonsTeamB.get(1).setText(getString(R.string.reversal));
        buttonsTeamB.get(2).setText(getString(R.string.escape));
        buttonsTeamB.get(3).setText(getString(R.string.nearFall2));
        buttonsTeamB.get(4).setText(getString(R.string.nearFall3));
        buttonsTeamB.get(5).setText(getString(R.string.penaltyB));

        cTimer = new CountDownTimer(120000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (isPaused()) {
                    cancel();
                } else {
                    counterTextView.setText("" + String.format("%d : %d ",
                            TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                            TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
                    counterTextView.setTextSize(35);
                }
            }

            // ACTIONS TO DO ON TIMER FINISH
            @Override
            public void onFinish() {
                if (stringNameValue.contentEquals("")){
                    stringNameValue = getString(R.string.stringNameDefaultValue);
                    if (scoreTeamA > scoreTeamB) {
                        if (aSet <= 1){
                            aSet = aSet + 1;
                            displaySetA(aSet);
                        }
                        counterEnd = Toast.makeText(ScoreKeeper.this, (stringNameValue + getString(R.string.winsRound)), Toast.LENGTH_LONG);
                        counterEnd.show();
                    } else if (scoreTeamA < scoreTeamB) {
                        if (bSet <= 1){
                            bSet = bSet + 1;
                            displaySetB(bSet);
                        }
                        counterEnd = Toast.makeText(ScoreKeeper.this, getString(R.string.nikolaRound), Toast.LENGTH_LONG);
                        counterEnd.show();
                    } else if (scoreTeamA > scoreTeamB && aSet > bSet) {
                        counterEnd = Toast.makeText(ScoreKeeper.this, (stringNameValue + getString(R.string.overall)), Toast.LENGTH_LONG);
                        counterEnd.show();
                    } else if (scoreTeamA < scoreTeamB && aSet < bSet) {
                        counterEnd = Toast.makeText(ScoreKeeper.this, getString(R.string.nikolaOverall), Toast.LENGTH_LONG);
                        counterEnd.show();
                    } else {
                        counterEnd = Toast.makeText(ScoreKeeper.this, getString(R.string.tiedRound), Toast.LENGTH_LONG);
                        counterEnd.show();
                    }
                } else {
                    if (scoreTeamA > scoreTeamB) {
                        if (aSet <= 1){
                            aSet = aSet + 1;
                            displaySetA(aSet);
                        }
                        counterEnd = Toast.makeText(ScoreKeeper.this, (stringNameValue + getString(R.string.winsRound)), Toast.LENGTH_LONG);
                        counterEnd.show();
                    } else if (scoreTeamA < scoreTeamB) {
                        if (bSet <= 1){
                            bSet = bSet + 1;
                            displaySetB(bSet);
                        }
                        counterEnd = Toast.makeText(ScoreKeeper.this, getString(R.string.nikolaRound), Toast.LENGTH_LONG);
                        counterEnd.show();
                    } else if (scoreTeamA > scoreTeamB && aSet > bSet) {
                        counterEnd = Toast.makeText(ScoreKeeper.this, (stringNameValue + getString(R.string.overall)), Toast.LENGTH_LONG);
                        counterEnd.show();
                    } else if (scoreTeamA < scoreTeamB && aSet < bSet) {
                        counterEnd = Toast.makeText(ScoreKeeper.this, getString(R.string.nikolaOverall), Toast.LENGTH_LONG);
                        counterEnd.show();
                    } else {
                        counterEnd = Toast.makeText(ScoreKeeper.this, getString(R.string.tiedRound), Toast.LENGTH_LONG);
                        counterEnd.show();
                    }
                }
            }
        };

        // FINDING THE VIEWS FOR TOGGLEBUTTONS
        round1btn = (ToggleButton) findViewById(R.id.start_rnd_1);
        round2btn = (ToggleButton) findViewById(R.id.start_rnd_2);
        round3btn = (ToggleButton) findViewById(R.id.start_rnd_3);

        // CREATING AN ARRAY LIST OF TOGGLEBUTTONS FOR EASIER DATA HANDLING
        final ArrayList <ToggleButton> roundButtons = new ArrayList<ToggleButton>();
        roundButtons.add(round1btn);
        roundButtons.add(round2btn);
        roundButtons.add(round3btn);

        // DISABLING 2 BUTTONS FOR ON CREATE
        roundButtons.get(1).setEnabled(false);
        roundButtons.get(2).setEnabled(false);

        // SETTING ON CLICK LISTENER FOR ROUND BUTTONS TO START TIMERS AND RESET SCORE
        for (int i = 0; i < roundButtons.size(); i++){
            roundButtons.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cTimer.start();
                    mp.start();
                    roundReset2(v);
                }
            });
        }

        // SETTING THE ON CHECKED CHANGED LISTENER FOR ROUND BUTTONS
        for (int i = 0; i < roundButtons.size(); i++){
            roundButtons.get(i).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {

                        // SETTING THE ON CLICK LISTENERS FOR EACH TEAM A BUTTON IN A GROUP
                        for (int i = 0; i < buttonsTeamA.size(); i++) {
                            buttonsTeamA.get(i).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (buttonsTeamA.get(0).isPressed()) {
                                        addTwoForTeamA(v);
                                    } else if (buttonsTeamA.get(1).isPressed()) {
                                        addTwoForTeamA(v);
                                    } else if (buttonsTeamA.get(2).isPressed()) {
                                        addOneForTeamA(v);
                                    } else if (buttonsTeamA.get(3).isPressed()) {
                                        addTwoForTeamA(v);
                                    } else if (buttonsTeamA.get(4).isPressed()) {
                                        addThreeForTeamA(v);
                                    } else {
                                        addOneForTeamB(v);
                                    }
                                }
                            });
                        }

                        // SETTING THE ON CLICK LISTENER TO EACH TEAM B BUTTON IN A GROUP
                        for (int i = 0; i < buttonsTeamB.size(); i++) {
                            buttonsTeamB.get(i).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (buttonsTeamB.get(0).isPressed()) {
                                        addTwoForTeamB(v);
                                    } else if (buttonsTeamB.get(1).isPressed()) {
                                        addTwoForTeamB(v);
                                    } else if (buttonsTeamB.get(2).isPressed()) {
                                        addOneForTeamB(v);
                                    } else if (buttonsTeamB.get(3).isPressed()) {
                                        addTwoForTeamB(v);
                                    } else if (buttonsTeamB.get(4).isPressed()) {
                                        addThreeForTeamB(v);
                                    } else {
                                        addOneForTeamA(v);
                                    }
                                }
                            });
                        }

                        // ENABLING AND DISABLING BUTTONS ON CHEKS
                        if (roundButtons.get(0).isChecked()){
                            roundButtons.get(0).setEnabled(false);
                            roundButtons.get(1).setEnabled(true);
                        }

                        if (roundButtons.get(1).isChecked()){
                            roundButtons.get(1).setEnabled(false);
                            roundButtons.get(2).setEnabled(true);
                        }

                        if (roundButtons.get(2).isChecked()){
                            roundButtons.get(2).setEnabled(false);
                        }

                    }
                }
            });
        }

        // FINDING AND SETTING THE BUTTON VIEW FOR SUBMITTING SCORE
        submitFinalScore = (Button) findViewById(R.id.submit_final_score);
        submitFinalScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (aSet == 2 || bSet == 2){
                    cTimer.cancel();
                    resetBothTeams(v);
                    counterTextView.setText(getString(R.string.pressRoundButton));
                    counterTextView.setTextSize(20);
                    roundButtons.get(0).setEnabled(true);
                    roundButtons.get(0).setChecked(false);
                    roundButtons.get(1).setEnabled(false);
                    roundButtons.get(1).setChecked(false);
                    roundButtons.get(2).setEnabled(false);
                    roundButtons.get(2).setChecked(false);
                }
            }
        });

    }

}
