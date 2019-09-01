package com.agileengine.finders;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class UtilReaderHelper {

    private static Logger LOGGER = LoggerFactory.getLogger(Main.class);

    private static String CHARSET_NAME = "utf8";

    public static Optional<Document> readDocument(File htmlFile) {
        try {
            Document document = Jsoup.parse(
                    htmlFile,
                    CHARSET_NAME,
                    htmlFile.getAbsolutePath());

            return Optional.of(document);
        } catch (IOException e) {
            LOGGER.error(String.format("Error reading [%s] file", htmlFile.getAbsolutePath()), e);
            return Optional.empty();
        }
    }

    public static void appendElements(Element element, StringBuilder pathBuilder) {
        pathBuilder.append(" -> ");
        pathBuilder.append(element.tagName());
        pathBuilder.append("[");
        pathBuilder.append(element.elementSiblingIndex());
        pathBuilder.append("]");
    }
}
