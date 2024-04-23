import itertools, re
from collections import Counter
import math
# import numpy as np



LETTERS = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'

#Find the frequence letter length e.g., 3
FREQ_LETTERS = 3

#Try to minimise the key length
KEY_MAX_LENGTH = 10

repeated = {}

frequency = {'e' : 12.7, 't' : 9.1, 'a' : 8.2, 'o' : 7.5, 'i' : 7.0, 'n' : 6.7, 
's' : 6.3, 'h' : 6.1, 'r' : 6.0, 'd' : 4.3, 'l' : 4.0, 'u' : 2.8, 'c' : 2.8, 
'm' : 2.4, 'w' : 2.4, 'f' : 2.2, 'g' : 2.0, 'y' : 2.0, 'p' : 1.9, 'b' : 1.5, 
'v' : 1.0, 'k' : 0.8, 'j' : 0.2, 'x' : 0.2, 'z' : 0.1, 'q' : 0.1 }


#Write a function to decrypt cipher here
def hacked_plaintext(message):
    # save the space indexes
    space = []
    i = 0
    while i < len(message):
        if message[i] == " ":
            space.append(i)
        i += 1

    # split by space
    c_ls = list(message.split())
    no_dup = set(c_ls)
    
    # remove space
    ciphertext = message
    ciphertext = ciphertext.replace(" ", "")
    ciphertext = ciphertext.replace("\n", "")
    

    
    # find repeated sequences with length 3
    for sub in c_ls:
        if c_ls.count(sub) > 1 and len(sub) == 3:
            repeated[sub] = [index for index, el in enumerate(c_ls) if el == sub]
    
    
    # find distances between repeated sequences
    distances = []
    for element, indexes in repeated.items():
        
        substring = ""
        for word in c_ls[indexes[0]+1:indexes[1]+1]:
            substring += word
        distances.append(len(substring))
    

    # find gcd of distance
    possible_key_length = []
    # bc max key length is 10
    min_gcd = 11
    for i in range(0, len(distances)-1):
        g = math.gcd(distances[i], distances[i+1])
        if g < min_gcd:
            min_gcd = g
    
    key_length = min_gcd
    message = ciphertext

    # group letters from ciphertext based on key length
    c = []
    i = 0
    counter = 0
    string = ""

    for j in range(key_length):
        string += message[counter]
        while i + key_length < len(message):
            string += message[i + key_length]
            i = i + key_length
        
        counter += 1
        i = counter 
        c.append(string)
        string = ""

    # find keyword by calculating chi square score
    key = ""
    chi_square = 0
    lowest_chi = float('inf')
    possible_letter_k = 0

    i = 0
    # going through each string we found based on key length
    for string in c:
        # shifting to the left by one position and do it 24 times
        while i < 25:
            # find every letter count in the string to calculate the chi square
            for letter in LETTERS: 
                # expected value of the letter based on english frequency
                expected = (frequency.get(letter.lower())/100)
                # actual value in the string we have
                actual_count = string.count(letter.lower())/10
                chi_square += pow((actual_count - expected), 2) / expected
            
            # i would represent the position of the keyword in the alphabet
            if chi_square < lowest_chi:
                lowest_chi = chi_square
                possible_letter_k = i
            
            i += 1
            string = shifting(string)
            chi_square = 0
            
        key += LETTERS[possible_letter_k].lower()
        possible_letter_k = ""
        lowest_chi = float('inf')
        i = 0
    

    # decrypt using key
    plaintext = ""
    i = 0
    while i < len(ciphertext):
        p_index = LETTERS.index(ciphertext[i].upper()) - LETTERS.index(key[i % len(key)].upper())
        if p_index < 0:
            p_index +=  26
        else:
            p_index %= 26

        plaintext += LETTERS[p_index]
        i+=1


    # add space back to the ciphertext
    i = 0
    plaintext = list(plaintext)
    while i < len(space):
        # plaintext[space[i]] = " "
        plaintext.insert(space[i], " ")
        i += 1
    plaintext = ''.join(plaintext)
    return plaintext





def shifting(s):
    new = ""
    for i in s:
        new += LETTERS[LETTERS.index(i.upper()) - 1].lower()
    
    return new

def main():
    # Read the top_secret.txt file here
    with open("top_secret.txt", "r") as c:
        ciphertext = c.read()
    # """ """ 

    # print(ciphertext)
    plaintext = hacked_plaintext(ciphertext)

    #Save the plaintext into a new file
    if plaintext != None:
        outfh = open("top_secret_plaintext.txt", "w")
        outfh.write(plaintext)
        outfh.close()
    else:
        print('Fail to decrypt. Try again!')

main()