package com.fanmk.leetcode.queue;

import java.util.*;

/**
 * @program: com.fanmk.interview
 * @author: fanmk
 * @email: comeandgo2014@gmail.com
 * @decription: https://com.fanmk.leetcode.com/problems/word-ladder/
 * @date: 2020/4/4 23:44
 */
public class WordLadder127 {

    static class Solution {
        public int ladderLength(String beginWord, String endWord, List<String> wordList) {
            // Since all words are of same length.
            int L = beginWord.length();

            // Dictionary to hold combination of words that can be formed,
            // from any given word. By changing one letter at a time.
            Map<String, List<String>> allComboDict = new HashMap<>();

            wordList.forEach(
                    word -> {
                        for (int i = 0; i < L; i++) {
                            // Key is the generic word
                            // Value is a list of words which have the same intermediate generic word.
                            String newWord = word.substring(0, i) + '*' + word.substring(i + 1, L);
                            List<String> transformations = allComboDict.getOrDefault(newWord, new ArrayList<>());
                            transformations.add(word);
                            allComboDict.put(newWord, transformations);
                        }
                    });

            Queue<Node> queue = new LinkedList<>();
            queue.offer(new Node(beginWord, 1));

            // Visited to make sure we don't repeat processing same word.
            Map<String, Boolean> visited = new HashMap<>();
            visited.put(beginWord, true);

            while (!queue.isEmpty()) {
                Node cur = queue.poll();
                String word = cur.val;
                int step = cur.step;

                for (int i = 0; i < L; i++) {
                    // Key is the generic word
                    // Value is a list of words which have the same intermediate generic word.
                    String newWord = word.substring(0, i) + '*' + word.substring(i + 1, L);

                    List<String> list = allComboDict.getOrDefault(newWord, new ArrayList<>());
                    for (String s : list) {
                        if (s.equals(endWord)) {
                            return step + 1;
                        }
                        if (!visited.containsKey(s)) {
                            queue.offer(new Node(s, step + 1));
                            visited.put(s, true);
                        }
                    }
                }
            }

            return 0;
        }

    }

    static class Node {
        String val;
        int step;

        Node(String val, int step) {
            this.step = step;
            this.val = val;
        }
    }
}

