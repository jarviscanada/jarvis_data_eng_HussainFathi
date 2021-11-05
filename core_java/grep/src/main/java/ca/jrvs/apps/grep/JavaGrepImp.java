package ca.jrvs.apps.grep;

import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaGrepImp implements JavaGrep {

    final Logger logger = LoggerFactory.getLogger(JavaGrep.class);

    private String regex;
    private String rootPath;
    private String outFile;

    public static void main(String[] args) {
        if (args.length != 3){
            throw new IllegalArgumentException("Usage: JavaGrep regex rootPath outFile");
        }

        //Use default logger config
        BasicConfigurator.configure();

        JavaGrepImp javaGrepImp = new JavaGrepImp();
        javaGrepImp.setRegex(args[0]);
        javaGrepImp.setRootPath(args[1]);
        javaGrepImp.setOutFile(args[2]);

        try{
            javaGrepImp.process();
        } catch (Exception ex){
            javaGrepImp.logger.error("Error: Unable to process", ex);
        }

    }

    @Override
    public void process() throws IOException {
        List<String> matchedLines = new ArrayList<>();
        for (File file : listFiles(rootPath)){
            for (String line : readLines(file)){
                if (containsPattern(line)){
                    matchedLines.add(line);
                }
            }
        }
        writeToFile(matchedLines);
    }

    @Override
    public List<File> listFiles(String rootDir) {
        List<File> actualFiles = new ArrayList<>();
        File dir = new File(rootDir);
        File[] allFiles = dir.listFiles();
        for (File file : allFiles){
            if (file.isFile()){
                actualFiles.add(file);
            }
        }
        return actualFiles;
    }

    @Override
    public List<String> readLines(File inputFile) throws IOException {
        List<String> lines = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(inputFile));
        String line;
        while ((line = br.readLine()) != null){
            lines.add(line);
        }
        br.close();
        return lines;
    }

    @Override
    public boolean containsPattern(String line) throws IOException {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);
        boolean matchFound = matcher.find();
        return matchFound;
    }

    @Override
    public void writeToFile(List<String> lines) throws IOException {
        File file = new File(outFile);
        file.createNewFile();
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        for (String line : lines){
            bw.write(line + "\n");
        }
        bw.close();
    }

    @Override
    public String getRootPath() {
        return rootPath;
    }

    @Override
    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    @Override
    public String getRegex() {
        return regex;
    }

    @Override
    public void setRegex(String regex) {
        this.regex = regex;
    }

    @Override
    public String getOutFile() {
        return outFile;
    }

    @Override
    public void setOutFile(String outFile) {
        this.outFile = outFile;
    }
}
