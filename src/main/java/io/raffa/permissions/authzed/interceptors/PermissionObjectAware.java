package io.raffa.permissions.authzed.interceptors;

import javax.ws.rs.container.ContainerRequestContext;

public interface PermissionObjectAware {
  PermissionObject getPermissionObject(ContainerRequestContext requestContext);
}
