import sys
import hashlib
import os
from os.path import isfile, join
from shutil import copy

class FileFinder(object):
	"""docstring for FileFinder"""
	def __init__(self, dirPath, removeAfterCopy):
		super(FileFinder, self).__init__()
		self.dirPath = dirPath
		self.removeAfterCopy = removeAfterCopy

	def fileExists(self, shaCode):
		"""
	    This function will generate the file names in a directory 
	    tree by walking the tree either top-down or bottom-up. For each 
	    directory in the tree rooted at directory top (including top itself), 
	    it yields a 3-tuple (dirpath, dirnames, filenames).
	    """
		fileHashes = []  # List which will store all of the full filepaths

		# Walk the tree.
		for root, directories, files in os.walk(self.dirPath):
		    for filename in files:
		        # Join the two strings in order to form the full filepath.
		        filePath = os.path.join(root, filename)
		        fileHashes.append(self.identify(filePath))  # Add it to the list.
		return shaCode in fileHashes  # Self-explanatory.

	def identify(self, filePath):
		# BUF_SIZE is totally arbitrary, change for your app!
		BUF_SIZE = 65536  # lets read stuff in 64kb chunks!

		sha1 = hashlib.sha1()

		with open(filePath, 'rb') as f:
		    while True:
		        data = f.read(BUF_SIZE)
		        if not data:
		            break
		        sha1.update(data)
		shaCode = sha1.hexdigest()
		return shaCode

	def copyFiles(self, directoryPath):
		filePaths = []  # List which will store all of the full filepaths

		# Walk the tree.
		for root, directories, files in os.walk(directoryPath):
		    for fileName in files:
		        # Join the two strings in order to form the full filepath.
		        filePath = os.path.join(root, fileName)
		        if self.fileExists(self.identify(filePath)):
		        	print("File: {0} already exists. So, Ignoring this file.".format(filePath))
		        else:
		        	self.saveFile(filePath)
		        print self.removeAfterCopy
		        if self.removeAfterCopy:
		        	os.remove(filePath)
		        	print("File: {0} removed.".format(filePath))

	def saveFile(self, filePath):
		copy(filePath, self.dirPath)
		print("File '{0}' copied at '{1}'".format(filePath, self.dirPath))    

