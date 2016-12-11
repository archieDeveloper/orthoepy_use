package com.shahbazly_dev.orthoepy_use.Models;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.shahbazly_dev.orthoepy_use.Database.DBHelper;

import java.util.ArrayList;
import java.util.Collections;

public class WordsModel {
    private DBHelper dbHelper;
    private ArrayList<String> words = new ArrayList<>();

    public WordsModel(Context context){
        initializeDB(context);
    }

    private void initializeDB(Context context){
        dbHelper = new DBHelper(context, 1);
        dbHelper.getWritableDatabase();
    }

    public ArrayList<String> getWords(int amount){
        ArrayList<String> randomWords = words;
        Cursor cursor_unstudied = dbHelper.getReadableDatabase()
                .rawQuery("SELECT word FROM words WHERE isStudied =0", null);
        if (cursor_unstudied.moveToFirst()){
            int word = cursor_unstudied.getColumnIndex("word");
            do {
                words.add(cursor_unstudied.getString(word));
            } while (cursor_unstudied.moveToNext());
        }
        cursor_unstudied.close();

        Cursor cursor_studied = dbHelper.getReadableDatabase()
                .rawQuery("SELECT word FROM words WHERE study_level < 100", null);
        if (cursor_studied.moveToFirst()){
            int word = cursor_studied.getColumnIndex("word");
            do {
                words.add(cursor_studied.getString(word));
            } while (cursor_studied.moveToNext());
        }
        cursor_studied.close();
        Collections.shuffle(randomWords);
        if (randomWords.size() > amount) {
            randomWords.subList(amount, randomWords.size()).clear();
        }

        return randomWords;
    }

    public void addMistake(String word){
        Cursor cursor = dbHelper.getReadableDatabase()
                .rawQuery("UPDATE words SET mistakes_count = mistakes_count + 1 " +
                        "WHERE word = '" + word + "'",null);
        cursor.moveToFirst();
        cursor.close();
    }

    public void increaseLevel(String word){
        Cursor cursor = dbHelper.getReadableDatabase()
                .rawQuery("UPDATE words SET study_level = study_level + 5 " +
                        "WHERE word = '" + word + "'",null);
        cursor.moveToFirst();
        cursor.close();
    }

    public void reduceLevel(String word){
        Cursor cursor = dbHelper.getReadableDatabase()
                .rawQuery("UPDATE words SET study_level = study_level - 3 " +
                        "WHERE word = '" + word + "'",null);
        cursor.moveToFirst();
        cursor.close();
    }

    public void setStudied(String word){
        Cursor cursor = dbHelper.getReadableDatabase()
                .rawQuery("UPDATE words SET isStudied = 1 " +
                        "WHERE word = '" + word + "'",null);
        cursor.moveToFirst();
        cursor.close();
    }

    public String getTrueLetter(String word) {
        String true_letter = null;
        char[] word_by_letter = word.toCharArray(); // разбиваем слово на буквы
        for(char x:word_by_letter) {
            if (Character.isUpperCase(x)) { // x - правильный ответ(буква)
                true_letter = Character.toString(x);
                Log.e("WORD", word);
                Log.e("CHAR", String.valueOf(x));
                break;
            }
        }
        return true_letter;
    }

    public Boolean isVowelsChar(char charInWord) {
        String vowels = "ёуеыаоэяию";
        Boolean isVowels = false;
        int vowelsLength = vowels.length();
        for (int i = 0; i < vowelsLength; i++) {
            if (vowels.charAt(i) == Character.toLowerCase(charInWord)) {
                isVowels = true;
                break;
            }
        }
        return isVowels;
    }
}
