package com.ramiya.cs441.h3;

import java.io.*;
import java.io.File;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.sql.Time;
import java.util.Scanner;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;
import java.sql.Timestamp;
import java.time.LocalTime;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ResponseHeaderOverrides;
import com.amazonaws.services.s3.model.S3Object;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class BinarySearch  {

//    void main() throws IOException {
//        findTime("17");
//    }
//       public static void main(String[] args) throws Exception {
//            findTime("17:12:58.734");
//       }


    public static boolean findTime( String time) throws IOException {

        String bucketName = "logfilegrpcrest";
        String key = "LogFileGenerator.2021-10-18.log";

        System.out.println("inside binarysearch"+time);

        S3Object fullObject = null, objectPortion = null, headerOverrideObject = null, fullObject1 = null;
        try {
//            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
//                    .withRegion("us-east-2")
////                    .withCredentials(new ProfileCredentialsProvider())
//
//                    .build();

            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withRegion("us-east-2")
                    .withCredentials(DefaultAWSCredentialsProviderChain.getInstance())
                    .build();

//            AmazonS3 s3Client = new AmazonS3Client();


            // Get an object and print its contents.
            System.out.println("Downloading an object");

            fullObject = s3Client.getObject(new GetObjectRequest(bucketName, key));
         //   fullObject1 = s3Client.getObject(new GetObjectRequest(bucketName, key));

            System.out.println("Content-Type: " + fullObject.getObjectMetadata().getContentType().length());
            System.out.println("Content: ");

//            System.out.println(s3Client.getObjectMetadata(bucketName, key).getContentLength());

           // System.out.println("Number of lines in the "+ fullObject.getObjectContent());
            InputStream reader = fullObject.getObjectContent();
           // InputStream reader1 = fullObject1.getObjectContent();

            BufferedReader reader2 = new BufferedReader(new InputStreamReader(reader));
            //System.out.println("Number of lines in the "+ reader2.lines().count());

            return displayTextInputStream(reader, time);
        } catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.
            e.printStackTrace();
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            e.printStackTrace();
        }
        return false;
    }


//    public static Path path = Paths.get("src/main/resources/LogFileGenerator.2021-10-09.log");
//        public static int lines;
//
////    AmazonS3 s3Client = new AmazonS3Client(new ProfileCredentialsProvider());
////    S3Object object = s3Client.getObject(new GetObjectRequest(bucketName, key));
////    InputStream objectData = object.getObjectContent();
////// Process the objectData stream.
////objectData.close();
//
//    public static LocalTime time1 = LocalTime.parse("15:23:09.604");
//    public static LocalTime dT = LocalTime.parse("00:00:01.000");
//
//    static {
//        try {
//            lines = Integer.valueOf((int) Files.lines(path).count());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//

//    public static void main(String[] args) throws Exception {
//
//        int bottom = 0;
//        int top = lines;
//        int middle = 0;
//        boolean found = false;
//        while (bottom <= top) {
//            middle = (bottom + top) / 2;
//            System.out.println("mid" + middle);
//            String midLine;
//            try (Stream<String> liness = Files.lines(Paths.get("src/main/resources/LogFileGenerator.2021-10-09.log"))) {
//                midLine = liness.skip(middle - 1).findFirst().get();
//            }
//            LocalTime time = LocalTime.parse(midLine.split(" ")[0]);
//            System.out.println("mid1" + midLine);
//            int comparison = time.compareTo(time1);
//
//            if (comparison == 0) {
//                // found it
//                found = true;
////                findInterval(middle);
//                break;
//            } else if (comparison < 1) {
//                // line comes before searchValue
//                bottom = middle + 1;
//            } else {
//                // line comes after searchValue
//                top = middle - 1;
//            }
//        }
//
//        if (found) {
//            System.out.println("yes");
//            System.out.println("FINAL ANS" + " " + middle);
//            findInterval(middle);
//        } else {
//            System.out.println("no");
//        }
//    }
//
//    private static void findInterval(int middle) throws Exception {
//
//        int startFirstHalf = 0;
//        int endFirstHalf = middle;
//        int startSecondHalf = middle;
//        int endSecondHalf = lines;
//
//        LocalTime lowerIntervalTime = time1.plusHours(dT.getHour()).plusMinutes(dT.getMinute()).plusSeconds(dT.getSecond()).plusNanos(dT.getNano());
//        LocalTime upperIntervalTime = time1.minusHours(dT.getHour()).minusMinutes(dT.getMinute()).minusSeconds(dT.getSecond()).minusNanos(dT.getNano());
//
//        System.out.println("time" + time1);
//        System.out.println("upper" + upperIntervalTime);
//        System.out.println("lower" + lowerIntervalTime);
//
//        int ans1 = upperBound(startFirstHalf, endFirstHalf, upperIntervalTime);
//        int ans2 = lowerBound(startSecondHalf, endSecondHalf, lowerIntervalTime);
//
//        System.out.println("FINAL ANS1" + " " + ans1);
//        System.out.println("FINAL ANS2" + " " + ans2);
//
//    }
//
//    private static int upperBound(int s, int e, LocalTime upperIntervalTime) throws Exception {
//
//        System.out.println(s);
//        System.out.println(e);
//        System.out.println(upperIntervalTime);
//
//        boolean found = false;
//        int mid = 0;
//        int start = s;
//        int end = e;
//
//        while (start <= end) {
//            mid = (start + end) / 2;
//            System.out.println("mid" + mid);
//            String midLineVal;
//
//            try (Stream<String> liness = Files.lines(Paths.get("src/main/resources/LogFileGenerator.2021-10-09.log"))) {
//                midLineVal = liness.skip(mid - 1).findFirst().get();
//            }
//
//            LocalTime time = LocalTime.parse(midLineVal.split(" ")[0]);
//            System.out.println("midTime" + midLineVal);
//
//            int comparison = time.compareTo(upperIntervalTime);
//
//            if (comparison == 0) {
//                // found it
//                found = true;
////                findInterval(middle);
//                break;
//            } else if (comparison < 1) {
//                // line comes before searchValue
//                start = mid + 1;
//            } else {
//                // line comes after searchValue
//                end = mid - 1;
//            }
//        }
//
//        if (found)
//            System.out.println("true");
//        else
//            System.out.println("false");
//
//        System.out.print(start);
//        return start;
//
//    }
//
//    private static int lowerBound(int s, int e, LocalTime lowerIntervalTime) throws Exception {
//
//        System.out.println(s);
//        System.out.println(e);
//        System.out.println(lowerIntervalTime);
//
//        boolean found = false;
//        int mid = 0;
//        int start = s;
//        int end = e;
//
//        while (start <= end) {
//            mid = (start + end) / 2;
//            System.out.println("mid" + mid);
//            String midLineVal;
//
//            try (Stream<String> liness = Files.lines(Paths.get("src/main/resources/LogFileGenerator.2021-10-09.log"))) {
//                midLineVal = liness.skip(mid - 1).findFirst().get();
//            }
//
//            LocalTime time = LocalTime.parse(midLineVal.split(" ")[0]);
//            System.out.println("midTime" + midLineVal);
//
//            int comparison = time.compareTo(lowerIntervalTime);
//
//            if (comparison == 0) {
//                // found it
//                found = true;
////                findInterval(middle);
//                break;
//            } else if (comparison < 1) {
//                // line comes before searchValue
//                start = mid + 1;
//            } else {
//                // line comes after searchValue
//                end = mid - 1;
//            }
//        }
//
//        if (found)
//            System.out.println("true");
//        else
//            System.out.println("false");
//
//        System.out.print(end);
//        return end;
//    }

    private static boolean displayTextInputStream(InputStream input, String time) throws IOException {
        // Read the text input stream one line at a time and display each line.

        long lineCount;
      //  System.out.println("hey hi hello", input);

      //  System.out.println("Number of lines in the file are ::"+ input);


        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
//       // System.out.println("Number of lines in the "+ reader.lines().count());
//        //System.out.println(" lines in the "+reader.);
////        System.out.println("lines in the "+ reader.readLine());
//
//       // System.out.println("lines in the "+ reader.lines());
//        String specific_line_15 = null;
//        try (Stream<String> all_lines = reader.lines()) {
//            System.out.println("lines in the "+ all_lines.count());
//
//          //  specific_line_15 = all_lines.skip(14).findFirst().get();
//        }
//
//        try (Stream<String> all_lines1 = reader.lines()) {
//
//            specific_line_15 = all_lines1.skip(14).findFirst().get();
//        }
       // System.out.println("lines in the "+ specific_line_15);

//        all_lines.skip(14).findFirst().get();
        String line = null;
        boolean ans = false;
        int count = 0;
        while ((line = reader.readLine()) != null) {
         //  System.out.println("lines" + line);
            String timeValue = line.split(" ")[0];

            if(timeValue.equals(time)) {
                System.out.println("found here");
                ans = true;
                break;
            }
          //  count++;
        }
        System.out.println("ansinside" + ans);
        return ans;
    }
}




