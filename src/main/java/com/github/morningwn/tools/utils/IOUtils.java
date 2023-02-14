package com.github.morningwn.tools.utils;

import org.h2.engine.Constants;
import org.h2.mvstore.DataUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author morningwn
 * @create in 2023/1/31 17:11
 */
public final class IOUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(IOUtils.class);

    private IOUtils() {
    }

    public static String readString(File file) {
        try {
            return readString(file.toURI().toURL());
        } catch (MalformedURLException e) {
            LOGGER.error("IOUtils#readString error", e);
            return StrUtils.EMPTY;
        }
    }

    public static String readString(URL url) {
        try (InputStreamReader reader = new InputStreamReader(url.openStream())) {
            StringBuilder sb = new StringBuilder();
            char[] bff = new char[4 * 1024];
            int len = -1;
            while (-1 != (len = reader.read(bff))) {
                sb.append(bff, 0, len);
            }
            return sb.toString();
        } catch (IOException e) {
            LOGGER.error("IOUtils#readString error", e);
            return StrUtils.EMPTY;
        }
    }
}
