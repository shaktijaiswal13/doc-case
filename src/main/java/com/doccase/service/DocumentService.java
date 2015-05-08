package com.doccase.service;

import java.util.List;

import com.doccase.dao.DocumentDAO;
import com.doccase.domain.Document;

public class DocumentService {

	private DocumentDAO documentDAO;

	public DocumentService() {
		documentDAO = new DocumentDAO();
	}

	public void saveDocument(Document document) {
		documentDAO.saveDocument(document);
	}

	public Document retrieveDocument(String docName) {
		return documentDAO.retrieveDocument(docName);
	}

	public List<Document> retrieveDocuments() {
		return documentDAO.retrieveDocuments();
	}

}
