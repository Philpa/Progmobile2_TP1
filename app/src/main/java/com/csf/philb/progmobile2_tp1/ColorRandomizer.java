package com.csf.philb.progmobile2_tp1;

import java.util.ArrayList;
import java.util.Random;

public class ColorRandomizer {

    private Random random;
    private ArrayList<String> colors;
    private int correctAnswerId;
    private int correctColor;
    private String correctColorName;
    private PossibleAnswer[] possibleAnswers;

    public ColorRandomizer(){
        random = new Random();
        colors = new ArrayList<String>();
        populateColorsList();
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

    private void cloneColorsArray(ArrayList<String> receiver){
        for(int i = 0; i < colors.size(); i++){
            receiver.add(i, colors.get(i));
        }
    }

    public void randomize(){
        ArrayList<String> currentRoundColorNamePossibilities = new ArrayList<String>();
        ArrayList<String> currentRoundNameDisplayedPossibilities = new ArrayList<String>();
        cloneColorsArray(currentRoundColorNamePossibilities);
        cloneColorsArray(currentRoundNameDisplayedPossibilities);

        correctAnswerId = random.nextInt(4);
        correctColorName = randomColorName();
        correctColor = getColorId(correctColorName);
        currentRoundNameDisplayedPossibilities.remove(correctColorName);
        possibleAnswers[correctAnswerId].nameDisplayed = correctColorName;

        for(int i = 0; i < possibleAnswers.length; i++){
            if(i != correctAnswerId){
                String newColorName = randomColorName();
                while(!currentRoundNameDisplayedPossibilities.contains(newColorName)) {
                    newColorName = randomColorName();
                }
                possibleAnswers[i].nameDisplayed = newColorName;
                currentRoundNameDisplayedPossibilities.remove(newColorName);
            }

            String newColorName = randomColorName();
            while(newColorName == possibleAnswers[i].nameDisplayed || !currentRoundColorNamePossibilities.contains(newColorName)){
                newColorName = randomColorName();
            }
            currentRoundColorNamePossibilities.remove(newColorName);
            possibleAnswers[i].nameColor = getColorId(newColorName);
        }
    }

    private void populateColorsList(){
        colors.add("red");
        colors.add("pink");
        colors.add("purple");
        colors.add("blue");
        colors.add("green");
        colors.add("brown");
        colors.add("orange");
    }

    private int getColorId(String color){
        if(color == "red"){
            return R.color.red;
        }
        else if(color == "pink"){
            return R.color.pink;
        }
        else if(color == "purple"){
            return R.color.purple;
        }
        else if(color == "blue"){
            return R.color.blue;
        }
        else if(color == "green"){
            return R.color.green;
        }
        else if(color == "brown"){
            return R.color.brown;
        }
        else if(color == "orange"){
            return R.color.orange;
        }
        else{
            return 0;
        }
    }

    private String randomColorName(){
        return colors.get(random.nextInt(colors.size()));
    }

    public boolean verifyAnswer(int answerId) {
        return correctAnswerId == answerId;
    }
}
