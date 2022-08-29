package io.raffa.permissions.authzed.interceptors;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.apache.http.ParseException;

import com.authzed.api.v1.PermissionService;
import com.authzed.api.v1.Core.ObjectReference;
import com.authzed.api.v1.Core.SubjectReference;
import com.authzed.api.v1.Core.ZedToken;
import com.authzed.api.v1.PermissionService.CheckPermissionRequest;
import com.authzed.api.v1.PermissionService.Consistency;
import com.authzed.api.v1.PermissionService.CheckPermissionResponse.Permissionship;

import io.raffa.permissions.annotations.RequirePermission;
import io.raffa.permissions.authzed.client.AuthzedConnection;
import io.raffa.permissions.exceptions.PermissionException;
 
@Interceptor 
public class PermissionVerifier {
  

  @Inject
  AuthzedConnection authzedConnection;

  @AroundInvoke 
  Object permissionCheck(InvocationContext context) throws Exception{
    //get data from invocation context
    RequirePermission[] requirePermissions=context.getMethod().getAnnotationsByType(RequirePermission.class);

    for (RequirePermission requirePermission :  requirePermissions){
      boolean allowed=checkPermission(requirePermission.objectType(), requirePermission.objectID(), requirePermission.subjectType(), requirePermission.subjectID(), requirePermission.permission(), ZedToken.newBuilder().getDefaultInstanceForType());
      if (!allowed) {
        throw new PermissionException();
      }
    }
    return context.proceed();
  }

  boolean checkPermission(String objectObjectType,String objectObjectID, String subjectObjectType,String subjectObjectID,String permission, ZedToken zedToken) {

    PermissionService.CheckPermissionRequest request = CheckPermissionRequest.newBuilder()
    .setConsistency(
            Consistency.newBuilder()
                    .setAtLeastAsFresh(zedToken)
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

    PermissionService.CheckPermissionResponse response = authzedConnection.permissionsService.checkPermission(request);

    return response.getPermissionship().getNumber()==Permissionship.PERMISSIONSHIP_HAS_PERMISSION.getNumber();

  }

}
