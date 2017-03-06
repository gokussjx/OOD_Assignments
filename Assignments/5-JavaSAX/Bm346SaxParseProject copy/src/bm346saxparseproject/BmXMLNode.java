package bm346saxparseproject;

import org.xml.sax.ext.Attributes2;
import org.xml.sax.ext.Attributes2Impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by bidyut on 3/4/17.
 */
public class BmXMLNode {
    String name = "";
    String content = "";
    Map<String, List<BmXMLNode>> properties = new HashMap<>();
//    Attributes2 attributes = new Attributes2Impl();
    Map<String, String> attributes = new HashMap<>();
}
