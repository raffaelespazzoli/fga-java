package io.raffa;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;

import io.raffa.permissions.annotations.PermissionAware;
import io.raffa.permissions.annotations.RequirePermission;
import io.raffa.permissions.authzed.interceptors.PermissionObject;
import io.raffa.permissions.authzed.interceptors.PermissionObjectProducer;

//@Path("/hello")
//@PermissionAware
public class GreetingResource implements PermissionObjectProducer{

/*     @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("{blogid}")
    @RequirePermission(objectType = "blog/post", permission = "read")
    public String hello(String blogid) {
        return "Hello "+blogid;
    } */

    public PermissionObject getPermissionObject(ContainerRequestContext requestContext,Object[] parameters){
        return new PermissionObject() {
            @Override
            public String getID() {
              // TODO Auto-generated method stub
              //requestContext.getUriInfo().getRequestUri().getPath()
              System.out.println("DEBUG "+parameters[0].toString());
              return parameters[0].toString();
            }
        };
    } 

}