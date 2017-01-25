package com.csf.philb.progmobile2_tp1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ColorRandomizer {

    private Random random;
    private int[] colors;
    private int correctAnswerId;
    private int correctColor;
    private PossibleAnswer[] possibleAnswers;

    public ColorRandomizer(){
        random = new Random();
        colors = createColorsArray();
        possibleAnswers = new PossibleAnswer[4];
        for(int i = 0; i < possibleAnswers.length; i++){
            possibleAnswers[i] = new PossibleAnswer();
        }
    }

    public int getCorrectAnswerId(){
        return correctAnswerId;
    }

    public void setCorrectAnswerId(int correctAnswerId) {
        this.correctAnswerId = correctAnswerId;
    }

    public int getCorrectColor(){
        return correctColor;
    }

    public void setCorrectColor(int correctColor) {
        this.correctColor = correctColor;
    }

    public PossibleAnswer[] getPossibleAnswers(){
        return possibleAnswers;
    }

    public void setPossibleAnswers(PossibleAnswer[] possibleAnswers) {
        this.possibleAnswers = possibleAnswers;
    }

    public void randomize(){
        List<Integer> currentRoundColorNamePossibilities = convertIntArrayToIntegerList(colors);
        List<Integer> currentRoundNameDisplayedPossibilities = convertIntArrayToIntegerList(colors);

        correctAnswerId = random.nextInt(4);
        correctColor = getRandomColor();
        currentRoundNameDisplayedPossibilities.remove(new Integer(correctColor));
        possibleAnswers[correctAnswerId].nameDisplayed = correctColor;

        for(int i = 0; i < possibleAnswers.length; i++){
            if(i != correctAnswerId){
                int newColor = getRandomColor();
                while(!currentRoundNameDisplayedPossibilities.contains(newColor)) {
                    newColor = getRandomColor();
                }
                possibleAnswers[i].nameDisplayed = newColor;
                currentRoundNameDisplayedPossibilities.remove(new Integer(newColor));
            }

            //BEN_REVIEW : Peu optimal en terme d'utilisation de la puissance de calcul...
            //             Non pénalisé, car ce n'est pas le but du travail, mais je tiens à le souligner.
            //             Me voir pour les explications détaillées.
            int newColor = getRandomColor();
            while(newColor == possibleAnswers[i].nameDisplayed || !currentRoundColorNamePossibilities.contains(newColor)){
                newColor = getRandomColor();
            }
            currentRoundColorNamePossibilities.remove(new Integer(newColor));
            possibleAnswers[i].nameColor = newColor;
        }
    }

    //BEN_CORRECTION : Données à recevoir à la construction, car R est une classe faisant partie du framework Android.
    private int[] createColorsArray(){
        return new int[]{R.color.red,
                R.color.pink,
                R.color.purple,
                R.color.blue,
                R.color.green,
                R.color.brown,
                R.color.orange};
    }

    private List<Integer> convertIntArrayToIntegerList(int[] array) {
        List<Integer> list = new ArrayList<Integer>();
        for(int i = 0; i < array.length; i++) {
            list.add(array[i]);
        }
        return list;
    }

    private int getRandomColor(){
        return colors[random.nextInt(colors.length)];
    }

    public boolean verifyAnswer(int answerId) {
        return correctAnswerId == answerId;
    }
}
