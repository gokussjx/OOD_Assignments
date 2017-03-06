package bm346saxparseproject;

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
    Map<String, String> attributes = new HashMap<>();
}
