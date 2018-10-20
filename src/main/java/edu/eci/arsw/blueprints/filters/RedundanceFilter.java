/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.filters;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author diana
 */
@Service
public class RedundanceFilter implements Filter{

    @Override
    public void filter(Blueprint bp) {
        System.out.println("Redundancia");
        List<Point> pts0=bp.getPoints();        
        ArrayList<Point> ps1 = new ArrayList();
        for(int i=0;i<pts0.size()-1;i++){
            if(pts0.get(i).getX()!=pts0.get(i+1).getX() &pts0.get(i).getY()!=pts0.get(i+1).getY() ){
                ps1.add(pts0.get(i));
            }
        }
        ps1.add(pts0.get(pts0.size()-1));
        bp.setPoints(ps1); 
    }
    
}
