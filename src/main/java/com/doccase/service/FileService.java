package com.doccase.service;

import com.doccase.dao.FileDAO;
import com.doccase.domain.File;

public class FileService {

	private FileDAO fileDAO;

	public FileService() {
		fileDAO = new FileDAO();
	}

	public String saveFile(File file) {
		return fileDAO.saveFile(file);
	}

	public File retrieveFile(String id) {
		return fileDAO.retrieveFile(id);
	}
}
