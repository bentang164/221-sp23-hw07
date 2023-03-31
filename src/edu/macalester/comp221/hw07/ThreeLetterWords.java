package edu.macalester.comp221.hw07;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

// Special thanks: Assistance from Timothy Lang
public class ThreeLetterWords {
    Map<String, List<String>> words = new HashMap<>();
    Map<String, String> parentWords = new HashMap<>();
    Queue<String> bfs = new ArrayDeque<>();

    private void addWords(List<String> input) {
        for (String word : input) {
            List<String> matchingWords = new ArrayList<>();

            for (String checkWord : input) {
                int similarCounter = 0;

                for (int location = 0; location < checkWord.length(); location++) {
                    if (word.charAt(location) == (checkWord.charAt(location))) {
                        similarCounter++;
                    }
                }

                if (similarCounter == 2) {
                    matchingWords.add(checkWord);
                }
            }

            words.put(word, matchingWords);
        }
    }

    private void findPath(String start, String end) {
        List<String> bfsVisited = new ArrayList<>();
        
        bfs.add(start);
        
        while (!bfs.isEmpty()) {
            String head = bfs.remove();
            
            if (head.equals(end)) break;
            
            bfsVisited.add(head);
            
            for (String neighbor : words.get(head)) {
                if (!bfsVisited.contains(neighbor) && !bfs.contains(neighbor)) {
                    bfs.add(neighbor);
                    
                    parentWords.put(neighbor, head);
                }
            }
        }

        backtrace(start, end);
    }

    private List<String> backtrace(String start, String end) {
        List<String> path = new ArrayList<>();

        String currentWord = end;

        while (!currentWord.equals(start)) {
            path.add(0, currentWord);
            
            currentWord = parentWords.get(currentWord);
        }

        path.add(0, start);

        return path;
    }

    public static void main(String[] args) throws Exception {
        ThreeLetterWords tlw = new ThreeLetterWords();
        File input = new File("res/threeletterwords.txt");
        BufferedReader readIn = new BufferedReader(new FileReader(input));
        List<String> contents = new ArrayList<>();
        String line;

        while((line = readIn.readLine()) != null) {
            if (!line.isEmpty()) {
                if (!line.substring(0, 1).equals("#")) {
                    contents.add(line);
                }
            }
        }

        readIn.close(); 

        tlw.addWords(contents);

        tlw.findPath("Car", "Kea");
    }
}
