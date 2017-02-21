package com.shahbazly_dev.orthoepy_use.Managers;

import android.util.Log;

import com.shahbazly_dev.orthoepy_use.Models.WordsModel;

import java.util.ArrayList;

public class StudyManager {

    private WordsModel wordsModel;
    private ArrayList<String> words;
    private int index;
    private int maxIndex;
    private int countErrors;

    public StudyManager(WordsModel wordsModel) {
        this.wordsModel = wordsModel;
        restart();
        Log.e("SIZE words", Integer.toString(maxIndex));
    }

    private void restart() {
        words = wordsModel.getWords(10);
        index = 0;
        countErrors = 0;
        maxIndex = words.size();
    }

    public WordsModel getWordsModel() {
        return wordsModel;
    }

    public int getIndex() {
        return index;
    }

    public int size() {
        return maxIndex;
    }

    public String getTrueLetter() {
        return wordsModel.getTrueLetter(words.get(index - 1));
    }

    public boolean hasNextWord() {
        return index < maxIndex;
    }

    public String getNextWord() {
        index += 1;
        return words.get(index - 1);
    }

    public void addMistake(String word) {
        wordsModel.addMistake(word);
    }

    public void increaseLevel(String word){
        wordsModel.increaseLevel(word);
    }

    public void reduceLevel(String word){
        wordsModel.reduceLevel(word);
    }

    public void setStudied(String word){
        wordsModel.setStudied(word);
    }

    public int getCountErrors() {
        return countErrors;
    }

    public int getProgress() {
        return (int) (((float)index/maxIndex)*100);
    }
}
