package com.ramiya.cs441.h3

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent
import java.util
import com.amazonaws.services.lambda.runtime.{Context, RequestHandler}
import com.google.gson.Gson
import java.time.LocalTime
import scala.collection.JavaConverters._

/**
 * REST Lambda Function responds to the request raised by the client. The timestamp and timeInterval recieved is passed to the binarySearchFunction
 *
 * Input is the Time Stamp
 * Ouput is true if the TimeStamp is present in the logFile and false if the TimeStamp is not present in the Log File.
 *
 */

class restServer extends RequestHandler[util.Map[String, String], APIGatewayProxyResponseEvent] {

  // Create instance of Gson for (de)serializing Java Map to JSON string
  val gSon = new Gson()

  override def handleRequest(input: util.Map[String, String], context: Context): APIGatewayProxyResponseEvent = {

    // Get AWS Lambda Logger
    val logger = context.getLogger

    logger.log("event:\n" + input)

    //Data sent from Client is parsed here
    val time = LocalTime.parse(input.get("time"))
    val dT = LocalTime.parse(input.get("delta"))

    logger.log("Inputs retrieved:\n" + "TIME" + " " + time + " " + "Time Interval" + " " +dT)

    logger.log("Calling Binary search\n")

    //Binary Search is called to get logs between the time interval
    val result = BinarySearchRest.IntervalTime(time, dT)

    //The response message is formated here
    val response = ResultMessage(result.toString, Map("Content-Type" -> "application/json"))

    val jsonResponse = gSon.toJson(response.body)

    logger.log("Sending Message to Client\n")

    //Response is sent
      new APIGatewayProxyResponseEvent()
      .withStatusCode(response.statusCode)
      .withHeaders(response.getHeaders)
      .withBody(jsonResponse)
  }

  //Response Message is constructed here with status code 400 if the messages are not found between the time interval
  case class ResultMessage(body : String, headers: Map[String, String]) {
    def getHeaders: java.util.Map[String, String] = headers.asJava
    val statusCode : Int = if(body.split(",").length == 1) 404 else 200
  }

  }
