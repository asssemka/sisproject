package kz.narxoz.springbootdemo1.service.impl;
import kz.narxoz.springbootdemo1.repository.PermissionRepository;
import kz.narxoz.springbootdemo1.service.PermissionService;
import kz.narxoz.springbootdemo1.Model.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {

  @Autowired
  private PermissionRepository permissionRepository;

  @Override
  public List<Permission> simpleUserPermissions() {
    Permission permission = permissionRepository.findByPermission("ROLE_USER");
    List<Permission> permissions = new ArrayList<>();
    permissions.add(permission);
    return permissions;
  }
}
