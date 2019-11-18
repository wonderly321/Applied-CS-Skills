/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.anagrams;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();
    private HashSet<String> words = new HashSet<String>();

    public AnagramDictionary(Reader reader) throws IOException {
        BufferedReader in = new BufferedReader(reader);
        String line;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            boolean flag = true;
            for(int i = 0; i < word.length(); i++) {
                if((int)(word.charAt(i) - 'z') > 0) flag = false;
            }
            if(flag) words.add(word);
        }
    }

    public boolean isGoodWord(String word, String base) {
        int[] times = new int[26];
        for(int i = 0; i < word.length(); i++) {
            times[(int)(word.charAt(i) - 'a')] += 1;
        }
        for(int i = 0; i < base.length(); i++) {
            times[(int)(base.charAt(i) - 'a')] -= 1;
        }
        int flag = 0;
        for(int i = 0; i < 26; i++) {
            if(times[i] != 0 && times[i] != 1) return false;
            if(times[i] == 1) flag++;
        }
        if(flag != 1) return false;
        return true;
    }

    public List<String> getAnagrams(String targetWord) {
        ArrayList<String> result = new ArrayList<String>();
        for (String w:words) {
            //Log.d("INFO", "!!!" + w);
            if(isGoodWord(w, targetWord)) result.add(w);
        }
        return result;
    }

    public List<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();
        return result;
    }

    public String pickGoodStarterWord() {
        int n = random.nextInt(words.size());
        String ret = (String)words.toArray()[n];
        List<String> ls = getAnagrams(ret);
        while(ls.size() < 5) {
            n = random.nextInt(words.size());
            ret = (String)words.toArray()[n];
            ls = getAnagrams(ret);
            for (String ans:
                 ls) {
                Log.d("INFO", "!!!" + ans);
            }
        }
        return ret;
    }
}
