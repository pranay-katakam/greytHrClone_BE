package com.nineleaps.greytHRClone.repository;

import com.nineleaps.greytHRClone.model.Role;
import com.nineleaps.greytHRClone.model.UserRoles;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role,Integer> {
 Role findByRole(UserRoles role);
}
