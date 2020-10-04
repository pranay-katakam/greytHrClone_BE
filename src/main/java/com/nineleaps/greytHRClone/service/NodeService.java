package com.nineleaps.greytHRClone.service;

import com.nineleaps.greytHRClone.model.Node;
import com.nineleaps.greytHRClone.repository.NodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class NodeService {

    @Autowired
    private NodeRepository nodeRepository;


    public ResponseEntity<String> addNode(Node node) {
        nodeRepository.save(node);
        return ResponseEntity.status(HttpStatus.CREATED).body("node saved successfully");
    }

    public ResponseEntity<List<String>> getNode(int id) {
        return ResponseEntity.status(HttpStatus.OK).body(nodeRepository.findAncestry(id));
    }
}
