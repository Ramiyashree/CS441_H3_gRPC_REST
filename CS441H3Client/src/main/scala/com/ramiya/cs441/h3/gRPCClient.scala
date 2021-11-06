package com.ramiya.cs441.h3

import scalaj.http.{Http, HttpResponse}
import com.typesafe.config.{Config, ConfigFactory}
import h3.{TimeData, TimeResponse}

/**
 * gRPC client program invokes the AWS Lambda Function
 *
 * The request is sent with timeStamp as the input
 * The response excpeted is true if the timeStamp is present or false if the timeStamp is not present in the logFiles
 *
 * The endPoint and timeStamp is mentioned in the config file.
 */

object gRPCClient  extends App {

  //Getting the endpoints, time and time interval from application config file
  val conf: Config = ConfigFactory.load("application.conf")
  val grpcEndpoint: String = conf.getString("clientConfig.grpcEndPoint")
  val inputTime: String = conf.getString("clientConfig.inputTime")
  val timeInterval: String = conf.getString("clientConfig.timeInterval")

  // Input to the TimeFunction is passed in TimeData format as mentioned in the protobuf
  TimeFunction(TimeData(time = inputTime))

  def TimeFunction(timeData: TimeData): Boolean = {

    //HTTP Request sent to the AWS Lambda Function
    val request = Http(grpcEndpoint)
      .headers(Map(
        "Content-Type" -> "application/grpc+proto",
        "Accept" -> "application/grpc+proto"
      )).postData(timeData.toByteArray).timeout(connTimeoutMs = 2000, readTimeoutMs = 1000000)

    val response = request.asBytes

    //Response is parsed in TimeResponse format as mentioned in the protobuf
    val responseMessage = TimeResponse.parseFrom(response.body)

    println(s"Time is Present: ${responseMessage.result}")
    //println("Client Response" + response)

    responseMessage.result
  }
}