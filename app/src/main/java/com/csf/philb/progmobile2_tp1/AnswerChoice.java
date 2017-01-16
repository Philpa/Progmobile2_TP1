package com.csf.philb.progmobile2_tp1;

import java.util.Random;

public class AnswerChoice {

    private String text;
    private Random random;
    private int color;

    public AnswerChoice()
    {
        random = new Random();
    }

    public String getText()
    {
        return text;
    }

    public int getColor()
    {
        return color;
    }

    public void randomize()
    {
        int pick = random.nextInt(PossibleChoices.values().length);
        text = PossibleChoices.values()[pick].name();
    }
}
