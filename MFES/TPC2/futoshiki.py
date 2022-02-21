from z3 import *
import numpy as np



def check_range(i,size):
    if (i<size or i>=0):
        return True

def check_positions(i,j,i1,j1):
    if (i==i1 and j== j1): 
        return False
    elif (i==i1 or j==j1):
        return True
    else:
        return False

# Abrir o ficheiro para leitura. Nome do ficheiro a abrir é definido na variável file_name

file_name = "tabuleiro.txt"
f = open(file_name, "r")
lines = f.readlines()

# Iniciação do solver
sol = Solver()

# converter valor da primeira linha(tamanho do tabuleiro) para inteiro
size = int(lines[0])
# converter valor da segunda linha(número de valores fixos a adicionar ao tabuleiro) para inteiro
n1 = int(lines[1])
# converter valor da terceira linha(número de restrições de desigualdade) para inteiro
n2 = int(lines[2])


if (size > 0 and n1 >= 0 and n2 >= 0):

    # inicializar matriz de inteiros que representa o puzzle
    X = [ [Int("x_%s_%s" % (i+1,j+1)) for j in range(size)] for i in range(size)]


    # Restrição que impede que uma row tenha digitos repetidos
    sol.add([ Distinct(X[i]) for i in range(size)])

    # Restrição que impede que uma coluna tenha digitos repetidos
    sol.add([Distinct([X[i][j] for i in range(size) ]) for j in range(size)])

    # Restrição que garante que apenas valores entre 1 e size são colocados no puzzle
    sol.add( [ And(X[i][j] >= 1, X[i][j] <= size) for j in range(size) for i in range(size)])


    if (n1>0):
        for i in range(3,n1+3):
            text = [int(n) for n in lines[i].split()]
            if (check_range(text[0],size) and check_range(text[1],size) and check_range(text[2],size)):
                sol.add(X[text[0]-1][text[1]-1] == text[2])
            else:
                print("Restrições inválidas!")
                exit()


    if (n2>0):
        for i in range(3+n1,3+n1+n2):
            text = lines[i].split()
            t0 = int(text[0])
            t1 = int(text[1])
            t2 = int(text[2])
            t3 = int(text[3])
            if (check_range(t0,size) and check_range(t1,size) and check_range(t2,size) and check_range(t3,size)):
                if(check_positions(t0,t1,t2,t3)):
                    if(text[4] == ">"):
                        sol.add(X[t0-1][t1-1] > X[t2-1][t3-1])
                    elif (text[4] == "<"):
                        sol.add(X[t0-1][t1-1] < X[t2-1][t3-1])
                    else:
                        print("Desigualdade inválida!")
                        exit()
                else:
                    print("As desigualdades devem ser entre posições adjacentes e não pode ser a mesma posição!")
                    exit()

            else:
                print("Restrições inválidas!")
                exit()

    
else:
    print("Dados inválidos!!")
    exit()

if sol.check() == sat:
	m = sol.model()
	r = [ [ m.evaluate(X[i][j]) for j in range(size) ]
		for i in range(size) ]

	print(np.array(r))
else:
	print ("Não foi encontrada uma solução...")