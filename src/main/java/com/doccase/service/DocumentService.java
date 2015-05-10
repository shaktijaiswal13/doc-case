package com.doccase.service;

import java.util.List;

import com.doccase.dao.DocumentDAO;
import com.doccase.domain.Document;

public class DocumentService {

	private DocumentDAO documentDAO;

	public DocumentService() {
		documentDAO = new DocumentDAO();
	}

	public String saveDocument(Document document) {
		return documentDAO.saveDocument(document);
	}

	public List<Document> retrieveDocuments() {
		return documentDAO.retrieveDocuments();
	}

}
