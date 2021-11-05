package ca.jrvs.apps.grep;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import org.apache.log4j.BasicConfigurator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JavaGrepLambdaImp extends JavaGrepImpl{

    public static void main(String[] args) {
        if (args.length != 3){
            throw new IllegalArgumentException("Usage: JavaGrep regex rootPath outFile");
        }

        //creating JavaGrepLambdaImp object instead of JavaGrepImp
        JavaGrepLambdaImp javaGrepImp = new JavaGrepLambdaImp();
        javaGrepImp.setRegex(args[0]);
        javaGrepImp.setRootPath(args[1]);
        javaGrepImp.setOutFile(args[2]);

        try{
            //calling the parent method, but it will call overridden methods in this class
            javaGrepImp.process();
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }


    @Override
    public List<File> listFiles(String rootDir) {
        File dir = new File(rootDir);
        Stream<File> allFiles = Arrays.stream(dir.listFiles());
        return allFiles.filter(file -> file.isFile()).collect(Collectors.toList());

    }

    @Override
    public List<String> readLines(File inputFile) throws IOException {
        List<String> lines = new ArrayList<>();
        Stream<String> lineStream = Files.lines(Paths.get(getRootPath() + '/' + inputFile.getName()));
        lineStream.forEach(line -> lines.add(line));
        return lines;

    }
}
