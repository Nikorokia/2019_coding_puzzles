class Bracelet:
    beads = list()
    def __init__(self, beadslist):
        self.beads = beadslist
    def __str__(self):
        return str(self.beads)
    def __eq__(self, other):
        e = False
        for _ in range(2):
            for _ in range(len(self.beads)):
                self.rotate()
                if self.sameSequenceAs(other):
                    e = True
            self.reverse()
        return e
    def addBead(self, bead):
        self.beads.append(bead)
    def sameSequenceAs(self, other):
        n_es = []
        for i in range(0,len(self.beads)):
            if self.beads[i] == other.beads[i]:
                n_es.append(True)
        return len(n_es) == len(self.beads)
    def reverse(self):
        self.beads.reverse()
    def rotate(self):
        self.beads.append(self.beads.pop(0))
    def copy(self):
        bs = []
        for b in self.beads:
            bs.append(b)
        return Bracelet(bs)

def generateBracelets(b_len, c_num):
    masterlist= []
    sublist = []
    for c in range(c_num):
        sublist.append(Bracelet([c]))
    masterlist.append(sublist.copy())
    for p in range(1, b_len):
        sublist.clear()
        for l in masterlist[p-1]:
            for c in range(c_num):
                l_1 = l.copy()
                l_1.addBead(c)
                sublist.append(l_1.copy())
        masterlist.append(sublist.copy())
    return masterlist[-1]

def refineBracelets(b_list):
    processed_bs = []
    while b_list:
        ies = []
        b = b_list.pop()
        for ob in b_list:
            # print(b, "!=", ob, not b==ob)
            if b != ob:
                ies.append(True)
        if len(ies) == len(b_list):
            processed_bs.append(b)
    return processed_bs

def main(b_len, c_num):
    solution = len(refineBracelets(
                    generateBracelets(
                    b_len,c_num
                )   )   )
    print("\nFor bracelets of length",b_len,"and with",c_num,"different colors,");
    print("  the number of possible unique bracelets is",solution,".\n")

def test():
    a = Bracelet([0,0,0,0])
    b = Bracelet([1,1,1,1])
    print(a)
    print(b)
    print(a==b)

    print("\nRaw List:")
    g = generateBracelets(4,2)
    for l in g:
        print(l)
    
    print("\nRefined List:")
    r = refineBracelets(g)
    for l in r:
        print(l)
    
    main(4,2)

if __name__ == "__main__":
    test()

