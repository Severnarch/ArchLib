package io.github.severnarch.archlib.api;

import io.github.severnarch.archlib.ArchLib;
import io.github.severnarch.archlib.exceptions.config.PropertyNotFoundException;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;

public class Config {
    private final HashMap<String, Object> properties;
    private final String fileName;
    private final JavaPlugin parent;

    public Config(String fileName, JavaPlugin parent) {
        this.fileName = fileName;
        this.parent = parent;
        this.properties = new HashMap<>();
    }

    public Config(String fileName) {
        this.fileName = fileName;
        this.parent = JavaPlugin.getPlugin(ArchLib.class);
        this.properties = new HashMap<>();
    }

    public boolean save() throws IOException {
        File saveFile = new File(parent.getDataFolder(), this.fileName+".yml");
        boolean newCreations = false;
        if (!saveFile.exists()) {
            Boolean createdDirs = saveFile.getParentFile().mkdirs();
            Boolean createdFile = saveFile.createNewFile();
            if (createdFile && createdDirs) {
                newCreations = true;
            }
        }
        FileWriter fileWriter = new FileWriter(saveFile, false);
        String writeContents = "# Configuration file created by ArchLib plugin";
        for (String key : properties.keySet()) {
            writeContents = writeContents.concat("\n"+key+": "+properties.get(key));
        }
        fileWriter.write(writeContents);
        fileWriter.close();
        return newCreations;
    }

    public void get() throws IOException {
        File getFile = new File(parent.getDataFolder(), this.fileName+".yml");
        if (!getFile.exists()) {
            throw new FileNotFoundException(this.fileName+".yml");
        } else {
            List<String> readContent = Files.readAllLines(getFile.getAbsoluteFile().toPath());
            this.properties.clear();
            for (String line : readContent) {
                String[] kvPair = line.split(": ");
                if (kvPair.length > 1) {
                    this.properties.put(kvPair[0], kvPair[1]);
                }
            }
        }
    }

    public boolean delete() throws IOException {
        File deleteFile = new File(parent.getDataFolder(), this.fileName+".yml");
        if (!deleteFile.exists()) {
            throw new FileNotFoundException(this.fileName+".yml");
        } else {
            return deleteFile.delete();
        }
    }

    public void addProperty(String propertyName, Object defaultPropertyValue) {
        properties.put(propertyName, defaultPropertyValue);
    }
    public void removeProperty(String propertyName) throws PropertyNotFoundException {
        if (properties.containsKey(propertyName)) {
            properties.remove(propertyName);
        } else {
            throw new PropertyNotFoundException(propertyName, fileName);
        }
    }

    public Object getProperty(String propertyName) throws PropertyNotFoundException {
        if (properties.containsKey(propertyName)) {
            return properties.get(propertyName);
        } else {
            throw new PropertyNotFoundException(propertyName, fileName);
        }
    }
}
