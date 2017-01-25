package com.csf.philb.progmobile2_tp1;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ImageView imgvColorPreview;
    private TextView txtvColor1;
    private TextView txtvColor2;
    private TextView txtvColor3;
    private TextView txtvColor4;

    private TextView[] answerTextViews;

    private MediaPlayer soundGoodPing;
    private MediaPlayer soundWrongPing;

    private ColorRandomizer colorRandomizer;

    private int[] colorResourceIdentifiers;

    //BEN_REVIEW : Mettre les constantes en premier dans les fichiers. C'est plus courant ainsi.
    private static final String CORRECT_ANSWER_ID_KEY = "CORRECT_ANSWER_ID";
    private static final String CORRECT_COLOR_KEY = "CORRECT_COLOR";
    private static final String POSSIBLE_ANSWERS_KEY = "POSSIBLE_ANSWERS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        colorRandomizer = new ColorRandomizer();

        imgvColorPreview = (ImageView)findViewById(R.id.imgvColorPreview);
        txtvColor1 = (TextView)findViewById(R.id.txtvColor1);
        txtvColor2 = (TextView)findViewById(R.id.txtvColor2);
        txtvColor3 = (TextView)findViewById(R.id.txtvColor3);
        txtvColor4 = (TextView)findViewById(R.id.txtvColor4);
        answerTextViews = new TextView[]{txtvColor1, txtvColor2, txtvColor3, txtvColor4};

        txtvColor1.setOnClickListener(verifyPossibleAnswer1);
        txtvColor2.setOnClickListener(verifyPossibleAnswer2);
        txtvColor3.setOnClickListener(verifyPossibleAnswer3);
        txtvColor4.setOnClickListener(verifyPossibleAnswer4);

        soundGoodPing = MediaPlayer.create(this, R.raw.good_ping);
        soundWrongPing = MediaPlayer.create(this, R.raw.wrong_ping);

        startGameBoard();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CORRECT_ANSWER_ID_KEY, colorRandomizer.getCorrectAnswerId());
        outState.putInt(CORRECT_COLOR_KEY, colorRandomizer.getCorrectColor());
        //BEN_REVIEW : Préfère "Parcelable" à "Serializable". Parcelable est plus performant, mais nécessite plus de travail.
        outState.putSerializable(POSSIBLE_ANSWERS_KEY, colorRandomizer.getPossibleAnswers());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        colorRandomizer.setCorrectAnswerId(savedInstanceState.getInt(CORRECT_ANSWER_ID_KEY));
        colorRandomizer.setCorrectColor(savedInstanceState.getInt(CORRECT_COLOR_KEY));
        colorRandomizer.setPossibleAnswers((PossibleAnswer[])savedInstanceState.getSerializable(POSSIBLE_ANSWERS_KEY));

        setupGameBoard();
    }

    private String getColorText(int colorId) {
        if(colorId == R.color.red) {
            return getResources().getString(R.string.color_red);
        }
        else if(colorId == R.color.pink) {
            return getResources().getString(R.string.color_pink);
        }
        else if(colorId == R.color.purple) {
            return getResources().getString(R.string.color_purple);
        }
        else if(colorId == R.color.blue) {
            return getResources().getString(R.string.color_blue);
        }
        else if(colorId == R.color.green) {
            return getResources().getString(R.string.color_green);
        }
        else if(colorId == R.color.brown) {
            return getResources().getString(R.string.color_brown);
        }
        else if(colorId == R.color.orange) {
            return getResources().getString(R.string.color_orange);
        }
        else {
            return "";
        }
    }

    private void startGameBoard(){
        colorRandomizer.randomize();

        setupGameBoard();
    }

    private void setupGameBoard() {
        //BEN_CORRECTION : Utilisation d'une méthode "deprecated".
        imgvColorPreview.setColorFilter(getResources().getColor(colorRandomizer.getCorrectColor()));

        PossibleAnswer[] possibleAnswers = colorRandomizer.getPossibleAnswers();
        for(int i = 0; i < possibleAnswers.length; i++){
            answerTextViews[i].setText(getColorText(possibleAnswers[i].nameDisplayed));
            //BEN_CORRECTION : Utilisation d'une méthode "deprecated".
            answerTextViews[i].setTextColor(getResources().getColor(possibleAnswers[i].nameColor));
        }
    }

    private View.OnClickListener verifyPossibleAnswer1 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            verifyAnswer(0);
        }
    };

    private View.OnClickListener verifyPossibleAnswer2 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            verifyAnswer(1);
        }
    };

    private View.OnClickListener verifyPossibleAnswer3 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            verifyAnswer(2);
        }
    };

    private View.OnClickListener verifyPossibleAnswer4 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            verifyAnswer(3);
        }
    };

    private void verifyAnswer(int answerId) {
        if(colorRandomizer.verifyAnswer(answerId)) {
            soundGoodPing.start();
            //BEN_CORRECTION : Dans tous les cas, le "GameBoard" doit être modifié. C'est demandé dans l'énoncé.
            startGameBoard();
        }
        else {
            soundWrongPing.start();
        }
    }
}
