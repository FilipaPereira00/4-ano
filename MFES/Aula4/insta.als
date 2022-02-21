sig User {
	follows : set User,
	sees : set Photo,
	posts : set Photo,
	suggested : set User
}

sig Influencer extends User {}

sig Photo {
	date : one Day
}
sig Ad extends Photo {}

sig Day {}

// Specify the following properties
// You can check their correctness with the different commands and
// when specifying each property you can assume all the previous ones to be true

pred inv1 {
	// Every image is posted be one user
  
    all x : Photo | some y : User | y -> x in posts
  
   
    // Se dois users postam a mesma foto então eles são o mesmo
    all p : Photo | all x , y : User  | x->p in posts and y->p in posts implies x=y

}


pred inv2 {
	// An user cannot follow itself.
    all x , y : User | x->y in follows implies x!=y
    
    // outra hipótese de resposta
      //all x : User | x->x not in follows
}


pred inv3 {
	// An user only sees (non ad) photos posted by followed users. 
   // Ads can be seen by everyone.
      
    all x , y : User | all p : Photo | (p not in Ad and x->p in sees and y->p in posts) implies (x->y in follows)
	
     
}


pred inv4 {
	// If an user posts an ad then all its posts should be labelled as ads 
    all x : User | all a : Ad | x->a in posts implies (all p : Photo | x->p in posts implies p in Ad) 

}


pred inv5 {
	// Influencers are followed by everyone else
    all x : User | all y : Influencer | x!= y implies x->y in follows 

}


pred inv6 {
	// Influencers post every day
    all x : Influencer | all y : Day | some p : Photo | x->p in posts and p->y in date

}


pred inv7 {
	// Suggested are other users followed by followers but not yet followed
    // CORRIGIR
    all x , y , z : User | x->z in follows and z->y in follows and x->y not in follows implies x->y in suggested

}


pred inv8 {
	// An user only sees ads from followed or suggested users
   all x : User | all a : Ad | x->a in sees implies (some y : User | y->a in posts and (x->y in follows or x->y in suggested))
  

}
