package com.shahbazly_dev.orthoepy_use;

import android.util.Log;

import java.util.ArrayList;

class TrainManager {
    private WordsModel wordsModel;
    private ArrayList<String> words;
    private int index;
    private int maxIndex;
    private int countErrors;

    TrainManager(WordsModel wordsModel) {
        this.wordsModel = wordsModel;
        restart();
        Log.e("SIZE words", Integer.toString(maxIndex));
    }

    public void restart() {
        words = wordsModel.getRandomWords(10);
        index = 0;
        countErrors = 0;
        maxIndex = words.size();
    }

    WordsModel getWordsModel() {
        return wordsModel;
    }

    public int getIndex() {
        return index;
    }

    public int size() {
        return maxIndex;
    }

    String getTrueLetter() {
        return wordsModel.getTrueLetter(words.get(index - 1));
    }

    boolean hasNextWord() {
        return index < maxIndex;
    }

    String getNextWord() {
        index += 1;
        return words.get(index - 1);
    }

    void addCountError() {
        countErrors += 1;
    }

    int getCountErrors() {
        return countErrors;
    }

    int getProgress() {
        return (int) (((float)index/maxIndex)*100);
    }
}
