class QUnionFind:
    def __init__(self, N:int) -> None:
        self.d = list(range(N))

    def root(self, a:int) -> int:
        while a != self.d[a]:
            a = self.d[a]
        return a
    
    def connected(self, a:int, b:int) -> bool:
        return self.root(a) == self.root(b)

    def union(self, a:int, b:int) -> None:
        ra = self.root(a)
        rb = self.root(b)
        self.d[ra] = rb

class UnionFind:
    def __init__(self, N:int) -> None:
        self.d = list(range(N))
    def connected(self, a:int, b:int) -> bool:
        return self.d[a] == self.d[b]

    def union(self, a:int, b:int) -> None:
        a_id = self.d[a]
        b_id = self.d[b]
        
        for ix, v in enumerate(self.d):
            if v == a_id:
                self.d[ix] = b_id


import random

unions = range(1000, 9000, 500)

def gen_ints(n) :
    return [[random.randint(0,10000),random.randint(0,10000)] for _ in range(n)]

for i in 