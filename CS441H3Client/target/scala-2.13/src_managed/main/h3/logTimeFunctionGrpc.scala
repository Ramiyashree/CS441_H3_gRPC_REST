package h3

object logTimeFunctionGrpc {
  val METHOD_TIME_FUNCTION: _root_.io.grpc.MethodDescriptor[h3.TimeData, h3.TimeResponse] =
    _root_.io.grpc.MethodDescriptor.newBuilder()
      .setType(_root_.io.grpc.MethodDescriptor.MethodType.UNARY)
      .setFullMethodName(_root_.io.grpc.MethodDescriptor.generateFullMethodName("logTimeFunction", "TimeFunction"))
      .setSampledToLocalTracing(true)
      .setRequestMarshaller(_root_.scalapb.grpc.Marshaller.forMessage[h3.TimeData])
      .setResponseMarshaller(_root_.scalapb.grpc.Marshaller.forMessage[h3.TimeResponse])
      .setSchemaDescriptor(_root_.scalapb.grpc.ConcreteProtoMethodDescriptorSupplier.fromMethodDescriptor(h3.H3Proto.javaDescriptor.getServices().get(0).getMethods().get(0)))
      .build()
  
  val SERVICE: _root_.io.grpc.ServiceDescriptor =
    _root_.io.grpc.ServiceDescriptor.newBuilder("logTimeFunction")
      .setSchemaDescriptor(new _root_.scalapb.grpc.ConcreteProtoFileDescriptorSupplier(h3.H3Proto.javaDescriptor))
      .addMethod(METHOD_TIME_FUNCTION)
      .build()
  
  /** PROTOBUF FOR CLIENT SERVER COMMUNICATION
    */
  trait logTimeFunction extends _root_.scalapb.grpc.AbstractService {
    override def serviceCompanion = logTimeFunction
    def timeFunction(request: h3.TimeData): scala.concurrent.Future[h3.TimeResponse]
  }
  
  object logTimeFunction extends _root_.scalapb.grpc.ServiceCompanion[logTimeFunction] {
    implicit def serviceCompanion: _root_.scalapb.grpc.ServiceCompanion[logTimeFunction] = this
    def javaDescriptor: _root_.com.google.protobuf.Descriptors.ServiceDescriptor = h3.H3Proto.javaDescriptor.getServices().get(0)
    def scalaDescriptor: _root_.scalapb.descriptors.ServiceDescriptor = h3.H3Proto.scalaDescriptor.services(0)
    def bindService(serviceImpl: logTimeFunction, executionContext: scala.concurrent.ExecutionContext): _root_.io.grpc.ServerServiceDefinition =
      _root_.io.grpc.ServerServiceDefinition.builder(SERVICE)
      .addMethod(
        METHOD_TIME_FUNCTION,
        _root_.io.grpc.stub.ServerCalls.asyncUnaryCall(new _root_.io.grpc.stub.ServerCalls.UnaryMethod[h3.TimeData, h3.TimeResponse] {
          override def invoke(request: h3.TimeData, observer: _root_.io.grpc.stub.StreamObserver[h3.TimeResponse]): _root_.scala.Unit =
            serviceImpl.timeFunction(request).onComplete(scalapb.grpc.Grpc.completeObserver(observer))(
              executionContext)
        }))
      .build()
  }
  
  /** PROTOBUF FOR CLIENT SERVER COMMUNICATION
    */
  trait logTimeFunctionBlockingClient {
    def serviceCompanion = logTimeFunction
    def timeFunction(request: h3.TimeData): h3.TimeResponse
  }
  
  class logTimeFunctionBlockingStub(channel: _root_.io.grpc.Channel, options: _root_.io.grpc.CallOptions = _root_.io.grpc.CallOptions.DEFAULT) extends _root_.io.grpc.stub.AbstractStub[logTimeFunctionBlockingStub](channel, options) with logTimeFunctionBlockingClient {
    override def timeFunction(request: h3.TimeData): h3.TimeResponse = {
      _root_.scalapb.grpc.ClientCalls.blockingUnaryCall(channel, METHOD_TIME_FUNCTION, options, request)
    }
    
    override def build(channel: _root_.io.grpc.Channel, options: _root_.io.grpc.CallOptions): logTimeFunctionBlockingStub = new logTimeFunctionBlockingStub(channel, options)
  }
  
  class logTimeFunctionStub(channel: _root_.io.grpc.Channel, options: _root_.io.grpc.CallOptions = _root_.io.grpc.CallOptions.DEFAULT) extends _root_.io.grpc.stub.AbstractStub[logTimeFunctionStub](channel, options) with logTimeFunction {
    override def timeFunction(request: h3.TimeData): scala.concurrent.Future[h3.TimeResponse] = {
      _root_.scalapb.grpc.ClientCalls.asyncUnaryCall(channel, METHOD_TIME_FUNCTION, options, request)
    }
    
    override def build(channel: _root_.io.grpc.Channel, options: _root_.io.grpc.CallOptions): logTimeFunctionStub = new logTimeFunctionStub(channel, options)
  }
  
  def bindService(serviceImpl: logTimeFunction, executionContext: scala.concurrent.ExecutionContext): _root_.io.grpc.ServerServiceDefinition = logTimeFunction.bindService(serviceImpl, executionContext)
  
  def blockingStub(channel: _root_.io.grpc.Channel): logTimeFunctionBlockingStub = new logTimeFunctionBlockingStub(channel)
  
  def stub(channel: _root_.io.grpc.Channel): logTimeFunctionStub = new logTimeFunctionStub(channel)
  
  def javaDescriptor: _root_.com.google.protobuf.Descriptors.ServiceDescriptor = h3.H3Proto.javaDescriptor.getServices().get(0)
  
}