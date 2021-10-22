package com.v1.irs.irhandler;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import com.v1.irs.irhandler.InformationRetrievalHandler;

public class LuceneMainCalls {

//    String indexLoc = InformationRetrievalHandler.locIndex;
//    String dataLoc = InformationRetrievalHandler.locData;

    LuceneIndexing luceneIndexing;
    LuceneSearching luceneSearching;

    public void indexCreation(List<File> filesInZip, String indexLoc) throws IOException {

        try {
            File directory = new File(indexLoc);
            for(File file: directory.listFiles())
                if (!file.isDirectory())
                    file.delete();
        } catch (Exception e) {
        }
        try {
            File directory = new File(indexLoc);
            directory.delete();
        } catch (Exception e) {
        }
        try {
            new File(indexLoc).mkdir();
        } catch (Exception e) {
        }

        luceneIndexing = new LuceneIndexing(indexLoc);
        int numIndexed;
        long indexingStart = System.currentTimeMillis();
        numIndexed = luceneIndexing.createIndex(filesInZip, new HelperFileFilter());
        long indexingEnd = System.currentTimeMillis();
        luceneIndexing.close();
        System.out.println("File(s) indexed: " + numIndexed +  " and time taken: " + (indexingEnd-indexingStart) + " ms");
    }

    public void querySearch(String query, String indexLoc) throws IOException, ParseException {
        luceneSearching = new LuceneSearching(indexLoc);
        long searchStart = System.currentTimeMillis();
        TopDocs topDocs = luceneSearching.search(query);
        long searchEnd = System.currentTimeMillis();

        System.out.println("Documents found: " + topDocs.totalHits + " and time taken: " + (searchEnd - searchStart) + " ms");

        int rank = 1;
        for(ScoreDoc scoreDoc : topDocs.scoreDocs) {
            Document documentLucene = luceneSearching.getDocumentLucene(scoreDoc);
            File f= new File(documentLucene.get(LuceneGlobalVariables.FILEPATH));

            String rankStr = "";
            String scoreStr = "";
            String filePath = "";
            String lastModified = "";
            String htmlTitle = "";
            String htmlSummary = "";
            Boolean html = false;

            try {
                rankStr = String.valueOf(rank);
            } catch (Exception e) {
            }
            try {
                scoreStr = String.valueOf(scoreDoc.score);
            } catch (Exception e) {
            }
            try {
                filePath = documentLucene.get(LuceneGlobalVariables.FILEPATH);
            } catch (Exception e) {
            }
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                lastModified = sdf.format(f.lastModified());
            } catch (Exception e) {
            }
            try {
                if(documentLucene.get(LuceneGlobalVariables.FILEPATH).contains(".html")) {
                    String pathhtml = documentLucene.get(LuceneGlobalVariables.FILEPATH);
                    htmlTitle = HelperJsoupParser.parsetitle(pathhtml);
                    htmlSummary = HelperJsoupParser.parsesummary(pathhtml);
                    html = true;
                }
            } catch (Exception e) {
            }
            outputGenerator(rankStr, scoreStr, filePath, lastModified, htmlTitle, htmlSummary, html);
            rank = rank + 1;
        }
        luceneSearching.close();
    }

    public void outputGenerator(String rank,String score, String filePath, String lastModified, String htmlTitle, String htmlSummary, Boolean html) throws IOException, ParseException {

        System.out.println("******************************************************************************");
        System.out.println("Document Relevance Rank: " + rank);
        System.out.println("Document File Path: " + filePath);
        System.out.println("Document Relevance Score: " + score);
        System.out.println("Document Last Modification Time: " + lastModified);
        if (html==true) {
            System.out.println("HTML Document Title: " + htmlTitle);
            System.out.println("HTML Document Summary: " + htmlSummary);
        }
    }

}
