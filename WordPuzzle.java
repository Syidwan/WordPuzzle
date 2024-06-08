import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class WordPuzzle {
    private static String[][] arrayStrings;

    public static void main(String[] args) {
        readArrayFromFile("array_strings.txt");
        printArray(arrayStrings);
        String[] words = {"MARS", "MERCURY", "JUPITER", "EARTH"};
        wordSearch(words);
    }

    public static void readArrayFromFile(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            int rowCount = 0;
            int colCount = -1;

            // Count rows and maximum columns
            while ((line = br.readLine()) != null) {
                rowCount++;
                String[] tokens = line.split("\\s+"); // Split by whitespace
                if (colCount == -1) {
                    colCount = tokens.length;
                } else {
                    colCount = Math.max(colCount, tokens.length);
                }
            }

            // Initialize the 2D array
            arrayStrings = new String[rowCount][colCount];

            // Read file 
            br.close(); // Close the previous reader
            BufferedReader newBr = new BufferedReader(new FileReader(fileName)); // Create new reader
            int rowIndex = 0;
            while ((line = newBr.readLine()) != null) {
                String[] tokens = line.split("\\s+");
                for (int colIndex = 0; colIndex < tokens.length; colIndex++) {
                    arrayStrings[rowIndex][colIndex] = tokens[colIndex];
                }
                rowIndex++;
            }
            newBr.close(); // Close the new reader
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void printArray(String[][] array) {
        System.out.println("PUZZLE");
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                System.out.print(array[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
    

    private static void wordSearch(String[] words) {
        System.out.println("Word positions:");

        int rows = arrayStrings.length;
        int cols = arrayStrings[0].length;
        String[][] positions = new String[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                positions[i][j] = "-";
            }
        }

        // horizontal (kiri ke kanan)
        for (String word : words) {
            for (int i = 0; i < rows; i++) {
                String rowString = String.join("", arrayStrings[i]);
                int index = rowString.indexOf(word);
                if (index != -1) {
                    for (int j = 0; j < word.length(); j++) {
                        positions[i][index + j] = String.valueOf(word.charAt(j));
                    }
                }
            }
        }

        // reversed horizontal (kanan ke kiri)
        for (String word : words) {
            String reversedWord = new StringBuilder(word).reverse().toString();
            for (int i = 0; i < rows; i++) {
                String rowString = String.join("", arrayStrings[i]);
                int index = rowString.indexOf(reversedWord);
                
                if (index != -1) {
                    for (int j = 0; j < reversedWord.length(); j++) {
                        positions[i][index + j] = String.valueOf(reversedWord.charAt(j));
                    }
                }
            }
        }

        // vertical (atas ke bawah)
        for (String word : words) {
            for (int i = 0; i < cols; i++) {
                StringBuilder colString = new StringBuilder();
                for (int j = 0; j < rows; j++) {
                    colString.append(arrayStrings[j][i]);
                }
                int index = colString.toString().indexOf(word);
                if (index != -1) {
                    for (int j = 0; j < word.length(); j++) {
                        positions[index + j][i] = String.valueOf(word.charAt(j));
                    }
                }
            }
        }

       // reversed vertical (bawah ke atas)
        for (String word : words) {
            String reversedWord = new StringBuilder(word).reverse().toString();
            for (int i = 0; i < cols; i++) {
                StringBuilder colString = new StringBuilder();
                for (int j = 0; j < rows; j++) {
                    colString.append(arrayStrings[j][i]);
                }
                int index = colString.toString().indexOf(reversedWord);
                if (index != -1) {
                    for (int j = 0; j < reversedWord.length(); j++) {
                        positions[index + j][i] = String.valueOf(reversedWord.charAt(j));
                    }
                }
            }
        }

        // diagonal (kiri atas ke kanan bawah)
        for (String word : words) {
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    StringBuilder diagString = new StringBuilder();
                    for (int k = 0; k < word.length() && i + k < rows && j + k < cols; k++) {
                        diagString.append(arrayStrings[i + k][j + k]);
                    }
                    if (diagString.toString().equals(word)) {
                        for (int k = 0; k < word.length(); k++) {
                            positions[i + k][j + k] = String.valueOf(word.charAt(k));
                        }
                    }
                }
            }
        }

        // diagonal (kanan-bawah ke kiri-atas)
        for (String word : words) {
            for (int i = rows - 1; i >= 0; i--) {
                for (int j = cols - 1; j >= 0; j--) {
                    StringBuilder diagString = new StringBuilder();
                    for (int k = 0; k < word.length() && i - k >= 0 && j - k >= 0; k++) {
                        diagString.append(arrayStrings[i - k][j - k]);
                    }
                    if (diagString.toString().equals(word)) {
                        for (int k = 0; k < word.length(); k++) {
                            positions[i - k][j - k] = String.valueOf(word.charAt(k));
                        }
                    }
                }
            }
        }
        
        // diagonal (kiri bawah ke kanan atas)
        for (String word : words) {
            for (int i = rows - 1; i >= 0; i--) {
                for (int j = 0; j < cols; j++) {
                    StringBuilder diagString = new StringBuilder();
                    for (int k = 0; k < word.length() && i - k >= 0 && j + k < cols; k++) {
                        diagString.append(arrayStrings[i - k][j + k]);
                    }
                    if (diagString.toString().equals(word)) {
                        for (int k = 0; k < word.length(); k++) {
                            positions[i - k][j + k] = String.valueOf(word.charAt(k));
                        }
                    }
                }
            }
        }
                   

        // diagonal (kanan atas ke kiri bawah)
        for (String word : words) {
            for (int i = 0; i < rows; i++) {
                for (int j = cols - 1; j >= 0; j--) {
                    StringBuilder diagString = new StringBuilder();
                    for (int k = 0; k < word.length() && i + k < rows && j - k >= 0; k++) {
                        diagString.append(arrayStrings[i + k][j - k]);
                    }
                    if (diagString.toString().equals(word)) {
                        for (int k = 0; k < word.length(); k++) {
                            positions[i + k][j - k] = String.valueOf(word.charAt(k));
                        }
                    }
                }
            }
        }

        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(positions[i][j] + " ");
            }
            System.out.println();
        }
    }
}


