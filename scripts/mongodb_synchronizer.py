import os

from bson.binary import Binary
from pymongo import MongoClient  # Comes with pymongo


class MongoDBSynchronizer:
    def __init__(self,host):
        self.mongoClient = MongoClient(host)

    def synchronizeDB(self, docDirectoryPath):
        db = self.mongoClient.test
        docCollection = db.documentCollection
        for root, directories, fileNames in os.walk(docDirectoryPath):
            for fileName in fileNames:
                dotTokens = fileName.split(".")
                extension = dotTokens[1]
                tokenStrBeforeDot = dotTokens[0]
                tokens = tokenStrBeforeDot.split(",")
                docName = tokens[0].replace(" ", "_")
                if (len(tokens) > 1):
                    description = tokens[1]
                else:
                    description = docName
                labels = tokens[2:]
                filePath = os.path.join(root, fileName)
                fileID = self.saveFile(filePath, self.makeFileName(docName, extension))
                documentID = self.saveDocument(fileID, docName, description, labels)
                print("docName: {0}, description: {1}, labels: {2}, documentID: {3}"
                      .format(docName, description, labels, documentID))

    def saveFile(self, filePath, fileName):
        with open(filePath, "rb") as file:
            f = file.read()
            byteArray = bytes(f)
            fileObj = {
                "name": fileName,
                "data": Binary(byteArray)
            }
            fileID = self.fileCollection().insert_one(fileObj).inserted_id
            # print(fileObj.get('_id'))
            file.close()
        return str(fileID)

    def saveDocument(self, fileID, docName, description, labels):
        documentObj = {
            "name": docName,
            "url": "/rest/file/" + fileID,
            "description": description,
            "labels": labels
        }
        return str(self.documentCollection().insert_one(documentObj).inserted_id)

    def fileCollection(self):
        return self.mongoClient.test.fileCollection

    def documentCollection(self):
        return self.mongoClient.test.documentCollection

    def makeFileName(self, docName, extension):
        return docName + '.' + extension
