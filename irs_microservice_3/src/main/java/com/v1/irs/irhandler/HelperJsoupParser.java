package com.v1.irs.irhandler;

import java.io.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.*;

public class HelperJsoupParser {
    public static  String parsetitle(String path) throws IOException{
        Document doc = Jsoup.parse(new File(path),"utf-8");
        String title = doc.title();
        return title;
    }

    public static  String parsesummary(String path) throws IOException{
//        Document doc = Jsoup.parse(new File(path),"utf-8");
//        Elements summarytag = doc.getElementsByTag("summary");
//        String summary = "";
//        for(Element e : summarytag) {
//            summary = summary + e.text();
////            System.out.println(e.text());
//        }
//        return summary;

        Document doc = Jsoup.parse(new File(path),"utf-8");
        Elements summarytag = doc.getElementsByTag("summary");
        String bodytext = doc.body().text();
        String summary = "";
        String[] partsbyfullstop = bodytext.split("\\.");
        String[] partsbyspace = bodytext.split("\\s+");
        if (partsbyfullstop.length>1){
            for (int i=0; i<2; i++) {
                summary = summary+ (i+1)+ " "+ partsbyfullstop[i] +". ";
            }
//            System.out.println("text has fullstops, so printing first two sentences; checking termination by full stops");
//            System.out.println(summary);
        }
        else {
            if (partsbyspace.length<20) {
                for (int i=0; i<partsbyspace.length; i++) {
                    summary = summary+ partsbyspace[i] +" ";
                }
//                System.out.println("text has NO fullstops; text has LESS than 20 words, so printing those words");
//                System.out.println(summary);
            }
            else  {
                for (int i=0; i<=20; i++) {
                    summary = summary+ partsbyspace[i] +" ";
                }
//                System.out.println("text has NO fullstops; and MORE than 20 words, so printing first 20 words");
//                System.out.println(summary);
            }

        }

        return summary;

    }

}
