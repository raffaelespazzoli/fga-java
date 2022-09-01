package io.raffa;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.quarkiverse.zanzibar.annotations.FGAPathObject;
import io.quarkiverse.zanzibar.annotations.FGARelation;

@Path("/fgatest")
public class FGATest {
  
  @GET
  @Produces(MediaType.TEXT_PLAIN)
  @Path("{itemid}")
  @FGAPathObject(param = "itemid", type = "item")
  @FGARelation("view")
  public String hello(String itemid) {
      return "Access granted to itemid: "+itemid;
  }

}


