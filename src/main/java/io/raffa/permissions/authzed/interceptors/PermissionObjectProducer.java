package io.raffa.permissions.authzed.interceptors;

import javax.ws.rs.container.ContainerRequestContext;

public interface PermissionObjectProducer {
  PermissionObject getPermissionObject(ContainerRequestContext requestContext,Object[] parameters);
}
