package com.v1.irs.irhandler;

import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class LuceneIndexing {

    private IndexWriter indexWriter;

    public LuceneIndexing(String locIndex) throws IOException {

        Path locIndexPath = Paths.get(locIndex);
        Directory dirIndex = FSDirectory.open(locIndexPath);
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(new LuceneCustomAnalyzer(LuceneStopWords.getList()));
        indexWriter = new IndexWriter(dirIndex,indexWriterConfig);
    }

    public void close() throws CorruptIndexException, IOException {
        indexWriter.close();
    }

    private Document getDocument(File file) throws IOException {
        Document documentLucene = new Document();

        FieldType ftContents = new FieldType();//TextField.TYPE_STORED);
        ftContents.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS);
//        ftContents.setStored(true);
        Field contentField = new Field(LuceneGlobalVariables.CONTENTS, new FileReader(file),ftContents);

        FieldType ftFileName = new FieldType(TextField.TYPE_STORED);
        ftFileName.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS);
        ftFileName.setStored(true);
        Field fileNameField = new Field(LuceneGlobalVariables.FILENAME,file.getName(),ftFileName);

        FieldType ftFilePath = new FieldType(TextField.TYPE_STORED);
        ftFilePath.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS);
        ftFilePath.setStored(true);
        Field filePathField = new Field(LuceneGlobalVariables.FILEPATH,file.getCanonicalPath(),ftFilePath);

        documentLucene.add(contentField);
        documentLucene.add(fileNameField);
        documentLucene.add(filePathField);

        return documentLucene;
    }

    private void indexFile(File file) throws IOException {
        System.out.println("Indexing "+file.getCanonicalPath());
        Document documentLucene = getDocument(file);
        indexWriter.addDocument(documentLucene);
    }

    public int createIndex(List<File> filesAll, FileFilter filter) throws IOException {
//        File[] files = new File(locData).listFiles();

//        List<File> filesAll = new ArrayList<>();
//        LuceneIndexing.listf(locData,filesAll);
//        for (int i = 0;i<filesAll.size();i++){
//            System.out.println(filesAll.get(i).getAbsolutePath().toString());
//        }

        for (int i = 0;i<filesAll.size();i++) {
            if(!filesAll.get(i).isDirectory()
//                    && !filesAll.get(i).isHidden()
//                    && filesAll.get(i).exists()
//                    && filesAll.get(i).canRead()
                    && filter.accept(filesAll.get(i))
            ){
                indexFile(filesAll.get(i));
            }
        }

        return indexWriter.numRamDocs();
    }

//    public static void listf(String directoryName, List<File> files) {
//        File directory = new File(directoryName);
//
//        File[] fList = directory.listFiles();
//        if(fList != null)
//            for (File file : fList) {
//                if (file.isFile()) {
//                    files.add(file);
//                } else if (file.isDirectory()) {
//                    listf(file.getAbsolutePath(), files);
//                }
//            }
//    }

}
