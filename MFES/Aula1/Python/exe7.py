from pysat.solvers import Minisat22

s = Minisat22()                # cria o solver s

workdays = ['Mon','Tue','Wed','Thu','Fri']
x = {}
c = 1
for d in workdays:
    x[d] = c
    c+=1

s.add_clause([-x['Fri']])
s.add_clause([x['Mon'],x['Wed'],x['Thu']])
s.add_clause([-x['Tue']])
s.add_clause([-x['Thu']])
# s.add_clause([-x['Mon']])
# s.add_clause([-x['Wed']])


if s.solve():
    m = s.get_model()
    print(m)
    for w in workdays:
        if m[x[w]-1]>0:
            print("The meeting can take place on %s."% w)

else:
    print("The meeting cannot take place")

s.delete()