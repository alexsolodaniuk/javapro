package com.company;

import java.io.FileWriter;
import java.io.IOException;

@SaveTo(path = "E:\\file.txt")
public class TextContainer {
    String text = "Hello World!";

    @Saver
    public void save(String path) throws IOException {
        FileWriter fw = new FileWriter(path, true);
        fw.write(this.text);
        fw.close();
    }
}
