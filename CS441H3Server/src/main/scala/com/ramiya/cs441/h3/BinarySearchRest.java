package com.ramiya.cs441.h3;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalTime;
import java.util.regex.Pattern;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import java.security.NoSuchAlgorithmException;

/**
 * REST Binary Search performs binary search on the log file to see if the the log entries are available between the input log time intervals
 *
 * Input is the Time Stamp and Time Interval
 * Ouput is MD5 HashMessage if the TimeStamp is present in the logFile and 404-level message if the TimeStamp is not present in the Log File.
 *
 */

public class BinarySearchRest {

    //Returns the log messages within the specified time interval

    public static List<String> IntervalTime(LocalTime time, LocalTime dT) throws IOException {

        //AWS S3 Bucket details
        String bucketName = "logfilegrpcrest";
        String key = "LogFileGenerator.2021-10-18.log";

        //Regex Pattern from Application Config
        Config conf = ConfigFactory.load("application.conf");
        String pattern = conf.getString("serverConfig.pattern");

        List<String> HashedMessages = new ArrayList<String>();

        S3Object fullObject = null;

        try {
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withRegion("us-east-2")
                    .withCredentials(DefaultAWSCredentialsProviderChain.getInstance())
                    .build();

            // Get an object and print its contents.
            System.out.println("Downloading an S3object");

            fullObject = s3Client.getObject(new GetObjectRequest(bucketName, key));
            InputStream inputStream = fullObject.getObjectContent();
            String text = new BufferedReader(
                    new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining("\n"));

            String[] stringIntervalLines = text.split("\n");

            //The lower and upper time is calculated from the mentioned time
            LocalTime lowerIntervalTime = time.plusHours(dT.getHour()).plusMinutes(dT.getMinute()).plusSeconds(dT.getSecond()).plusNanos(dT.getNano());
            System.out.println("Lower Time Interval" + lowerIntervalTime);

            LocalTime upperIntervalTime = time.minusHours(dT.getHour()).minusMinutes(dT.getMinute()).minusSeconds(dT.getSecond()).minusNanos(dT.getNano());
            System.out.println("Upper Time Interval" + upperIntervalTime);

            System.out.println("Performing Binary Search to find Upper Time Interval");
            // Performing binary search to find upperTimeInterval
            int startInterval = findTimesInInterval(stringIntervalLines, upperIntervalTime, true);
            // upperTimeInterval Line
            System.out.println("upperIndex" + startInterval);
            // Performing binary search to find lowerTimeInterval

            System.out.println("Performing Binary Search to find Lower Time Interval");
            // Performing binary search to find lowerTimeInterval
            int endInterval = findTimesInInterval(stringIntervalLines, lowerIntervalTime, false);
            // lowerTimeInterval Line
            System.out.println("endIndex" + endInterval);

            //Check if time interval is present or not, If present generate MD5 HashMessage if not, return No time found

            if(startInterval == 1 && endInterval == 0)
                    System.out.println("No Time is found between this interval");
                else {
                    for (int i = startInterval; i < endInterval; i++) {
                        // log message
                        int n = stringIntervalLines[i].split(" ").length;
                        String logMessage  = stringIntervalLines[i].split(" ")[n - 1];

                        // check if the log message matches a regex pattern

                        if(Pattern.matches(pattern, logMessage)){
                            //md5 Hash Function
                            String mdMessage = md5HashFunction(logMessage);
                            HashedMessages.add(mdMessage);
                        }
                    }
            }
        } catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.
            e.printStackTrace();
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return HashedMessages;
    }

    //Perform binary search to retrieve log messages between the given time interval
    private static int findTimesInInterval(String[] lines, LocalTime time, boolean isUpper) throws IOException {

        int count = lines.length - 1;
        int start = 0;
        int end = count;
        int middle = 0;

        while (start <= end) {
            middle = (start + end) / 2;
            String strMidTime = lines[middle].split(" ")[0];
            LocalTime midTime = LocalTime.parse(strMidTime);
            int comparison = midTime.compareTo(time);

            if (comparison == 0) {
                break;
            } else if (comparison < 1) {
                start = middle + 1;
            } else {
                end = middle - 1;
            }
        }

        return isUpper ? start + 1: end + 1;
    }

    //Performing md5hashing for the log messages
    public static String md5HashFunction(String message) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String digest;
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] hash = md.digest(message.getBytes("UTF-8"));
        //converting byte array to Hexadecimal
        StringBuilder sb = new StringBuilder(2*hash.length);
        for(byte b : hash){ sb.append(String.format("%02x", b&0xff)); }
        digest = sb.toString();

    return digest;
    }
}

