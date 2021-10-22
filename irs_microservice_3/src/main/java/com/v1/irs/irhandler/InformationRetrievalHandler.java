package com.v1.irs.irhandler;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import org.apache.lucene.queryparser.classic.ParseException;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class InformationRetrievalHandler {

//    public static String locIndex = "test_file";
//    public static String locData;
    private LuceneMainCalls luceneMainCalls;

    public void createIndex(List<File> filesInZip, String locIndex) {
        try {
            luceneMainCalls = new LuceneMainCalls();
            luceneMainCalls.indexCreation(filesInZip, locIndex);
//            luceneMainCalls.querySearch(query);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<JSONObject> query(String query, String locIndex) {
        try {
            luceneMainCalls = new LuceneMainCalls();
            List<JSONObject> listOfObjs = luceneMainCalls.querySearch(query, locIndex);
            return listOfObjs;
        } catch (IOException | ParseException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

//    public static void main(String[] args) {
//        LuceneMainCalls luceneMainCalls;
//        locData = args[0];
//        locIndex = locData + "\\" + "000_Index_Folder_Team_Taskforce";

//        String query="";

//        try {
//            Scanner scanner = new Scanner(System.in);
//            System.out.print("Enter query: ");
//            query = scanner.next();
//        } catch (Exception e) {
//        }

//        try {
//            luceneMainCalls = new LuceneMainCalls();
//            luceneMainCalls.indexCreation();
//            luceneMainCalls.querySearch(query);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

//    }

}
