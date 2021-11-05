## CS441 - Engineering Distributed Objects for Cloud Computing

## Homework 3 - gRPC Lambda - REST

### RAMIYA SHREE SESHAIAH

### Introduction
The objective of this homework is to create and implement RESTful service and a lambda function that are accessed from the clients using gRPC.

Video Link : https://youtu.be/tCL58WP2cHc

The video explains deployment of hadoop application in AWS EMR Instance

### Requirements

SBT installed on your system
AWS CLI installed and configured on your system

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

cd CS441H3Client
sbt run

choose appropriate client to run, to see the output in gRPC nad REST

Time Input is mentioned in the application.conf file.

### API EndPoints URLs

The API is deployed using AWS API Gateway 

grpcEndPoint = "https://koazj6rkt3.execute-api.us-east-2.amazonaws.com/default/grpcBinarySearch"
restEndPoint = "https://t323213c5g.execute-api.us-east-2.amazonaws.com/prod/restapi"

### Project Structure

1) The project has two folders CS441H3Client and CS441H3Server

2) CS441H3Client : Consist of gRPCClient and RESTClient

3) CS441H3Server : Consist of gRPCServer and RESTServer

### gRPC

What is protobuf?
why protobuf?
what is grpc?

gRPC client and server communication takes place via Protobuf

gRPC client and server communicate to see if a log file has a specific time 

Input : Time(HH:MM:SS.ss) 

EG:

Output: 
1) YES :  If the specific time is present
2) NO : If the specific time is not present

## gRPC Client : (CS441H3Client/src/main/scala/gRPCClient)

what is grpc client?

gRPC Client send a request to the server with input(time - HH:MM:SS.ss) to check for the time. The input is parsed to the server in protobuf format.
Once the client receive the data from the server to data is again processed in protobuf format.

##gRPC Server : (CS441H3Server/src/main/scala/gRPServer)

what is gRPC Server?

The request when reached to the server the input data is fetched and processed by the protobuf and then it is passed to the binarySearch function(CS441H3Server/src/main/scala/BinarySearchgRPC).
This function does binary search for the time in the log file.

### REST

what is REST?

Request from the client is sent to the server to check if time intervals exist between the start and end time interval.

Input : Time(HH:MM:SS.ss) and Time Interval(HH:MM:SS.ss) 
Processed Input : Lower Time Interval and Upper Time Interval (plus and minus of time and time interval)

EG: 

Output: 
1) When log messages are present between the time interval : 200 status code with the MD5 Hash Message of the log message between those time intervals.
2) When log messages are not present between the time interval : 400 status code with No log message

## REST Client : (CS441H3Client/src/main/scala/gRPCClient)

what is rest client?

Rest Client send a request to the server with input time(HH:MM:SS.ss) and time interval(HH:MM:SS.ss) to retrieve for the log messages between the interval. 

Once the client receive the response with 200 or 400 level message.

##gRPC Server : (CS441H3Server/src/main/scala/gRPServer)

what is rest Server?

The input sent from the client is processed to get the lower time interval(adding time and time interval) and upper time interval(subtracting time and time interval).
This input is passed to the binarySearch (CS441H3Client/src/main/scala/BinarySearchRest) to find the log messages between the interval.


##BinarySearch

what is binarysearch? why?

Both of these binarySearch fetches the input from the S3Bucket which stores the log file generated from the EC2 instance

1) BinarySearchGrpc 

Performs binary search to search if time is present or not in the log file.

2) BinarySearchRest

Performs binary search to retrieve the log entries between the upper and lower time interval.

###Deployment steps - lambda, ec2, s3
### Output

SNIP AND PUT THE OUTPUT BOTH TERMINAL AND CLOUD WATCH