package bm346saxparseproject;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.ext.Attributes2;
import org.xml.sax.ext.Attributes2Impl;
import org.xml.sax.ext.DefaultHandler2;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Created by bidyut on 3/3/17.
 */
public class BmSAXParser {

    static BmXMLNode root;
    static Deque<BmXMLNode> stack = new ArrayDeque<>();
    static BmXMLNode currentNode;

//    static String currentElementName;
//    static String currentElementData = "";

    public static BmXMLNode parse(File xmlFile) throws Exception {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            DefaultHandler2 handler = new DefaultHandler2() {

                boolean cdataContent = false;

                @Override
                public void startDocument() throws SAXException {
                    root = null;
                    stack = new ArrayDeque<>();
                }

                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                    BmXMLNode node = new BmXMLNode();
                    node.name = qName;
                    if (attributes.getLength() > 0) {
                        for (int i = 0; i < attributes.getLength(); i++) {
                            node.attributes.put(attributes.getQName(i), attributes.getValue(i));
                        }
                    }
//                    node.attributes = (Attributes2) attributes;
//                    stack.addFirst(node);
                    stack.push(node);

                    if (currentNode != null) {
                        if (currentNode.properties.get(qName) != null) {
                            currentNode.properties.get(qName).add(node);
                        } else {
                            List<BmXMLNode> nodes = new ArrayList<>();
                            nodes.add(node);
                            currentNode.properties.put(qName, nodes);
                        }
                    }
                    currentNode = node;
                }

                @Override
                public void endElement(String uri, String localName, String qName) throws SAXException {
                    if (stack.peek() != null) {
                        BmXMLNode poppedNode = stack.pop();
                        poppedNode.content = poppedNode.content.trim();
                        if (stack.isEmpty()) {
                            root = poppedNode;
                            currentNode = null;
                        } else {
                            currentNode = stack.peek();
                        }
                    }
                }

                @Override
                public void characters(char[] ch, int start, int length) throws SAXException {
                    if (currentNode != null) {
                        String string = new String(ch, start, length);
                        if (cdataContent) {
                            byte[] buffer = StandardCharsets.UTF_8.encode(string).array();
                            string = new String(buffer);
                        }
                        currentNode.content += string;  // \n \t etc. saved. Weird
                    }
                }

                @Override
                public void startCDATA() throws SAXException {
                    cdataContent = true;
                }

                @Override
                public void endCDATA() throws SAXException {
                    cdataContent = false;
                }
            };

            saxParser.parse(xmlFile.getAbsoluteFile(), handler);
        } catch (SAXException e) {
            throw e;
        }
        return root;
    }
}
