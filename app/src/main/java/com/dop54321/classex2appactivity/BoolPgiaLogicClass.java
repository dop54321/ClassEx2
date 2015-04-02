package com.dop54321.classex2appactivity;

/**
 * Created by dop54321 on 27/03/2015.
 */
public class BoolPgiaLogicClass {
    //interface field for win the game event
    private WhatHapandWhenWin whatHapandWhenWin;
    private Number thisNumber;


    /**
     * Default game constructor, the number of digits in number is 4.
     */
    public BoolPgiaLogicClass() {
        this(4);
    }


    /**
     * Game constructor, the number of digits in number is howManyDigitsInGame
     *
     * @param howManyDigitsInGame the number of digits in number
     */
    public BoolPgiaLogicClass(int howManyDigitsInGame) {

        if (howManyDigitsInGame < 10 && howManyDigitsInGame > 0) {
            thisNumber = new Number(howManyDigitsInGame);
            thisNumber.generateNewNumber();
        } else if (howManyDigitsInGame >= 10) {
            thisNumber = new Number(9);
            thisNumber.generateNewNumber();
        }

    }

    public Number getThisNumber() {
        return thisNumber;
    }

    public void setWhatHapandWhenWin(WhatHapandWhenWin whatHapandWhenWinInterface) {
        whatHapandWhenWin = whatHapandWhenWinInterface;
    }


    public String StartNewGame() {
        Number number = thisNumber.generateNewNumber();
        return number.getThisNumber();
    }

    public boolean isValid(String gussedNumber) {
        int intGussedNumber;
        try {
            intGussedNumber = Integer.parseInt(gussedNumber);
        } catch (Exception e) {
            return false;
        }
        int defaultNumOfDigits = thisNumber.getDefaultNumOfDigits();
        if (gussedNumber.length() != defaultNumOfDigits)
            return false;

        if (intGussedNumber < 0)
            return false;
        for (int i = 0; i < defaultNumOfDigits; i++) {
            char c = gussedNumber.charAt(i);
            for (int j = 0; j < defaultNumOfDigits; j++) {
                if (j != i && gussedNumber.charAt(j) == c)
                    return false;
            }
        }


        return true;
    }

    /**
     * @param guessedNumber
     * @return number of times the gussed digit and the random number digit at same index are
     * equal.
     */
    public int howManyBools(String guessedNumber) throws Exception {
        Number otherNumber = new Number(guessedNumber.length());
        otherNumber.setThisNumber(guessedNumber);
        if (!isValid(guessedNumber))
            throw new IllegalArgumentException("Arg number is not leagal");

        int boolCounter = thisNumber.howManyBools(otherNumber);

        if (boolCounter == thisNumber.getDefaultNumOfDigits())
            if (whatHapandWhenWin != null) {
                whatHapandWhenWin.youWin();
            }
        return boolCounter;

    }

    public int howManyHits(String guessedNumber) throws Exception {
        Number other = new Number(guessedNumber.length());
        other.setThisNumber(guessedNumber);
        if (!isValid(guessedNumber))
            throw new IllegalArgumentException("Arg number is not leagal");
        int almostCounter = thisNumber.howManyHits(other);

        return almostCounter;
    }


    public interface WhatHapandWhenWin {
        public void youWin();
    }

    //test my class
    public static void main(String[] args) {
        BoolPgiaLogicClass boolPgiaLogicClass = new BoolPgiaLogicClass();

        boolPgiaLogicClass.setWhatHapandWhenWin(new WhatHapandWhenWin() {
            @Override
            public void youWin() {
                System.out.println("you win!!");
            }
        });

        //generate 4 random unique digits and return string of that number
        //String s = boolPgiaLogicClass.RandomGenerator();
        //System.out.println("result: "+s);

        //the return String is only for the testing
        String s = boolPgiaLogicClass.StartNewGame();
        System.out.println("the random number is: " + s);
//
//        String guess="236719";
//        String winGuess=s;
        int numOfBool = 0;
        int numOfHits = 0;
//        try {
//            numOfBool = boolPgiaLogicClass.howManyBools(guess);
//            numOfHits = boolPgiaLogicClass.howManyHits(guess);
//        } catch (IllegalArgumentException e) {
//            System.out.println("ERROR: "+e.getMessage());
//            return;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        System.out.println("your guess is: "+guess);
//        System.out.println("you have: "+numOfBool+" bools");
//        System.out.println("you have: "+numOfPgiot+" Pgiot");
        String start = "0123";
        boolean leagal = true;
        String guess = start;
        int startint= Integer.parseInt(start);
        while (numOfBool != 4 && startint < 10000) {
            try {
                numOfBool = boolPgiaLogicClass.howManyBools(guess);
                numOfHits = boolPgiaLogicClass.howManyHits(guess);
            } catch (IllegalArgumentException e) {
                //System.out.println("ERROR: " + e.getMessage());
                leagal = false;
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (!leagal) {
                startint= Integer.parseInt(guess);
                startint++;
                guess = String.valueOf(startint);
                if (startint<1000) {
                    guess = String.valueOf(startint);
                    guess="0"+guess;
                }
                leagal=true;
                continue;
            }

            System.out.println("guess: " + guess);

            System.out.println("---bools: " + numOfBool);
            System.out.println("---hits: " + numOfHits);

            startint= Integer.parseInt(guess);
            startint++;
            if (startint<1000) {
                guess = String.valueOf(startint);
                guess="0"+guess;
            }
            else
                guess = String.valueOf(startint);
        }

    }


}

