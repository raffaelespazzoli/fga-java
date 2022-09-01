package io.raffa.permissions.authzed.interceptors;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import com.authzed.api.v1.Core.ObjectReference;
import com.authzed.api.v1.Core.SubjectReference;
import com.authzed.api.v1.PermissionService;
import com.authzed.api.v1.PermissionService.CheckPermissionRequest;
import com.authzed.api.v1.PermissionService.CheckPermissionResponse.Permissionship;
import javax.ws.rs.core.Request;
import com.authzed.api.v1.PermissionService.Consistency;
import com.authzed.api.v1.PermissionsServiceGrpc;
import javax.ws.rs.container.ContainerRequestContext;

import io.quarkus.arc.Arc;
import io.raffa.permissions.annotations.PermissionAware;
import io.raffa.permissions.annotations.RequirePermission;
import io.raffa.permissions.exceptions.PermissionException;

@Interceptor
@PermissionAware
public class PermissionVerifier {

  @Inject
  PermissionsServiceGrpc.PermissionsServiceBlockingStub permissionService;

  @AroundInvoke
  Object permissionCheck(InvocationContext context) throws Exception {
    // get data from invocation context
    RequirePermission[] requirePermissions = context.getMethod().getAnnotationsByType(RequirePermission.class);

    if (requirePermissions.length==0) {
      return context.proceed();
    } 

    ContainerRequestContext requestContext = Arc.container().instance(ContainerRequestContext.class).get();
    PermissionSubject permissionSubject= getPermissionSubject(requestContext);
    PermissionObject permissionObject = ((PermissionObjectProducer)context.getTarget()).getPermissionObject(requestContext,context.getParameters());

    for (RequirePermission requirePermission : requirePermissions) {
      boolean allowed = checkPermission(requirePermission.objectType(), permissionObject.getID(),
          permissionSubject.getType(), permissionSubject.getID(), requirePermission.permission());
      if (!allowed) {
        throw new PermissionException();
      }
    }
    return context.proceed();
  }

  boolean checkPermission(String objectObjectType, String objectObjectID, String subjectObjectType,
      String subjectObjectID, String permission) {

    PermissionService.CheckPermissionRequest request = CheckPermissionRequest.newBuilder()
        .setConsistency(
            Consistency.newBuilder()
                .setMinimizeLatency(true)
                .build())
        .setResource(
            ObjectReference.newBuilder()
                .setObjectType(objectObjectType)
                .setObjectId(objectObjectID)
                .build())
        .setSubject(
            SubjectReference.newBuilder()
                .setObject(
                    ObjectReference.newBuilder()
                        .setObjectType(subjectObjectType)
                        .setObjectId(subjectObjectID)
                        .build())
                .build())
        .setPermission(permission)
        .build();

    PermissionService.CheckPermissionResponse response = permissionService.checkPermission(request);

    return response.getPermissionship().getNumber() == Permissionship.PERMISSIONSHIP_HAS_PERMISSION.getNumber();

  }

  PermissionSubject getPermissionSubject(ContainerRequestContext requestContext){
    return new PermissionSubject() {
      @Override
      public String getType() {
        // TODO Auto-generated method stub
        return "blog/user";
      }
      @Override
      public String getID() {
        // TODO Auto-generated method stub
        //requestContext.getHeaders().getFirst("User");
        return "emilia";
      }
    }; 
  }

}
