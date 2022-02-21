// Modelo abstracto de um sistema de emissão de cartões bancários

abstract sig Status {}
one sig Unissued, Issued, Cancelled extends Status {}

sig Card {
	var status : one Status
}

sig Client {
	var cards : set Card
}

// Algumas das propriedades desejadas para o sistema

assert NoUnissuedCards {
	// Os clientes nunca podem deter cartões unissued
  always Client.cards.status in (Issued + Cancelled)
 //all c : Client | always c.cards.status in (Issued + Cancelled)
 
}

assert NoSharedCards {
	// Ao longo da sua existência um cartão nunca pode pertencer a mais do que um cliente
      //all c : Card , a : Client | always (c in a.cards implies always cards.c = a)
     all c: Card  | always (lone (c.~cards))
}

// Especifique as condições iniciais do sistema

fact Init {

 no cards
 all c : Card | c.status = Unissued

}

// Especifique as operações do sistema por forma a garantir as propriedades
// de segurança

check NoUnissuedCards
check NoSharedCards

// Operação de emitir um cartão para um cliente
pred emit [c : Card, a : Client] {
  
 //guard
  c.status = Unissued
 
// effects
 a.cards' = a.cards + c
 c.status' = Issued

// frame conditions

all x : Client - a | x.cards' = x.cards
all x : Card - c | x.status' = x.status
   

}

// Operação de cancelar um cartão
pred cancel [c : Card] {

//guards
c.status = Issued

// effects
c.status'=Cancelled

// frame conditions
all x : Card - c | x.status' = x.status
cards' = cards
}

pred nop {
	status' = status
	cards' = cards
}

fact Traces {
	always (nop or some c : Card | cancel[c] or some a : Client | emit[c,a])
}

// Especifique um cenário onde 3 cartões são emitidos a pelo menos 2
// clientes e são todos inevitavelmente cancelados, usando os scopes
// para controlar a cardinalidade das assinaturas
// Tente também definir um theme onde os cartões emitidos são verdes
// e os cancelados são vermelhos, ocultando depois toda a informação que
// seja redundante 
// Pode introduzir definições auxiliares no modelo se necessário

run Exemplo {
 eventually Card.status = Cancelled
} for exactly 3 Card,  exactly 2 Client
