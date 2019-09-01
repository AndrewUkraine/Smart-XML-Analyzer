package com.agileengine.finders;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Optional;

public class Main {

    private static Logger LOGGER = LoggerFactory.getLogger(Main.class);

    private static final String DEFAULT_TARGET_ELEMENT_ID = "make-everything-ok-button";

    public static void main(String[] args) {

        String originalHtmlDocumentPath = args[0];
        String additionalHtmlDocumentPath = args[1];

        Optional<Document> originalHtmlDocument = UtilReaderHelper.readDocument(new File(originalHtmlDocumentPath));

        Optional<Element> documentHtmlForCompare = originalHtmlDocument.map(doc -> doc.getElementById(DEFAULT_TARGET_ELEMENT_ID));
        if (!documentHtmlForCompare.isPresent()) {
            LOGGER.warn("There is no element with the id={} into original document: {}", DEFAULT_TARGET_ELEMENT_ID, originalHtmlDocumentPath);
            return;
        }

        Optional<Document> otherSampleDocument = UtilReaderHelper.readDocument(new File(additionalHtmlDocumentPath));
        otherSampleDocument.ifPresent(document -> {
            ElementFinder elementFinder = new ElementFinder(document, documentHtmlForCompare.get());
            elementFinder.findSameElements()
                    .map(elementFinder::findElementPath)
                    .ifPresent(p -> LOGGER.info("The path of found element is: {}", p));
        });
    }
}