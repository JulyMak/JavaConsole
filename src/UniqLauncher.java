import org.kohsuke.args4j.*;

import java.io.IOException;

public class UniqLauncher {
    @Option(name = "-i")
    private boolean ignoreCase;

    @Option(name = "-u")
    private boolean onlyUniqStrings;

    @Option(name = "-c")
    private boolean countStrings;

    @Option(name = "-o", metaVar = "outputFileName")
    private String outputFileName;

    @Option(name = "-s")
    private int amountToSkip;

    @Argument(required = false)
    private String inputFileName;

    public static void main(String[] args) {
        new UniqLauncher().launch(args);
    }

    private void launch(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);

        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("java -jar Uniq.jar [-i] [-u] [-c] [-s N] [-o OutputFileName] [InputFileName]");
            parser.printUsage(System.err);
            return;
        }

        Uniq uniq = new Uniq(ignoreCase, onlyUniqStrings, countStrings, amountToSkip);

        try {
            uniq.filter(inputFileName, outputFileName);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
