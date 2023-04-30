/*
 * Name: Tony Adams WAFFO
 * Stuedent number: 300310088
*/

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.*;

/**
 *
 * @author Yahya Alaa
 */
public class Graph {
    List<Integer> nodes;
    Map<Integer, List<Integer>> edges;
    
    public Graph(List<Integer> nodes, Map<Integer, List<Integer>> edges) {
        this.nodes = nodes;
        this.edges = edges;
    }
    
    public List<Integer> getGraphNodes() {
        return this.nodes;
    }
    
    public Map<Integer, List<Integer>> getGraphEdges() {
        return this.edges;
    }

    public Map<Integer, Double> calPageRank(){
       // implement your function here

       //previous and current pageRank will help checking the convergency to determine if we most stop the loop or not
       Map<Integer, Double> pageRank=new HashMap<>();
       Map<Integer, Double> prevPageRank=new HashMap<>();
       
       //set all PR to a value of 1
       for(Integer node:nodes){
        pageRank.put(node,1.0);
        prevPageRank.put(node,1.0);
       }

       //help visualising the pagerank
       
       /*System.out.println("pageRank before\n\n");
       for(Map.Entry<Integer, Double> entry:pageRank.entrySet()){
        System.out.println(entry.getKey()+" --> "+entry.getValue());
        }
        for(Map.Entry<Integer, Double> entry:prevPageRank.entrySet()){
            System.out.println(entry.getKey()+" --> "+entry.getValue());
        }*/

        double dFactor=0.85;            //damping factor
       double result;                   //it's equivalent to the formula PR(A) which is the result of that calculation
       double partialResult;            //it's equivalent to the formula d(PR(Ti)/C(Ti)+......+PR(Tn)/C(Tn))
       boolean stabilization=false;     // the pagerank is not stabilized at the beginning
       double convergence;              //help checking if all the node have approximative the same value after every iteration

       //calculate the PR of every node until it is stabilsed
       
       while(stabilization==false){             // iterate until a every node has a stable PR

        for( Integer node : nodes){             //calculate the PR of every node
            //System.out.println(node+" is to be processed");
            result=1-dFactor;
            partialResult=0;

            for( Map.Entry<Integer, List<Integer>> entry:edges.entrySet()){     //iterate throuth the edges map to find all nodes where the current node is found
                int key=entry.getKey();                                         //proivde all the node and their outgoing nodes
                //System.out.println("key is "+key);
                List<Integer> values = entry.getValue();
                if(values.size()==0 ){
                    //System.out.println("value is 0 ");
                    continue;
                }else if(key==node){
                    //System.out.println("key=node ");
                    continue;
                }else{
                    if (values.contains(node)){                 // if we find a node that comes to A,we do the operation PR(Ti)/C(Ti) and add it to partial result
                        partialResult+=(pageRank.get(key)/values.size());
                        //System.out.println("Tn is "+key+" ,PR(Tn) is "+pageRank.get(key)+" and C(Tn) is "+values.size());
                    } 
                }
            }
            result+=dFactor*partialResult;
            prevPageRank.put(node,pageRank.get(node));      //keep track of the previous rank of the page 
            pageRank.put(node,result);                      //update the new rank of the page

            //help me visualizing 
            /* 
            System.out.println("the pagerank of "+node+ " is "+pageRank.get(node));
            System.out.println();
            System.out.println();
            */
        }

        for(Integer node: nodes){  //check if stabilization is reached by cheking the difference between the previous and current rank of each page
            convergence=Math.abs(prevPageRank.get(node)-pageRank.get(node));
            if(convergence>0.001){
                stabilization=false; 
                break;
            }
            stabilization=true; // stabiliation reached if the difference for all pages is less than 0.001
        }
    }
    //visualize the pageRank after the algorithm
    /* 
    System.out.println("pageRank after\n\n");
    for(Map.Entry<Integer, Double> entry:pageRank.entrySet()){
     System.out.println(entry.getKey()+" --> "+entry.getValue());
     }
     */
       return pageRank;
    }
}
