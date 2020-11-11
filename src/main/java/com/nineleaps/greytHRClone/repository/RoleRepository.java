package com.nineleaps.greytHRClone.repository;

import com.nineleaps.greytHRClone.model.Role;
import com.nineleaps.greytHRClone.model.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Integer> {
 Role findByRole(UserRoles role);
}
