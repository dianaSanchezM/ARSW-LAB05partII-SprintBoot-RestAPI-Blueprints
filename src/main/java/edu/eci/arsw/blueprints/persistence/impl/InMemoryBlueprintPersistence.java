/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.persistence.impl;

import edu.eci.arsw.blueprints.filters.Filter;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author hcadavid
 */
@Service
public class InMemoryBlueprintPersistence implements BlueprintsPersistence{
    @Autowired
    private Filter filtro;
    private final Map<Tuple<String,String>,Blueprint> blueprints=new ConcurrentHashMap();

    public InMemoryBlueprintPersistence() {
        //load stub data
        Point[] pts=new Point[]{new Point(140, 140),new Point(115, 115)};
        Blueprint bp=new Blueprint("_authorname_", "_bpname_ ",pts);
        blueprints.put(new Tuple<>(bp.getAuthor(),bp.getName()), bp);
        Point[] pts0=new Point[]{new Point(40, 40),new Point(15, 15)};
        Blueprint bp0=new Blueprint("mack", "mypaint",pts0);
        blueprints.put(new Tuple<>(bp0.getAuthor(),bp0.getName()), bp0);
        Point[] pts1=new Point[]{new Point(0, 0),new Point(10, 10)};
        Blueprint bp1=new Blueprint("john", "thepaint",pts1);
        blueprints.put(new Tuple<>(bp1.getAuthor(),bp1.getName()), bp1);
        Point[] pts2=new Point[]{new Point(35, 35),new Point(20, 20),new Point(20,20)};
        Blueprint bp2=new Blueprint("diana", "la pintura",pts2);
        blueprints.put(new Tuple<>(bp2.getAuthor(),bp2.getName()), bp2);
        Point[] pts3=new Point[]{new Point(20, 20),new Point(50, 50)};
        Blueprint bp3=new Blueprint("santiago", "blueprint",pts3);
        blueprints.put(new Tuple<>(bp3.getAuthor(),bp3.getName()), bp3);
        Point[] pts4=new Point[]{new Point(10, 10),new Point(100, 100)};
        Blueprint bp4=new Blueprint("santiago", "puntos1",pts4);
        blueprints.put(new Tuple<>(bp4.getAuthor(),bp4.getName()), bp4);
    }    
    
    @Override
    public void saveBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        if (blueprints.containsKey(new Tuple<>(bp.getAuthor(),bp.getName()))){
            throw new BlueprintPersistenceException("The given blueprint already exists: "+bp);
        }
        blueprints.putIfAbsent(new Tuple<>(bp.getAuthor(),bp.getName()), bp);     
    }

    @Override
    public Blueprint getBlueprint(String author, String bprintname) throws BlueprintNotFoundException {
        Blueprint bp= blueprints.get(new Tuple<>(author, bprintname));
        if (bp==null){
            throw new BlueprintNotFoundException("Blueprint not found");
        }
        filtro.filter(bp);
        return bp;
    }

    @Override
    public Set<Blueprint> getBlueprintByAuthor(String author) throws BlueprintNotFoundException {
        Set<Tuple<String, String>> keys=blueprints.keySet();
        HashSet bps=new HashSet();
        for (Tuple t : keys){
            if (author.equals(t.o1)){
                Blueprint bp =blueprints.get(new Tuple<>(author,(String)t.o2));
                filtro.filter(bp);
                bps.add(bp);
            }
        }
        if(bps.isEmpty()){
            throw new BlueprintNotFoundException(author+" does not exists");
        }
        return bps;
    }
    
    @Override
    public Set<Blueprint> getAllBlueprints() throws BlueprintNotFoundException {
        Set<Tuple<String, String>> keys=blueprints.keySet();
        HashSet bps=new HashSet();
        for (Tuple t : keys){
            Blueprint bp = blueprints.get(new Tuple<>((String)t.o1,(String)t.o2));
            filtro.filter(bp);
            bps.add(bp);
            
        }
        return bps;
    }
}
