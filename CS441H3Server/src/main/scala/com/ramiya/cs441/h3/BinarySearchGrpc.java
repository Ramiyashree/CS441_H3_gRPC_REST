package com.ramiya.cs441.h3;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.util.stream.Collectors;
import java.time.LocalTime;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class BinarySearchGrpc {


    // Function to find if a specific time is present in the log file are not
    public static boolean findTime(String time) throws IOException {

        //AWS S3 Bucket Details
        String bucketName = "logfilegrpcrest";
        String key = "LogFileGenerator.2021-10-18.log";

        S3Object fullObject = null;

        try {
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withRegion("us-east-2")
                    .withCredentials(DefaultAWSCredentialsProviderChain.getInstance())
                    .build();

            // Get an object and print its contents.
            System.out.println("Downloading an S3 Object");

            fullObject = s3Client.getObject(new GetObjectRequest(bucketName, key));

            InputStream inputStream = fullObject.getObjectContent();

            String text = new BufferedReader(
                    new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining("\n"));

            // binary Search on the log file to find a specific time
            System.out.println("Passing data to perform binary search");

            return findTimeInLog(text, time);
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

    //Binary Search over the log file to find the particular log time
    private static boolean findTimeInLog(String lines, String time) throws IOException {

        int count = lines.split("\n").length - 1;
        String[] stringLines = lines.split("\n");

        int start = 0;
        int end = count;
        int middle = 0;
        boolean found = false;

        System.out.println("Binary Search Started");

        while (start <= end) {
            middle = (start + end) / 2;
            String strMidTime = stringLines[middle].split(" ")[0];
            LocalTime midTime = LocalTime.parse(strMidTime);
            int comparison = midTime.compareTo(LocalTime.parse(time));

            if (comparison == 0) {
                found = true;
                break;
            } else if (comparison < 1) {
                start = middle + 1;
            } else {
                end = middle - 1;
            }
        }
        if (found) {
            System.out.println("Time Found at" + " " + middle + "th" + " " +"line");
        } else {
            System.out.println("Time not found");
        }
        return found;
    }
}