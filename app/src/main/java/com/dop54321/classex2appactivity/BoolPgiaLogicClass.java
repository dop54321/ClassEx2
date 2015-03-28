package com.dop54321.classex2appactivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by dop54321 on 27/03/2015.
 */
public class BoolPgiaLogicClass  {
    //interface field for win the game event
    private WhatHapandWhenWin whatHapandWhenWin;
    //how many digits for game
    private int defaultNumOfDigits = 4;

    private String currentRandomNumber;

    /**
     * Default game constructor, the number of digits in number is 4.
     */
    public BoolPgiaLogicClass() {

    }

    public void setWhatHapandWhenWin(WhatHapandWhenWin whatHapandWhenWinInterface){
        whatHapandWhenWin=whatHapandWhenWinInterface;
    }

    /**
     * Game constructor, the number of digits in number is howManyDigitsInGame
     * @param howManyDigitsInGame the number of digits in number
     */
    public BoolPgiaLogicClass(int howManyDigitsInGame) {

        if (howManyDigitsInGame<10&&howManyDigitsInGame>0) {
            this.defaultNumOfDigits = howManyDigitsInGame;
        } else if (howManyDigitsInGame>=10){
            this.defaultNumOfDigits =9;
        }

    }

    public String StartNewGame(){
        currentRandomNumber=RandomGenerator();
        return currentRandomNumber;
    }

    public boolean isValid(String gussedNumber) {
        int intGussedNumber;
        try {
            intGussedNumber = Integer.parseInt(gussedNumber);
        } catch (Exception e) {
            return false;
        }
        if (gussedNumber.length() != this.defaultNumOfDigits)
            return false;

        if (intGussedNumber < 0)
            return false;
        for (int i = 0; i < this.defaultNumOfDigits; i++) {
            char c = gussedNumber.charAt(i);
            for (int j = 0; j < this.defaultNumOfDigits; j++) {
                if (j != i && gussedNumber.charAt(j) == c)
                    return false;
            }
        }


        return true;
    }

    /**
     *
     * @param guessedNumber
     * @return number of times the gussed digit and the random number digit at same index are
     * equal.
     */
    public int howManyHits(String guessedNumber) throws Exception{
        if (!isValid(guessedNumber))
            throw new IllegalArgumentException("Arg number is not leagal");

        int boolCounter = 0;
        for (int i = 0; i < guessedNumber.length(); i++) {
             if (currentRandomNumber.charAt(i)==guessedNumber.charAt(i)){
                 boolCounter++;
             }
        }
        if (boolCounter==this.defaultNumOfDigits)
            if (whatHapandWhenWin!=null){
                whatHapandWhenWin.youWin();
            }
        return boolCounter;
    }
    
    public int howManyAlmosts(String guessedNumber) throws Exception {
        if (!isValid(guessedNumber))
            throw new IllegalArgumentException("Arg number is not leagal");
        int almostCounter = 0;
        for (int i = 0; i < guessedNumber.length(); i++) {
            char c = guessedNumber.charAt(i);
            boolean isCurrentCharIsInTheRandomNumber =
                    currentRandomNumber.contains(String.valueOf(c));

            if (isCurrentCharIsInTheRandomNumber
                    && currentRandomNumber.charAt(i)!=c)
                almostCounter++;
        }
        return almostCounter;
    }
    
    /**
     *
     * @return 4 unique digit number as string
     */
    public String RandomGenerator() {
        List<Integer> numbers = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            numbers.add(i);
        }

        Collections.shuffle(numbers);

        String result = "";
        for(int i = 0; i < this.defaultNumOfDigits; i++){
            result += numbers.get(i).toString();
        }
        return result;
    }

    public interface WhatHapandWhenWin {
        public void youWin();
    }

//test my class
    public static void main(String[] args) {
        BoolPgiaLogicClass boolPgiaLogicClass=new BoolPgiaLogicClass();

        boolPgiaLogicClass.setWhatHapandWhenWin(new WhatHapandWhenWin() {
            @Override
            public void youWin() {
                System.out.println("you win!!");
            }
        });

        ////generate 4 random unique digits and return string of that number
        //String s = boolPgiaLogicClass.RandomGenerator();
        //System.out.println("result: "+s);

        //the return String is only for the testing
        String s = boolPgiaLogicClass.StartNewGame();
        System.out.println("the random number is: "+s);

        String guess="23567";
        String winGuess=s;
        int numOfBool= 0;
        int numOfPgiot= 0;
        try {
            numOfBool = boolPgiaLogicClass.howManyHits(winGuess);
            numOfPgiot = boolPgiaLogicClass.howManyAlmosts(winGuess);
        } catch (IllegalArgumentException e) {
            System.out.println("ERROR: "+e.getMessage());
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("your guess is: "+guess);
        System.out.println("you have: "+numOfBool+" bools");
        System.out.println("you have: "+numOfPgiot+" Pgiot");


    }



}

