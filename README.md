## CS441 - Engineering Distributed Objects for Cloud Computing

## Homework 3 - gRPC Lambda - REST

### RAMIYA SHREE SESHAIAH

### Introduction
The objective of this homework is to create and implement RESTful service and a lambda function that are accessed from the clients using gRPC.

Video Link : https://youtu.be/tCL58WP2cHc

The video explains 

### Requirements

SBT installed on your system
Installed and configured AWS CLI on your machine

### Running the test file

Test File can be found under CS441H3Client/src/test/scala/ClientTest

````
sbt clean compile test
````

### Running the project

1) Clone this repository

```
git clone https://github.com/RamiyaShreeSeshaiah/CS441_H3_gRPC_REST.git
```
```
cd CS441_H3_gRPC_REST
```

2) Open the project in intelliJ

3) Generate jar file by running the following command in the terminal

````
sbt clean compile 
````

````
sbt assembly 
````
````
cd CS441H3Client
````
```
sbt run
````

choose appropriate client(gRPC and REST) to run and see the output


### API EndPoints URLs

The API is deployed using AWS API Gateway 

grpcEndPoint = "https://koazj6rkt3.execute-api.us-east-2.amazonaws.com/default/grpcBinarySearch"
restEndPoint = "https://t323213c5g.execute-api.us-east-2.amazonaws.com/prod/restapi"

Note : Time Input and Time Interval is mentioned in the `application.conf` file.

### Project Structure

1) The project has two folders CS441H3Client and CS441H3Server

2) CS441H3Client : Consist of gRPCClient and RESTClient

3) CS441H3Server : Consist of gRPCServer and RESTServer(LAMBDA FUNCTIONS)

### gRPC - Remote Procedure Call

gRPC is a modern, lightweight open source RPC framework from Google that may be used everywhere. It makes it easier to develop connected systems by allowing client and server programs to communicate transparently.

Protocol Buffers (Protobuf) is a cross-platform library developed by Google for serializing and deserializing structured data that is free and open source. It can be used to create programs that communicate with one another via a network or to store data.

Using gRPC, Client program invokes a lambda function deployed on AWS to determine if the desired timestamp is in the log file

**Input :** Time(HH:MM:SS.ss) 

**Output**: 
**1) YES :** If the desired timestamp is present
**2) NO :** If the desired timestamp is not present


A client program can use gRPC to call a method on a server application on another machine as if it were a local object, making it easier to develop distributed applications and services. This interface is implemented on the server side, and a gRPC server is used to handle client requests.

**gRPC Client : (CS441H3Client/src/main/scala/gRPCClient)**

The gRPC Client sends a request to the Lambda function with the input(time - HH:MM:SS.ss) parameter and receives the response after the request is processed by the lambda function.

**##gRPC Server : (CS441H3Server/src/main/scala/gRPServer)**

When the request reaches the lambda function and the input is sent to the binarySearch function (CS441H3Server/src/main/scala/BinarySearchgRPC).
This function performs a binary search in the log file for the desired time.

### REST - Representational state transfer

When a RESTful API is invoked, the server sends a representation of the requested resource's current state to the client.

When submitting an API request to a server, two items must be provided:

 1) The resource's unique identification. The resource's URL, also known as the endpoint, is this.

 2) In the form of an HTTP method or verb, the operation to be done on that resource. GET, POST, PUT, and DELETE are the most frequent HTTP methods.

Client should determine if the log files contain messages in the given time interval from the designated input time stamp and return an MD5-generated hash code from these messages or some 400-level HTTP client response to designate that the log files do not contain any messages in the given time interval given the input of time stamp and time interval server.

Input : Time(HH:MM:SS.ss) and Time Interval(HH:MM:SS.ss) 
Processed Input : Lower Time Interval and Upper Time Interval (plus and minus of time and time interval)

**EG:** Input1 : Time(09:58:55.569) and Input2 : Time Interval(00:00:01.000).
Lower and Upper Time Interval :  9:57:55 and 9:59:55.

**Output:** 
1) When log messages are present between the time interval : 200 status code with the MD5 Hash Message of the log message between those time intervals.
2) When log messages are not present between the time interval : 400 status code with No log message

**REST Client : (CS441H3Client/src/main/scala/gRPCClient)**

Rest Client sends a request to the server with input time(HH:MM:SS.ss) and time interval(HH:MM:SS.ss) to retrieve log messages between the intervals.

Once the server processes the request the client receives the response with MD-5 hash message if log message exist or 400 level error message if the log messages does not exist between the interval.

**gRPC Server : (CS441H3Server/src/main/scala/gRPServer)**

The input sent from the client is processed to get the "lower time interval(adding Time and Time Interval)" and "Upper Time Interval(subtracting Time and Time Interval)".
This input is passed to the binarySearch (CS441H3Client/src/main/scala/BinarySearchRest) to find the log messages between the interval.


##BinarySearch

Binary search is a fast way to find a specific item in a sorted list of things. It works by dividing the section of the list that could contain the item in half until you've reduced down the options to just one.

**1)BinarySearchGrpc** 

Performs a binary search in the log file to see if the required TimeStamp is present or not.

**2) BinarySearchRest**

Binary search is used to look for log entries between the upper and lower time ranges. If log entries are detected, the MD-5 hash algorithm is used to hash the log messages.

Note: BinarySearch functions are written in Java

###Deployment steps - lambda, ec2, s3

### Output

SNIP AND PUT THE OUTPUT BOTH TERMINAL AND CLOUD WATCH