package com.creseliana.controller;

import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TestUtils {
    private static final String MSG_IO_EXCEPTION = "IO exception has occurred. " +
            "Check file existence and availability.\n" +
            "File path: %s\n" +
            "Exception: %s";

    public static String jsonBody(String path) {
        ClassPathResource resource = new ClassPathResource(path);
        StringBuilder builder = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(resource.getFile()))) {
            String line;

            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
        } catch (IOException e) {
            System.err.printf(MSG_IO_EXCEPTION, path, e.getLocalizedMessage());
        }

        System.out.println(builder);
        return builder.toString();
    }
}
