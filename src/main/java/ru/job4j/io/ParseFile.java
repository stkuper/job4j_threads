package ru.job4j.io;

import java.io.File;

public final class ParseFile {
    private final File file;
    private final FileManager fileManager;

    public ParseFile(File file, FileManager fileManager) {
        this.file = file;
        this.fileManager = fileManager;
    }

    public String getContent() {
        return fileManager.getContent(file, character -> true);
    }

    public String getContentWithoutUnicode() {
        return fileManager.getContent(file, character -> character < 0x80);
    }

    public void saveContent(String content) {
        fileManager.saveContent(content, file);
    }
}