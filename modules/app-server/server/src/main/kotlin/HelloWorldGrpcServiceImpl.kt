import proto.HelloWorld
import proto.HelloWorldApi
import proto.HelloWorldApi.HelloWorldResponse
import proto.HelloWorldServiceGrpcKt
import proto.helloWorldResponse

class HelloWorldGrpcServiceImpl : HelloWorldServiceGrpcKt.HelloWorldServiceCoroutineImplBase() {
    override suspend fun helloWorld(request: HelloWorld.HelloWorldRequest): HelloWorldApi.HelloWorldResponse =
        helloWorldResponse {
            response = "Hello World ! TOTO"
        }
}