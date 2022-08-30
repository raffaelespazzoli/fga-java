package io.raffa.permissions.authzed.client;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.authzed.api.v1.PermissionsServiceGrpc;
import com.authzed.api.v1.SchemaServiceGrpc;
import com.authzed.api.v1.WatchServiceGrpc;
import com.authzed.grpcutil.BearerToken;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

@ApplicationScoped
public class AuthzedConnection {

  @ConfigProperty(name = "authzed.hostname")
  String hostname;

  @ConfigProperty(name = "authzed.port", defaultValue = "9000")
  String port;

  @ConfigProperty(name = "authzed.bearerToken", defaultValue = "9000")
  String token;

  BearerToken bearerToken;

  ManagedChannel channel;

  @PostConstruct
  public void CreateChannel() {
    this.channel = ManagedChannelBuilder
        .forTarget(hostname + ":" + port).usePlaintext()
        .build();
    this.bearerToken = new BearerToken(token);
  }

  @Produces
  public PermissionsServiceGrpc.PermissionsServiceBlockingStub getPermissionsService() {
    return PermissionsServiceGrpc.newBlockingStub(channel).withCallCredentials(bearerToken);
  }

  @Produces
  public SchemaServiceGrpc.SchemaServiceBlockingStub getSchemaService() {
    return SchemaServiceGrpc.newBlockingStub(channel).withCallCredentials(bearerToken);
  }

  @Produces
  public WatchServiceGrpc.WatchServiceBlockingStub getWatchService() {
    return WatchServiceGrpc.newBlockingStub(channel).withCallCredentials(bearerToken);
  }

}
