package com.yoslab.lucene;

import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.QueryBuilder;
import org.apache.lucene.util.Version;

public class HelloLucene {
  
  public static void main(String args[]) throws IOException {
    
    // create index
    StandardAnalyzer analyzer = new StandardAnalyzer();
    analyzer.setVersion(Version.LUCENE_4_0_0);
    
    Directory index = new RAMDirectory();
    IndexWriterConfig config = new IndexWriterConfig(analyzer);

    IndexWriter w = new IndexWriter(index, config);
    addDoc(w, "Lucene in Action", "193398817");
    addDoc(w, "Lucene for Dummies", "55320055Z");
    addDoc(w, "Managing Gigabytes", "55063554A");
    addDoc(w, "The Art of Computer Science", "9900333X");
    w.close();
    
    // query
    String querystr = args.length > 0 ? args[0] : "Gigabytes";
    QueryBuilder queryBuilder = new QueryBuilder(analyzer);
    Query q = queryBuilder.createPhraseQuery("title", querystr);
    
    // Query q = new TermQuery(new Term("title", querystr));
    
    // 3. search
    int hitsPerPage = 10;
    IndexReader reader = DirectoryReader.open(index);
    IndexSearcher searcher = new IndexSearcher(reader);
    TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage);
    searcher.search(q, collector);
    ScoreDoc[] hits = collector.topDocs().scoreDocs;
    
    // 4. display results
    System.out.println("Found " + hits.length + " hits.");
    for(int i=0;i<hits.length;++i) {
      int docId = hits[i].doc;
      Document d = searcher.doc(docId);
      System.out.println((i + 1) + ". " + d.get("isbn") + "\t" + d.get("title"));
    }

    // reader can only be closed when there
    // is no need to access the documents any more.
    reader.close();
    
  }

  private static void addDoc(IndexWriter w, String title, String isbn) throws IOException {
    Document document = new Document();
    document.add(new TextField("title", title, Field.Store.YES));
    document.add(new StringField("isbn", isbn, Field.Store.YES));
    w.addDocument(document);
  }
}
