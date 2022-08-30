package io.raffa.permissions.authzed.interceptors;

public interface PermissionSubject {
  public String getType();

  public String getID();
}
