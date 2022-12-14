package io.raffa;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import io.quarkiverse.openfga.client.AuthorizationModelClient;
import io.quarkiverse.openfga.client.model.TupleKey;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.groups.UniJoin;
import javafx.util.Pair;

@Path("/hello")
public class GreetingResource {


    @Inject
    AuthorizationModelClient authModelClient;
  
    @Inject
    SecurityContext securityContext;
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello(String blogid) {
        // a database operation returns some items, possibly in a stream.
        String[] returnedItems = new String[] { "item:item1", "item:item2", "item:item3", "item:item4" };
        List<String> resultingItems = new ArrayList<String>();

        UniJoin.Builder<Pair<String, Boolean>> builder = Uni.join().builder();
        for (String item : returnedItems) {
            builder.add(authModelClient
                    .check(new TupleKey(item, "view", securityContext.getUserPrincipal().getName()), null)
                    .map(it -> new Pair<String, Boolean>(item, it)));
        }

        builder.joinAll().andCollectFailures().await().atMost(Duration.ofSeconds(5)).stream()
                .filter((p) -> p.getValue()).iterator().forEachRemaining((t) -> resultingItems.add(t.getKey()));

        return "Access granted to items: " + resultingItems;
    }

}