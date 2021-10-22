package com.v1.irs.irhandler;

import org.apache.lucene.analysis.CharArraySet;

import java.util.Arrays;
import java.util.List;

public class LuceneStopWords {

    private static List<String> customStopWords = Arrays.asList(
            "a", "an", "and", "are", "as", "at", "be", "but", "by",
            "for", "if", "in", "into", "is", "it",
            "no", "not", "of", "on", "or", "such",
            "that", "the", "their", "then", "there", "these",
            "they", "this", "to", "was", "will", "with"
    );

    public CharArraySet charArraySet;

    public static CharArraySet getList() {
        CharArraySet charArraySet = new CharArraySet(customStopWords.size(),true);
        for (int g=0;g<customStopWords.size();g++){
            charArraySet.add(customStopWords.get(g));
        }
        return charArraySet;
    }

}
