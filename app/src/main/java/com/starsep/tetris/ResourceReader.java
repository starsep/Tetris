package com.starsep.tetris;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ResourceReader {
    private static Context context;

    public static void assign(Context context) {
        ResourceReader.context = context;
    }

    public static String get(int resourceId) {
        InputStream inputStream = context.getResources().openRawResource(resourceId);
        InputStreamReader inputReader = new InputStreamReader(inputStream);
        BufferedReader bufferReader = new BufferedReader(inputReader);
        String line;
        StringBuilder text = new StringBuilder();

        try {
            while ((line = bufferReader.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
        } catch (IOException e) {
            return null;
        }
        return text.toString();
    }
}
