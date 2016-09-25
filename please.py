import sys
import os
import math
def please(x):
	f=open('newfile.txt','w')
	f.write('a') #sacrifice storage space to allow program to compile
	f.close() #always close your files just in case they run away
	return x
def factorial(num):
	return math.factorial(num)
def main():
	print(str(please(factorial(1))))
if __name__ == "__main__":
	main()
