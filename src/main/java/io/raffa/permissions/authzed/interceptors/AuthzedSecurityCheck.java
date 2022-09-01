package io.raffa.permissions.authzed.interceptors;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

import io.quarkus.security.ForbiddenException;
import io.quarkus.security.UnauthorizedException;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.security.spi.runtime.MethodDescription;
import io.quarkus.security.spi.runtime.SecurityCheck;

public class AuthzedSecurityCheck  {

    /*
     * The reason we want to cache RolesAllowedCheck is that it is very common
     * to have a lot of methods using the same roles in the security check
     * In such cases there is no need to have multiple instances of the class hanging around
     * for the entire lifecycle of the application
     */


    // @Override
    // public void apply(SecurityIdentity identity, Method method, Object[] parameters) {
    //     method.
    // }

    // @Override
    // public void apply(SecurityIdentity identity, MethodDescription method, Object[] parameters) {
    //     method.
    // }

}