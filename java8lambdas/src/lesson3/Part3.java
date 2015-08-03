package lesson3;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.BinaryOperator;
import java.util.stream.Stream;

/**
 *
 */
public class Part3 {

    private BinaryOperator<String> biggerLength = (str1, str2) -> str1.length() > str2.length()? str1 : str2;

    public String longestLineInFileAt(Path path) throws IOException {

        return Files.lines(path)
                .max((line1, line2) -> line1.length() - line2.length())
                .orElseGet(()->"");


    }

    public static void main(String[] args) {
        Path path = Paths.get("file.txt");

        Part3 part3 = new Part3();

        try {
            System.out.printf(part3.longestLineInFileAt(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
