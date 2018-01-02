package com.leon.creatingaquizappinandroid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Created by Leon on 31.12.2017..
 */

public class QuestionGenerator {
    private int mLeftAdress = 0;
    private int mRightAdress = 0;
    private int mCorrectAnswer = 0;
    private int mFirstIncorrectAnswers = 0;
    private int mSecondIncorrectAnswers = 0;
    private Set<QuestionGenerator> mQuestions = null;

    private Random mRandom = null;
    private static QuestionGenerator mInstance = null;
    public static int MIN_NUMBER = 0;
    public static int MAX_NUMBER = 150;
    private int mCorrectAnswerCount = 0;
    private int mIncorrectAnswerCount = 0;

    private QuestionGenerator() {
        mRandom = new Random();
        mQuestions = new HashSet<>();
        generate();
    }



    public synchronized static QuestionGenerator getInstance() {
        if (mInstance == null) {
            mInstance = new QuestionGenerator();
        }
        return mInstance;
    }

    public synchronized static QuestionGenerator getInstance(int min, int max) {
        MIN_NUMBER = min;
        MAX_NUMBER = max;
        if (mInstance == null) {
            mInstance = new QuestionGenerator();
        }

        return mInstance;
    }

    public void generate() {
        mLeftAdress = randInt(MIN_NUMBER, MAX_NUMBER);
        mRightAdress = randInt(MIN_NUMBER, MAX_NUMBER);
        mCorrectAnswer = getLeftAdress() + getRightAdress();
        mFirstIncorrectAnswers = getCorrectAnswer() - randInt(1, 30);
        mSecondIncorrectAnswers = getCorrectAnswer() + randInt(1, 30);
        if (!mQuestions.add(this)) {
            generate();
        }
    }

    public int getCorrectAnswerCount() {
        return mCorrectAnswerCount;
    }

    public int getIncorrectAnswerCount() {
        return mIncorrectAnswerCount;
    }

    public boolean checkAnswer(int answer) {
        if (answer == getCorrectAnswer()) {
            mCorrectAnswerCount++;
            return true;
        }
        mIncorrectAnswerCount++;
        return false;
    }

    public int getLeftAdress() {
        return mLeftAdress;
    }


    public int getRightAdress() {
        return mRightAdress;
    }

    public int getCorrectAnswer() {
        return mCorrectAnswer;
    }

    public int getFirstIncorrectAnswers() {
        return mFirstIncorrectAnswers;
    }

    public int getSecondIncorrectAnswers() {
        return mSecondIncorrectAnswers;
    }

    private int randInt(int min, int max) {
        int randomNum = mRandom.nextInt((max - min) + 1) + min;
        return randomNum;
    }

    public List<Integer> getAnswers() {
        List<Integer> values = new ArrayList<>();

        values.add(getCorrectAnswer());
        values.add(getFirstIncorrectAnswers());
        values.add(getSecondIncorrectAnswers());

        Collections.shuffle(values);

        return values;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof QuestionGenerator)) {
            return false;
        }

        if (o.getClass() != getClass()) {
            return false;
        }

        QuestionGenerator other = (QuestionGenerator)o;
        if (getLeftAdress() == other.getLeftAdress() && getRightAdress() == other.getRightAdress()) {
            return true;
        }


        return false;
    }


    @Override
    public String toString() {
        return String.format("What is %d + %d?", getLeftAdress(), getRightAdress());
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
