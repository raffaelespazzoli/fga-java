package io.raffa.permissions.authzed.client;

import javax.enterprise.context.ApplicationScoped;

import com.authzed.api.v1.PermissionsServiceGrpc;
import com.authzed.api.v1.SchemaServiceGrpc;
import com.authzed.api.v1.WatchServiceGrpc;

import io.quarkus.grpc.GrpcClient;



@ApplicationScoped
public class AuthzedConnection {

  @GrpcClient("authzed-service")
  public PermissionsServiceGrpc.PermissionsServiceBlockingStub permissionsService;

  @GrpcClient("authzed-service")
  public SchemaServiceGrpc.SchemaServiceBlockingStub schemaService;

  @GrpcClient("authzed-service")
  public WatchServiceGrpc.WatchServiceBlockingStub watchService;

}
