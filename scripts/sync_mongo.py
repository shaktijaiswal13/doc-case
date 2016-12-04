import sys
from mongodb_synchronizer import MongoDBSynchronizer


def run(host,docDirectoryPath):
    mongoDBSynchronizer = MongoDBSynchronizer(host)
    mongoDBSynchronizer.synchronizeDB(docDirectoryPath)


run(sys.argv[1],sys.argv[2])
