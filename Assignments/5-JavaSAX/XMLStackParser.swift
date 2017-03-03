//
//
//  Created by Dale Musser on 11/21/16.
//  Copyright © 2016 Dale Musser. All rights reserved.
//
//  Event driven XML parsing using a stack to create a tree (DOM)
//  This is written using Swift 3.

import Foundation

class XMLNode {
    var name = ""
    var content = ""
    var properties = [String:[XMLNode]]()
    var attributes = [String:String]()
}

class XMLStackParser: NSObject, XMLParserDelegate {
    var root: XMLNode?
    var stack = [XMLNode]()
    var currentNode: XMLNode?
    
    var currentElementName: String?
    var currentElementData = ""
    
    func parse(with data: Data) -> XMLNode? {
        let xmlParser = XMLParser(data: data)
        xmlParser.delegate = self
        xmlParser.parse()
        
        return root
    }
    
    func parserDidStartDocument(_ parser: XMLParser) {
        root = nil
        stack = [XMLNode]()
    }
    
    func parser(_ parser: XMLParser, didStartElement elementName: String, namespaceURI: String?, qualifiedName qName: String?, attributes attributeDict: [String : String] = [:]) {
        let node = XMLNode()
        node.name = elementName
        node.attributes = attributeDict
        stack.append(node)
        
        if let currentNode = currentNode {
            if currentNode.properties[elementName] != nil {
                currentNode.properties[elementName]?.append(node)
            } else {
                currentNode.properties[elementName] = [node]
            }
        }
        currentNode = node
    }
    
    func parser(_ parser: XMLParser, didEndElement elementName: String, namespaceURI: String?, qualifiedName qName: String?) {
        if let poppedNode = stack.popLast(){
            poppedNode.content = poppedNode.content.trimmingCharacters(in: CharacterSet.whitespacesAndNewlines)
            if (stack.isEmpty) {
                root = poppedNode
                currentNode = nil
            } else {
                currentNode = stack.last
            }
        }
    }
    
    func parser(_ parser: XMLParser, foundCharacters string: String) {
        currentNode?.content += string
    }
    
    func parser(_ parser: XMLParser, foundCDATA CDATABlock: Data) {
        if let string = String(data: CDATABlock, encoding: .utf8) {
            currentNode?.content += string
        }
    }
}
