package com.shahbazly_dev.orthoepy_use;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

import static android.R.attr.data;
import static android.R.attr.listChoiceBackgroundIndicator;


class WordsModel {

    private ArrayList<String> words = new ArrayList<String>();

    WordsModel(InputStream inputStream) {
        loadWords(inputStream);
    }

    private void loadWords(InputStream inputStream) {
        byte[] buffer;

        try {
            //перевод содержания assets/words.txt в String
            int size = inputStream.available();
            buffer = new byte[size];

            inputStream.read(buffer);
            inputStream.close();

            //Строка со словами из words.txt
            String str_data = new String(buffer);

            //String в ArrayList
            Scanner scan = new Scanner(str_data);
            while (scan.hasNext()) {
                words.add(scan.next());
            }
            scan.close();

            String str = Integer.toString(words.size());
            Log.d("Количество слов в базе",str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getWords(){
        return words;
    }

    String getRandomWord() {
        // Инициализируем генератор случайного числа
        Random rnd = new Random(System.currentTimeMillis());
        // Получаем случайное число в диапазоне от min до max (включительно)
        int max = words.size();
        int min = 0;
        int random = min + rnd.nextInt(max - min + 1);

        return words.get(random);
    }

    ArrayList<String> getRandomWords(int amount) {
        ArrayList<String> randomWords = new ArrayList<String>(words);
        Collections.shuffle(randomWords);
        if (randomWords.size() > amount) {
            randomWords.subList(amount, randomWords.size()).clear();
        }
        return randomWords;
    }

    String getTrueLetter(String word) {
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

    Boolean isVowelsChar(char charInWord) {
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