import sys
from file_finder import FileFinder

def run(srcDirectory, destDirectory, removeAfterCopy):
	fileFinder = FileFinder(destDirectory, removeAfterCopy)
	fileFinder.copyFiles(srcDirectory)	
	
"""Entry point for the application script"""
print("doc collector is called.")
run(sys.argv[1], sys.argv[2], sys.argv[3].upper()=='TRUE')

