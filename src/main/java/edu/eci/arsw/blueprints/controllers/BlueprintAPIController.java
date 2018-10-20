/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.controllers;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.services.BlueprintsServices;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author hcadavid
 */
@RestController
@RequestMapping(value="/blueprints")
@Service
public class BlueprintAPIController {
    @Autowired
    private BlueprintsServices bps;
    
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getBlueprints(){
        try {
            Set<Blueprint> blueprints = bps.getAllBlueprints();
            return new ResponseEntity<>(blueprints,HttpStatus.ACCEPTED);
        } catch (BlueprintNotFoundException ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error obteniendo todos los planos",HttpStatus.NOT_FOUND);
        }
    }
    
    @RequestMapping(method = RequestMethod.GET, value="/{author}")
    public ResponseEntity<?> getBlueprintByAuthor(@PathVariable("author") String author){
        try {
            Set<Blueprint> blueprints=bps.getBlueprintsByAuthor(author);
            return new ResponseEntity<>(blueprints,HttpStatus.ACCEPTED);
        } catch (BlueprintNotFoundException ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("ERROR 404 author "+author+"not found",HttpStatus.NOT_FOUND);
        }
        
    }
    
    @RequestMapping(method = RequestMethod.GET, value="/{author}/{bpname}")
    public ResponseEntity<?> getBlueprintByAuthorAndName(@PathVariable("author") String author, @PathVariable("bpname")String name){
        try {
            Blueprint blueprint=bps.getBlueprint(author, name);
            return new ResponseEntity<>(blueprint,HttpStatus.ACCEPTED);
        } catch (BlueprintNotFoundException ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("ERROR 404 blueprint "+name+"not found",HttpStatus.NOT_FOUND);
        }
        
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> postBlueprint(@RequestBody Blueprint bp){
        try {
            bps.addNewBlueprint(bp);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (BlueprintPersistenceException ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Blueprint has not been created", HttpStatus.FORBIDDEN);
        }
    }
    //curl -i -X POST -HContent-Type:application/json -HAccept:application/json http://localhost:8080/blueprints -d '{"author":"Artur","points":[{"x":11,"y":22},{"x":33,"y":44}],"name":"la pinta"}'
    
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<?> putBlueprint(@RequestBody Blueprint bp){
        try {
            Blueprint blueprint=bps.getBlueprint(bp.getAuthor(), bp.getName());
            blueprint.setPoints(bp.getPoints());
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (BlueprintNotFoundException ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Blueprint not found", HttpStatus.NOT_FOUND);
        }
    }
    //curl -i -X PUT -HContent-Type:application/json -HAccept:application/json http://localhost:8080/blueprints -d '{"author":"Artur","points":[{"x":44,"y":33},{"x":22,"y":11}],"name":"la pinta"}'

}

