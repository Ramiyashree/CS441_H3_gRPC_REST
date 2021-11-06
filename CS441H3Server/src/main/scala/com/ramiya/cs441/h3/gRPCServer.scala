package com.ramiya.cs441.h3

import java.util.Base64
import com.amazonaws.services.lambda.runtime.events.{APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent}
import com.amazonaws.services.lambda.runtime.{Context, RequestHandler}
import scala.collection.JavaConverters._
import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import h3.{TimeData, TimeResponse, logTimeFunctionGrpc}
import scala.language.postfixOps

class gRPCServer extends RequestHandler[APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent] {

  //gRPC Server Lambda Function
  override def handleRequest(input: APIGatewayProxyRequestEvent, context: Context): APIGatewayProxyResponseEvent = {

    // AWS Lambda Logger
    val logger = context.getLogger
    logger.log("Request Body:\n" + input.toString)

    //Decode binary data encoded in base-64 from the request body.
    val message = if (input.getIsBase64Encoded) Base64.getDecoder.decode(input.getBody.getBytes) else input.getBody.getBytes
    logger.log(s"message: (${message.mkString(", ")})")

    // Parses the input time sent from client using protobuf
    val dataTime = TimeData.parseFrom(message)

    //Calls LogTimeSearch which is the protobuf rpc service that parse the input and output based on the protobuf and performs binarySearch
    val logTimeFind = new LogTimeSearch()
    // The result is parsed based on the protobuf
    val result = Await.result(logTimeFind.timeFunction(dataTime), atMost = 5 seconds)
    logger.log(s"result: ${result.result}" + "\n")

    //Encode binary data to be sent to client
    val output = Base64.getEncoder.encodeToString(result.toByteArray)

    logger.log(s"Output: $output" + "\n")

    logger.log(s"Sending Response to Client" + "\n")

    // Send the response
    new APIGatewayProxyResponseEvent()
      .withStatusCode(200)
      .withHeaders(Map("Content-Type" -> "application/grpc+proto").asJava)
      .withIsBase64Encoded(true)
      .withBody(output)
  }

  //Calls BinarySearch by passing the protobuf parsed input data and returns the output that is again parsed based on the protobuf format
  private class LogTimeSearch extends logTimeFunctionGrpc.logTimeFunction{
    override def timeFunction(request: TimeData): Future[TimeResponse] = {
      val time = request.time
      val result1 = BinarySearchGrpc.findTime(time)
      val reply = TimeResponse(result1)
      Future.successful(reply)
    }
  }
}
