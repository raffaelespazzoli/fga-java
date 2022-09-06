package io.raffa.permissions.openfgagrpc;

import javax.enterprise.context.ApplicationScoped;

import io.grpc.ForwardingServerCall;
import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;

@ApplicationScoped
public class ZanzibarGRPCInterceptor implements ServerInterceptor{
 
  @Override
  public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall,
          Metadata metadata, ServerCallHandler<ReqT, RespT> serverCallHandler) {
      // look for FGA annotations
      System.out.println("header received from client:" + metadata);
      return serverCallHandler.startCall(new ForwardingServerCall.SimpleForwardingServerCall<>(serverCall) {}, metadata);      
  }

}