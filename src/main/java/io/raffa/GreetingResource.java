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
import io.raffa.permissions.authzed.interceptors.PermissionObjectAware;

@Path("/hello")
@PermissionAware
public class GreetingResource implements PermissionObjectAware{

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/{name}")
    @RequirePermission(objectType = "blog/post", permission = "read")
    public String hello(String name) {
        return "Hello "+name;
    }

    public PermissionObject getPermissionObject(ContainerRequestContext requestContext){
        return new PermissionObject() {
            @Override
            public String getID() {
              // TODO Auto-generated method stub
              //requestContext.getUriInfo().getRequestUri().getPath()
              return "1";
            }
        };
    }

}