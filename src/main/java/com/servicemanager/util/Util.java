package com.servicemanager.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.StringReader;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

import org.junit.Assert;

public class Util {
    public static String YYYY_MM_DD_HHMMSS = "yyyy-MM-dd HH:mm:ss";
    public static String YYYY_MM_DD_T_HH_MM_SS_FORMAT = "yyyy-MM-dd'T'HH:mm:ss+0000";
    public static DateTimeFormatter YYYY_MM_DD_T_HH_MM_SS_FORMATTER = DateTimeFormatter
            .ofPattern(YYYY_MM_DD_T_HH_MM_SS_FORMAT);

    public static String formatTimeStamp(Timestamp ts, String dtf) {
        SimpleDateFormat formatter = new SimpleDateFormat(dtf);
        if (dtf == null || ts == null)
            return null;
        return formatter.format(ts);
    }

    public static <T> boolean isCollectionEmpty(Collection<T> collection) {
        if (collection == null || collection.isEmpty()) {
            return true;
        }
        return false;
    }

    public static <K, V> boolean isMapEmpty(Map<K, V> map) {
        if (map == null || map.isEmpty()) {
            return true;
        }
        return false;
    }

    public static <T> boolean isCollectionPresent(Optional<? extends Collection<T>> optionalCollection) {
        if (optionalCollection.isPresent() && !isCollectionEmpty(optionalCollection.get())) {
            return true;
        }
        return false;

    }

    public static boolean isStringEmpty(String anyString) {
        if (anyString == null || anyString.trim().length() == 0) {
            return true;
        }
        return false;
    }

    public static String toTrimmedUpperCase(String anyString) {
        if (anyString == null || anyString.trim().length() == 0) {
            return anyString;
        } else
            return anyString.trim().toUpperCase();
    }

    public static JsonObject jsonFromString(String jsonString) throws Exception {
        try (JsonReader jsonReader = Json.createReader(new StringReader(jsonString))) {
            JsonObject object = jsonReader.readObject();
            return object;
        }
    }

    public static void createFolderIfDoesNotExist(File logFileDir) {
        if (!logFileDir.exists()) {
            logFileDir.mkdirs();
        }
    }

    public static void validateGeneratedStringForIncompleteness(String detokenizedString) {
        Assert.assertTrue("Template has not been evaluated correctly", !detokenizedString.contains("{"));
    }

    /**
     * Method that creates specified file on disk and writes contents to that file.
     */
    public static String writeFileOntoDisk(String content, String destinationFilePath) throws Exception {
        File parentFile = new File(destinationFilePath).getParentFile();
        Util.createFolderIfDoesNotExist(parentFile);
        try (BufferedWriter br = new BufferedWriter(new FileWriter(destinationFilePath))) {
            br.write(content);
        }
        Logger.debug("Wrote " + content + " to " + destinationFilePath);
        return destinationFilePath;
    }

    public static void log(HashMap<String, Object> aMap, String msg) {
        Set<String> keys = aMap.keySet();
        Logger.debug(msg + "----");
        Iterator<String> it = keys.iterator();
        while (it.hasNext()) {
            String key = it.next();
            Object value = aMap.get(key);
            Logger.debug("     " + key + "---" + value);
        }
    }

    public static void deleteIfExists(String exitCodeFileLocation) {
        File f = new File(exitCodeFileLocation);
        if (f.exists()) {
            Logger.debug("Going to delete file " + exitCodeFileLocation);
            f.delete();
        } else {
            Logger.debug("File " + exitCodeFileLocation + " deos not exists to delete.");
        }
    }

    public static String toString(Object o) {
        String responseAsString = null;
        try (Jsonb jsonb = JsonbBuilder.create()) {
            responseAsString = jsonb.toJson(o);
        } catch (Exception e) {
            Logger.warn("Exception while closing jsonb object", e);
        }
        return responseAsString;
    }

    public static void renameFile(String dir, String fileName, String newFileName) {
        File oldfile = new File(dir + fileName);
        File newfile = new File(dir + newFileName);
        if (oldfile.exists())
            oldfile.renameTo(newfile);
    }

    public static String createFileWithContents(String fileContents, String destinationFileLocation, String fileName) {
        String jsonFilePath = Paths.get(destinationFileLocation, fileName).toString();

        try (BufferedWriter br = new BufferedWriter(new FileWriter(jsonFilePath))) {
            br.write(fileContents);
        } catch (Exception e) {
            Logger.debug("createFileWithContents failed for " + jsonFilePath, e);
            return jsonFilePath;
        }

        return jsonFilePath;
    }

    public static String getTimestampInString(String YYYYMMDDHHMMSS) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter yyyymmddhhmmss = DateTimeFormatter.ofPattern(YYYYMMDDHHMMSS);
        return yyyymmddhhmmss.format(now).toString();
    }
}
