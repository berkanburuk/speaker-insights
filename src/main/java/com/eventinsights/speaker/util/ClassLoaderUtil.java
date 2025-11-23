package com.eventinsights.speaker.util;

import com.eventinsights.speaker.exception.NotFoundException;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ClassLoaderUtil {

    /**
     * Returns Path of a resource in src/main/resources or src/test/resources
     * Usage: ClassLoaderUtil.getResource("conference-data.csv")
     */
    public static Path getPath(String resourcePath) throws URISyntaxException {
        URL resource = ClassLoaderUtil.class.getClassLoader().getResource(resourcePath);
        if (resource == null) {
            throw new NotFoundException("Resource not found: " + resourcePath);
        }
        return Paths.get(resource.toURI());
    }

}

