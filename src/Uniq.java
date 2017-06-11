import java.io.*;

public class Uniq {
    private final boolean ignoreCase;
    private final boolean onlyUniqStrings;
    private final boolean countStrings;
    private final int amountToSkip;

    public Uniq(boolean ignoreCase, boolean onlyUniqStrings, boolean countStrings, int amountToSkip) {
        this.ignoreCase = ignoreCase;
        this.onlyUniqStrings = onlyUniqStrings;
        this.countStrings = countStrings;
        this.amountToSkip = amountToSkip;
    }

    public void filter(String inputName, String outputName) throws IOException {
        try (InputStream inputStream = (inputName == null) ? System.in : new FileInputStream(inputName)) {
            try (OutputStream outputStream = (outputName == null) ? System.out : new FileOutputStream(outputName)) {
                filter(inputStream, outputStream);
            }
        }
    }

    public void filter(InputStream inputStream, OutputStream outputStream) throws IOException {
        String prevLine = null;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outputStream))) {
                int counter = 0;
                String currentLine;
                while ((currentLine = br.readLine()) != null) {
                    if (areEqual(currentLine, prevLine)) {
                        counter++;
                    } else {
                        printCountedLine(prevLine, counter, bw);
                        prevLine = currentLine;
                        counter = 1;
                    }
                }
                printCountedLine(prevLine, counter, bw);
            }
        }
    }

    private boolean areEqual(String currentLine, String prevLine) {
        if (prevLine == null) return false;
        currentLine = currentLine.substring(Math.min(amountToSkip, currentLine.length()));
        prevLine = prevLine.substring(Math.min(amountToSkip, prevLine.length()));
        if (!ignoreCase) {
            return currentLine.equals(prevLine);
        } else {
            return currentLine.toLowerCase().equals(prevLine.toLowerCase());
        }
    }

    private void printCountedLine(String line, int counter, BufferedWriter bw) throws IOException {
        if (line != null) {
            if (onlyUniqStrings && counter > 1) return;
            if (countStrings)
                bw.write("" + counter + "\t");
            if (ignoreCase) {
                line = line.toLowerCase();
            }
           /* if (amountToSkip > 0 && counter > 1 && line.length() > amountToSkip) {
                int endIndex = Math.min(line.length(), amountToSkip);
                line = line.substring(0, endIndex).replaceAll(".", "*") + line.substring(endIndex);
            }*/
            bw.write(line);
            bw.newLine();
            bw.flush();
        }
    }
}




