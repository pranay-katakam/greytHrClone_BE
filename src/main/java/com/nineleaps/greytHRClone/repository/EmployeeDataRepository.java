package com.nineleaps.greytHRClone.repository;

import com.nineleaps.greytHRClone.model.EmployeeData;
import org.json.simple.JSONObject;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeDataRepository extends CrudRepository<EmployeeData, Integer> {

    @Query(value="select * from employee_data where emp_id=?1", nativeQuery = true)
    public JSONObject profile(int id);

}
