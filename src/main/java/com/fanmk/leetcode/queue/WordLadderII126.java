package com.fanmk.leetcode.queue;

import java.util.*;

/**
 * @program: com.fanmk.interview
 * @author: fanmk
 * @email: comeandgo2014@gmail.com
 * @decription: https://com.fanmk.leetcode.com/problems/word-ladder/ 采用bfs解法，好理解，但复杂度较高
 * @date: 2020/4/4 23:44
 */
public class WordLadderII126 {

    static class Solution {
        public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {

            List<List<String>> res = new ArrayList<>();

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
            List<String> seq = new ArrayList<>();
            queue.offer(new Node(beginWord, seq));

            // Visited to make sure we don't repeat processing same word.
            Map<String, Boolean> visited = new HashMap<>();

            int minDepth = wordList.size();

            while (!queue.isEmpty()) {
                Node cur = queue.poll();
                String word = cur.val;
                List<String> tmp = cur.list;

                // 因为需要得到所有路径 所有只能在真正访问该元素的时候将其设为true
                visited.put(word, true);

                if (tmp.size() > minDepth)
                    continue;

                for (int i = 0; i < L; i++) {
                    // Key is the generic word
                    // Value is a list of words which have the same intermediate generic word.
                    String newWord = word.substring(0, i) + '*' + word.substring(i + 1, L);

                    List<String> list = allComboDict.getOrDefault(newWord, new ArrayList<>());
                    for (String s : list) {
                        if (s.equals(endWord)) {
                            minDepth = tmp.size();

                            List<String> ans = new ArrayList<>(tmp);
                            ans.add(word);
                            ans.add(endWord);
                            res.add(ans);
                        }
                        if (!visited.containsKey(s)) {
                            List<String> ans = new ArrayList<>(tmp);
                            ans.add(word);
                            queue.offer(new Node(s, ans));
                        }
                    }
                }
            }

            return res;
        }

        public List<List<String>> findLadders1(String beginWord, String endWord, List<String> wordList) {

            List<List<String>> res = new ArrayList<>();

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
            List<String> seq = new ArrayList<>();
            seq.add(beginWord);
            queue.offer(new Node(beginWord, seq));

            // Visited to make sure we don't repeat processing same word.
            Set<String> visited = new HashSet<>();
            visited.add(beginWord);

            int minDepth = wordList.size();

            while (!queue.isEmpty()) {
                Node cur = queue.poll();
                String word = cur.val;
                List<String> tmp = cur.list;

                if (tmp.size() > minDepth)
                    continue;

                for (int i = 0; i < L; i++) {
                    // Key is the generic word
                    // Value is a list of words which have the same intermediate generic word.
                    String newWord = word.substring(0, i) + '*' + word.substring(i + 1, L);

                    List<String> list = allComboDict.getOrDefault(newWord, new ArrayList<>());
                    for (String s : list) {
                        if (s.equals(endWord)) {
                            minDepth = tmp.size();

                            List<String> ans = new ArrayList<>(tmp);
                            ans.add(endWord);
                            res.add(ans);
                        }
                        if (!visited.contains(s)) {
                            List<String> ans = new ArrayList<>(tmp);
                            ans.add(s);
                            queue.offer(new Node(s, ans));
                            visited.add(s);
                        }
                    }
                }
            }

            return res;
        }

    }

    static class Node {
        String val;
        List<String> list;

        Node(String val, List<String> list) {
            this.list = list;
            this.val = val;
        }
    }
}

