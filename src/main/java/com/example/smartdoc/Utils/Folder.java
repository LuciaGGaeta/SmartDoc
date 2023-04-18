package com.example.smartdoc.Utils;

import java.util.ArrayList;
import java.util.List;

public class Folder {
    private String name;
    private List<String> files = new ArrayList<>();
    public Folder() {
    }

    public Folder(String name, List<String> files) {
        this.name = name;
        this.files = files;
    }

    public String getname() {
        return name;
    }

    public List<String> getFiles() {
        return files;
    }

    @Override
    public String toString() {
        return "Folder{" +
                "name='" + name + '\'' +
                ", files=" + files.toString() +
                '}';
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFiles(List<String> files) {
        this.files = files;
    }
}
