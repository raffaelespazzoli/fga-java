package io.raffa;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import javafx.util.Pair;

import io.quarkiverse.openfga.client.AuthorizationModelClient;
import io.quarkiverse.openfga.client.model.TupleKey;
import io.quarkiverse.zanzibar.annotations.FGAPathObject;
import io.quarkiverse.zanzibar.annotations.FGARelation;
import io.quarkiverse.zanzibar.jaxrs.ZanzibarAuthorizationFilter.Action;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.groups.UniJoin;

@Path("/items")
public class Items {

  @Inject
  AuthorizationModelClient authModelClient;

  @Inject
  SecurityContext securityContext;
  
  
  @GET
  @Produces(MediaType.TEXT_PLAIN)
  @Path("{itemid}")
  @FGAPathObject(param = "itemid", type = "item")
  @FGARelation("view")
  public String getItem(String itemid) {
      return "Access granted to itemid: "+itemid;
  }

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  @Path("specialQuery")
  public String listSpecialquery() {

      //a database operation returns some items, possibly in a stream.
      String[] returnedItems = new String[]{"item:item1","item:item2","item:item3","item:item4"};
      List<String> restultingItems = new ArrayList<String>();

      UniJoin.Builder<Pair<String,Boolean>> builder=Uni.join().builder();
      for (String item:returnedItems){
        builder.add(authModelClient.check(new TupleKey(item, "view", securityContext.getUserPrincipal().getName()),null).map(it -> new Pair<String,Boolean>(item, it)));
      }
      
      builder.joinAll().andCollectFailures().await().atMost(Duration.ofSeconds(5)).stream().filter((p) -> p.getValue()).iterator().forEachRemaining((t) -> restultingItems.add(t.getKey()));
      
      return "Access granted to items: "+restultingItems;
  }






}


