package com.yoslab.lucene;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.charfilter.HTMLStripCharFilter;
import org.apache.lucene.analysis.core.UpperCaseFilter;
import org.apache.lucene.analysis.core.WhitespaceTokenizer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;

public class HelloTokenizer {
  
  public static void main(String[] args) throws IOException {
    String str = "<p>My father's name was John & Kinsella. It's an Irish name.</p>";
    
    Reader reader = new StringReader(str);
    reader = new HTMLStripCharFilter(reader);
    
    Tokenizer tokenizer = new WhitespaceTokenizer();
    tokenizer.setReader(reader);
    tokenizer.reset();
    
    CharTermAttribute ta = (CharTermAttribute)tokenizer.addAttribute( CharTermAttribute.class );
    PositionIncrementAttribute pia = (PositionIncrementAttribute)tokenizer.addAttribute( PositionIncrementAttribute.class );
    OffsetAttribute oa = (OffsetAttribute)tokenizer.addAttribute( OffsetAttribute.class );
    TypeAttribute tya = (TypeAttribute)tokenizer.addAttribute( TypeAttribute.class );
    
    while(tokenizer.incrementToken()) {
      System.out.println( ta + ", positionIncrement=" + pia.getPositionIncrement() + ", start=" + oa.startOffset() + ",end=" + oa.endOffset() + ", type=" + tya.type() );
    }
    
    tokenizer.close();
  }
}
