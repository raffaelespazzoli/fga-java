package io.raffa.permissions.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.interceptor.InterceptorBinding;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@InterceptorBinding
@Inherited
public @interface PermissionAware {
  
}
