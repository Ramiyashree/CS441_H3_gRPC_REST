package com.ramiya.cs441.h3

import scalaj.http.{Http, HttpResponse}
import com.google.gson.Gson
import com.typesafe.config.{Config, ConfigFactory}
/**
 * rest client program invokes the AWS Lambda Function
 *
 * The request is sent with timeStamp and timeInterval as the input
 * The response excpeted is MD5HashMessage if the timeStamp is present or 404 level message if the timeStamp is not present in the logFiles
 *
 * The endPoint and timeStamp is mentioned in the config file.
 */

object restClient  extends App {

  //Getting the endpoints, time and time interval from application config file
  val conf: Config = ConfigFactory.load("application.conf")
  val restEndpoint: String = conf.getString("clientConfig.restEndPoint")
  val inputTime : String = conf.getString("clientConfig.inputTime")
  val timeInterval : String = conf.getString("clientConfig.timeInterval")

  // Input to the IntervalFunction is time and time interval
  IntervalFunction(inputTime, timeInterval)

  def IntervalFunction(time: String, delta: String): String = {

    // Request is case class used to model the data
    val timeOBJ = Request(time, delta)
    val gson = new Gson()
    val reqOBJ = gson.toJson(timeOBJ)

    //HTTP Request sent to the AWS Lambda Function
    val request = Http(restEndpoint).postData(reqOBJ).timeout(connTimeoutMs = 2000, readTimeoutMs = 1000000)

    val response = request.asString.body

    //Response from the lambda function is printed here
    print("Response Received" + response)

    response
  }

  case class Request(time: String, delta: String)

}
