package ca.jrvs.apps.grep;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface JavaGrep {

    /**
     * Top level search workflow
     * @throws IOException
     */
    void process() throws IOException;

    /**
     * Traverse a given directory and return all the files in the directory
     * @param rootDir input directory
     * @return files under the rootDir
     */
    List<File> listFiles(String rootDir);

    /**
     * Read lines from a file and return them
     * FileReader is used to only read from a text file. BufferReader is a class that helps read
     * text from a character-based input stream. It reads characters using another reader.
     * @param inputFile file to be read
     * @return the read lines
     * @throws IOException if the given inputFile is not a file
     */
    List<String> readLines (File inputFile) throws IOException;

    /**
     * Write lines to a file
     * @param line input string
     * @return true if there is a match
     */
    boolean containsPattern (String line) throws IOException;

    /**
     * Write given matched lines to a file
     * @param lines matched lines
     * @throws IOException
     */
    void writeToFile(List<String> lines) throws IOException;

    String getRootPath();

    void setRootPath(String rootPath);

    String getRegex();

    void setRegex(String regex);

    String getOutFile();

    void setOutFile(String outFile);

}
