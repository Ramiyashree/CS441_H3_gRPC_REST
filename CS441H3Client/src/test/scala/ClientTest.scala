import com.typesafe.config.{Config, ConfigFactory}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.BeforeAndAfter
import org.scalatest.matchers.should.Matchers._

import java.time.LocalTime
import java.time.format.DateTimeFormatter
import scala.util.matching.Regex


class ClientTest extends AnyFlatSpec with Matchers {

  val config = ConfigFactory.load("application.conf")

  it should "Match Endpoint gRPC" in {
    val grpcEndpoint: String = config.getString("clientConfig.grpcEndPoint")
    assert(grpcEndpoint.equals("https://koazj6rkt3.execute-api.us-east-2.amazonaws.com/default/grpcBinarySearch"))
  }

  it should "Match Endpoint REST" in {
    val restEndpoint: String = config.getString("clientConfig.restEndPoint")
    assert(restEndpoint.equals("https://t323213c5g.execute-api.us-east-2.amazonaws.com/prod/restapi"))
  }

}