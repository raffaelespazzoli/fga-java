package io.raffa;


import io.quarkus.grpc.GrpcService;
import io.raffa.hello.proto.Greeter;
import io.raffa.hello.proto.HelloReply;
import io.raffa.hello.proto.HelloRequest;
import io.raffa.permissions.openfgagrpc.FGAObject;
import io.raffa.permissions.openfgagrpc.FGARelation;
import io.smallrye.mutiny.Uni;

@GrpcService 
public class HelloService implements Greeter { 

    @Override
    @FGAObject(type="item",idJsonPath=".name")
    @FGARelation(relation="view")
    public Uni<HelloReply> sayHello(HelloRequest request) {
        return Uni.createFrom().item(() ->
                HelloReply.newBuilder().setMessage("Hello " + request.getName()).build()
        );
    }
}
