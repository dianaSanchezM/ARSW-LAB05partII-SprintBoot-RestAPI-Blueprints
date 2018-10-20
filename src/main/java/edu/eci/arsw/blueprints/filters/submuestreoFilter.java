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

/**
 *
 * @author diana
 */
public class submuestreoFilter implements Filter{

    @Override
    public void filter(Blueprint bp) {
        System.out.println("Submuestreo");
        List<Point> pts0=bp.getPoints();
        int i=0;
        ArrayList<Point> ps1 = new ArrayList();
        for(Point p:pts0){               
            if(i%2!=0){                
                ps1.add(p);
            }
            i++;
        }        
        bp.setPoints(ps1);
    }
    
}
