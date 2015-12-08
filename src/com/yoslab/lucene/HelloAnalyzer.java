package com.yoslab.lucene;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.UpperCaseFilter;
import org.apache.lucene.analysis.core.WhitespaceTokenizer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

public class HelloAnalyzer {
  
  public static void main(String[] args) throws IOException {
    StandardAnalyzer analyzer = new StandardAnalyzer();
    
    String str = "My father's name was John Kinsella. It's an Irish name.";
    Reader reader = new StringReader(str);
    
    
//    TokenStream stream = analyzer.tokenStream("", reader);
//    // need to call at first. see also  https://lucene.apache.org/core/5_3_1/core/org/apache/lucene/analysis/TokenStream.html
//    stream.reset(); 
//    
//    while (stream.incrementToken()) {
//      CharTermAttribute term = stream.getAttribute(CharTermAttribute.class);
//      System.out.println(term);
//    }
//    
    
    
    // TokenFilter is subclass of TokenStream !!
    TokenStream stream = new UpperCaseFilter(analyzer.tokenStream("", reader));
    
    // while の外で予め addAttribute しておくこと。 http://d.hatena.ne.jp/shinobu_aoki/20120128/1327762530
    CharTermAttribute term = stream.addAttribute(CharTermAttribute.class);
    stream.reset();
    
    while (stream.incrementToken()) {
      System.out.println(term);
    }
    
    stream.end();
    stream.close();
  }
}
