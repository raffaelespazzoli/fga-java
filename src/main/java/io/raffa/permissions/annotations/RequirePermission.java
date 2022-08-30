package io.raffa.permissions.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.interceptor.InterceptorBinding;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@InterceptorBinding
@Inherited
public @interface RequirePermission {
  String permission();
  String objectType();
}

