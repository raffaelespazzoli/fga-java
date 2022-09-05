package io.raffa;

import java.time.Duration;
import java.util.Arrays;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.quarkiverse.openfga.client.AuthorizationModelClient;
import io.quarkiverse.openfga.client.model.TupleKey;
import io.quarkiverse.zanzibar.annotations.FGAPathObject;
import io.quarkiverse.zanzibar.annotations.FGARelation;

@Path("locations")
public class Locations {

  @Inject
  AuthorizationModelClient authModelClient;
  
  @GET
  @Produces(MediaType.TEXT_PLAIN)
  @Path("{locationid}/items")
  @FGAPathObject(param = "locationid", type = "location")
  @FGARelation("view")
  public String listItemsAtLocations(String locationid) {
      return "Access granted to all items at location: "+locationid;
  }

  @PUT
  @Produces(MediaType.TEXT_PLAIN)
  @Path("{locationid}/items/{itemid}")
  @FGAPathObject(param = "locationid", type = "location")
  @FGARelation("write")
  public String createItem(String locationid,String itemid) {
      authModelClient.write(TupleKey.of("item:"+itemid, "assigned_to_location", "location:"+locationid)).await().atMost(Duration.ofSeconds(1));
      return "Item created at location: "+locationid+" item: "+itemid;
  }

  @DELETE
  @Produces(MediaType.TEXT_PLAIN)
  @Path("{locationid}/items/{itemid}")
  @FGAPathObject(param = "itemid", type = "item")
  @FGARelation("write")
  public String deleteItem(String locationid,String itemid) {
      authModelClient.write(Arrays.asList(new TupleKey[]{}),Arrays.asList(new TupleKey[]{TupleKey.of("item:"+itemid, "assigned_to_location", "location:"+locationid)})).await().atMost(Duration.ofSeconds(1));
      return "Item deleted at location: "+locationid+" itemid: "+itemid;
  }

}


