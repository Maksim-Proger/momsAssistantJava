package PozMaxPav.com.model.helperClasses;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ReadBasa {
    protected String path;

    public ReadBasa(String path) {
        this.path = path;
    }

    public String read() throws FileNotFoundException {
        String string = "";
        Scanner scanner = new Scanner(new File(path));
        while (scanner.hasNext()){
            string = String.valueOf(scanner.hasNext());
        }
        scanner.close();
        return string;
    }
}
