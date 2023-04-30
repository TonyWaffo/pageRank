/*
 * Name: Tony Adams WAFFO
 * Stuedent number: 300310088
*/

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.*;

/**
 *
 * @author Yahya Alaa
 */
public class GraphReader {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, URISyntaxException {
        String edgesFilename = "graph.csv";
        Graph graph = readGraph(edgesFilename);
        
        Map<Integer, Double> Pr = graph.calPageRank();

        // print the top ten pages 
        // add you code here
        // Sort the Map by value using Stream API
        LinkedHashMap<Integer, Double> sortedMap = new LinkedHashMap<>();
        Pr.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(entry -> sortedMap.put(entry.getKey(), entry.getValue()));

        System.out.println("\nThe 10 most influencial pages are :\n");
        int x=1;
        for(Map.Entry<Integer, Double> entry:sortedMap.entrySet()){
            if(x==11)
                break;
            System.out.println(x+". Page "+entry.getKey()+" - PR= "+entry.getValue());
            x++;
        }

    }
    
    private static Graph readGraph(String edgesFilename) throws FileNotFoundException, IOException, URISyntaxException {
        URL edgesPath = GraphReader.class.getResource(edgesFilename);
        BufferedReader csvReader = new BufferedReader(new FileReader(edgesPath.getFile()));
        String row;
        List<Integer> nodes = new ArrayList<Integer>();
        Map<Integer, List<Integer>> edges = new HashMap<Integer, List<Integer>>(); 
        
        boolean first = false;
        while ((row = csvReader.readLine()) != null) {
            if (!first) {
                first = true;
                continue;
            }
            
            String[] data = row.split(",");
            
            Integer u = Integer.parseInt(data[0]);
            Integer v = Integer.parseInt(data[1]);
            
            if (!nodes.contains(u)) {
                nodes.add(u);
            }
            if (!nodes.contains(v)) {
                nodes.add(v);
            }
            
            if (!edges.containsKey(u)) {
                // Create a new list of adjacent nodes for the new node u
                List<Integer> l = new ArrayList<Integer>();
                l.add(v);
                edges.put(u, l);
            } else {
                edges.get(u).add(v);
            }
        }
        
        for (Integer node : nodes) {
            if (!edges.containsKey(node)) {
                edges.put(node, new ArrayList<Integer>());
            }
        }
        
        csvReader.close();
        return new Graph(nodes, edges);
    }
    
}
