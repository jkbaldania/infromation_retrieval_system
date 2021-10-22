package com.v1.irs.irhandler;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class LuceneSearching {
    IndexSearcher indexSearcher;
    QueryParser queryParser;
    Query query;

    public LuceneSearching(String locIndex) throws IOException {

        Path locIndexPath = Paths.get(locIndex);
        Directory dirIndex = FSDirectory.open(locIndexPath);

        DirectoryReader directoryReader = DirectoryReader.open(dirIndex);
        indexSearcher = new IndexSearcher(directoryReader);
        queryParser = new QueryParser(LuceneGlobalVariables.CONTENTS, new LuceneCustomAnalyzer(LuceneStopWords.getList()));
    }

    public TopDocs search(String query) throws IOException, ParseException {
        this.query = queryParser.parse(query);
        return indexSearcher.search(this.query, LuceneGlobalVariables.MAX_RESULTS);
    }

    public Document getDocumentLucene(ScoreDoc scoreDoc) throws CorruptIndexException, IOException {
        return indexSearcher.doc(scoreDoc.doc);
    }

    public void close() throws IOException {
//        indexSearcher.close();
    }
}
