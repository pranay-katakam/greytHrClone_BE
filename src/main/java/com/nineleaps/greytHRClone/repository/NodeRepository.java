package com.nineleaps.greytHRClone.repository;

import com.nineleaps.greytHRClone.model.Liked;
import com.nineleaps.greytHRClone.model.Node;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NodeRepository extends JpaRepository<Node, Integer> {
    @Query(
            value = "WITH RECURSIVE ancestors(id, parent_id, name, lvl) AS ("
                    + "   SELECT cat.id, cat.parent_id, cat.name, 1 AS lvl "
                    + "   FROM tree cat "
                    + "   WHERE cat.id = :nodeId "
                    + "   UNION ALL "
                    + "   SELECT parent.id, parent.parent_id, parent.name, child.lvl + 1 AS lvl "
                    + "   FROM tree parent "
                    + "   JOIN ancestors child "
                    + "   ON parent.id = child.parent_id "
                    + " )"
                    + "SELECT name from ancestors ORDER BY lvl DESC"
            , nativeQuery = true)
    List<String> findAncestry(@Param("nodeId")int nodeId);

}
