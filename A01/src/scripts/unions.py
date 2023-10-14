class UF:
    def __init__(self, N:int) -> None:
        self.d = list(range(N))
    def connected(self, a: int, b: int) -> bool:
        pass

class QU(UF):
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

class QF(UF):
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



def __quartile(data: list[float], percentile: int) -> float:
    index: int = int((percentile*len(data) + 99) / 100 - 1)
    return data[index]

def samepleMean(samples: list[float]) -> float:
    samples.sort()
    q1: float = __quartile(samples, 25)
    q3: float = __quartile(samples, 75)
    iqr: float = q3 - q1
    ub: float = q3 + 1.5*iqr
    lb: float = q1 - 1.5*iqr
    
    valid: list[float] = [i for i in samples if lb <= i <= ub]
    total: float = sum(valid)

    return total/len(valid) if len(valid)>0 else 0


def measure(uf:UF):
    measured: List[float] = []
    for union_number in unions:
        pairs = gen_ints(union_number)
        samples = []
        for _ in range(SAMPLES):
            start: float = time.time()
            for pair in pairs:
                uf.union(pair[0],pair[1])
            stop: float = time.time()
            samples.append(start-stop)
        measured.append(samepleMean(samples))
        samples = []
    print(measured)


import random
import time
from typing import List
from datetime import datetime
from concurrent.futures import ProcessPoolExecutor

SIZE: int = 1000
STEPS: int  = 100
SAMPLES: int = 100

start = datetime.now()

unions = list(range(100, SIZE, STEPS))
times = []

def gen_ints(n) :
    return [[random.randint(0,SIZE-1),random.randint(0,SIZE-1)] for _ in range(n)]

qf: QF = QF(SIZE)
qu: QU = QU(SIZE)

algos = (qf, qu)

 # create a process pool
with ProcessPoolExecutor(max_workers=2) as executor:
    _ = executor.map(measure, algos)

end = datetime.now()
runtime = end-start
print(f'\n> Runtime : {runtime} \n')
