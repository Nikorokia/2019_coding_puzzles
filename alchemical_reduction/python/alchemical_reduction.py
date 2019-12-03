import random

def main(s):
    print("\nOriginal: " + s)
    print("Stable:   " + scan(s))

def scan(s):
    r = True
    while r:
        s, r = scan_once(s)
    return s

def scan_once(s):
    r = False
    if len(s)<=1:    #need two elements
        return s, r

    sl = list(s)
    sl.reverse()
    ns = ""

    while sl:               #while list has elements
        a = sl.pop()        #take first one
        if not sl:          #if list now empty
            ns += a             #tack on to solution
        else:
            b = sl.pop()    #else take next element
            if react(a,b):      #if they react
                pass                #they're gone, start over
                r = True     #log reaction
            else:               #if they don't react
                ns += a             #first element is good, pass it on to solution
                sl.append(b)        #second element unknown, add back to puzzle for assessment
    return ns, r

def react(a,b):
    if a == b.upper() or b == a.upper():
        return True
    else:
        return False

def generate(elements=16,types=4):
    if types>26:
        print("Asked to generate with too many types!")
        types=26
    s = ""
    for _ in range(elements):
        e = chr(random.randrange(0,types+1) + 97)
        p = random.randrange(0,2)
        if p>0:
            e = e.upper()
        s += e
    return s


if __name__ == "__main__":
    main("dabAcCaCBAcCcaDA")
    main(generate(100,5))
