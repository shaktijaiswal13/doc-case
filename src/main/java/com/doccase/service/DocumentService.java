package com.doccase.service;

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

}
