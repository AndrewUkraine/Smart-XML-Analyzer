package com.agileengine.finders;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Optional;

public class ElementFinder {

    private Document document;
    private Element targetElement;

    public ElementFinder(Document document, Element targetElement) {
        this.document = document;
        this.targetElement = targetElement;
    }


    public Optional<Element> findSameElements() {

        Elements elements = document.getAllElements();
        Element similarElement = null;

        for (Element element : elements) {

            int elementWeight = elementsMatching(element);

            if (elementWeight > 0) {
                similarElement = element;
            }
        }

        return Optional.ofNullable(similarElement);
    }

    private int elementsMatching(Element element) {

        Attributes elementAttributes = element.attributes();
        Attributes requiredAttributes = targetElement.attributes();
        ;

        int match = 0;

        match = getMatch(elementAttributes, requiredAttributes, match);

        return match;
    }

    private int getMatch(Attributes elementAttributes, Attributes requiredAttributes, int match) {
        for (Attribute attribute : elementAttributes) {

            String attributeKey = attribute.getKey();
            String requiredAttributeValue = requiredAttributes.get(attributeKey);

            if (StringUtils.isNotEmpty(requiredAttributeValue) && requiredAttributeValue.equalsIgnoreCase(attribute.getValue())) {
                match = 1;
            }
        }
        return match;
    }


    public String findElementPath(Element element) {
        StringBuilder pathBuilder = new StringBuilder();
        Elements parents = element.parents();

        for (int i = parents.size() - 1; i >= 0; i--) {
            Element currentElement = parents.get(i);
            UtilReaderHelper.appendElements(currentElement, pathBuilder);
        }

        UtilReaderHelper.appendElements(element, pathBuilder);
        return pathBuilder.toString();
    }
}
