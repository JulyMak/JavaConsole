import java.io.*;

import static org.junit.Assert.*;


public class UniqTest {

    String inputName = "in.txt";
    String outputName = "out.txt";

    private void generateInputFile(String[] lines) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(inputName))) {
            for (String line : lines) {
                bw.write(line);
                bw.newLine();
            }
        }
    }

    @org.junit.Test
    public void filter() throws Exception {
        String[] lines = {"aa", "aa", "bb"};
        String[] expectedLines = { "bb"};
        generateInputFile(lines);
        Uniq u = new Uniq(false, true, false, 0);
        u.filter(inputName, outputName);
        checkOutputFile(expectedLines);
    }

    @org.junit.Test
    public void filter2() throws Exception {
        String[] lines = {"aa", "Aa", "bb"};
        String[] expectedLines = {"2\taa", "1\tbb"};
        generateInputFile(lines);
        Uniq u = new Uniq(true, false, true, 0);
        u.filter(inputName, outputName);
        checkOutputFile(expectedLines);
    }
    @org.junit.Test
    public void filter3() throws Exception {
        String[] lines = {"lojjjjjjj","opjjjjjjJ", "aab","aab"};
        String[] expectedLines = {"lojjjjjjj", "aab"};
        generateInputFile(lines);
        Uniq u = new Uniq(true, false, false, 2);
        u.filter(inputName, outputName);
        checkOutputFile(expectedLines);
    }

    @org.junit.Test
    public void filter4() throws Exception {
        String[] lines = {"aaa","aaa", "aab","aab","fk","Fk"};
        String[] expectedLines = {"aaa", "aab","fk","Fk"};
        generateInputFile(lines);
        Uniq u = new Uniq(false, false, false, 0);
        u.filter(inputName, outputName);
        checkOutputFile(expectedLines);
    }

    private void checkOutputFile(String[] expectedLines) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(outputName))) {
            String line;
            int i = 0;
            while ((line = bufferedReader.readLine()) != null) {
                assertEquals(line, expectedLines[i++]);
            }
        }
    }


}