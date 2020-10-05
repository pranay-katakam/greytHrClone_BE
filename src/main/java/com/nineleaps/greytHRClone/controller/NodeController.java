package com.nineleaps.greytHRClone.controller;


import com.nineleaps.greytHRClone.model.Node;
import com.nineleaps.greytHRClone.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
@RestController
public class NodeController {

    @Autowired
    private NodeService nodeService;

    @PostMapping(path = "/node")
    public ResponseEntity<String> addNode(@RequestBody Node node) {
        return nodeService.addNode(node);

    }

    @GetMapping(path = "/nodes")
    public ResponseEntity<List<String>> getNode(@RequestParam("id") int id) {
        return nodeService.getNode(id);
    }


}
