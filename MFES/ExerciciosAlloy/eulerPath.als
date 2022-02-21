abstract sig Status {}
one sig Visited , Unvisited extends Status {}

sig Vertice {
 edge : set Aresta
}

var sig VerticeInicial in Vertice {}
var sig VerticeAtual in Vertice {}


sig Aresta {
vertDest : one Vertice ,
var status : one Status
}



fact Init {

no VerticeAtual

no VerticeInicial

//all nides have an even degree
all v : Vertice | rem[#v.edge,2] = 0

//each pair of nodes only has one edge (in each direction)
all a1 , a2 : Aresta | (a1.vertDest = a2.vertDest and a1.~edge = a2.~edge) implies a1=a2

//edge has only one "starting" node
all a : Aresta | one a.~edge


  // no self loops
 all a : Aresta | a.vertDest != a.~edge


    // all nodes are unvisited
   Aresta.status = Unvisited

}


pred inicia[v : Vertice] {
    // guard
     
     no VerticeInicial
     no VerticeAtual
     Aresta.status = Unvisited
     
 
    //effect
     VerticeInicial' = v
     VerticeAtual' = v
      
      
    //frame conditions
    status' = status
    
}

pred travessia [a:Aresta] {
    // guard -> there's an edge that connects v to current node and that edge is unvisited and 
      one VerticeInicial
      one VerticeAtual
      a.status = Unvisited 
      a in VerticeAtual.edge
     
      

      //effect
      a.status' = Visited
      VerticeAtual' = a.vertDest   
      all a1 : Aresta - a | (a1.vertDest = a.~edge and a1.~edge = a.vertDest) implies a1.status' = Visited 
     
       
    
    //frame conditions -> all edges except the ones whose status changed, remian with the same status as before
      all a1 : Aresta - a  | (a1.vertDest  != a.~edge or a1.~edge != a.vertDest) implies a1.status' = a1.status
     
     VerticeInicial' = VerticeInicial      
    
  }


/*pred finaliza [v : Vertice] {
    // guard
     VerticeAtual = VerticeInicial
     Aresta.status =
    //effect

    //frame conditions
    VerticeInicial' = VerticeInicial      
}*/

pred nop {
 status' = status
 VerticeAtual' = VerticeAtual
 VerticeInicial' = VerticeInicial
  
}

 fact Traces {
	 always (nop or some v : Vertice | inicia[v] or some a : Aresta | travessia[a]) //or some v : Vertice | finaliza[v])
}


//pred ex {}
run ex {
  
 // complete
all v1 :Vertice, v2 : Vertice-v1 | v2 in v1.edge.vertDest

} for exactly 5 Vertice , exactly 20 Aresta , 20 steps
