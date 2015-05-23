package com.doccase.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.doccase.dao.LabelDAO;

public class LabelService {

	private LabelDAO labelDAO;

	public LabelService() {
		labelDAO = new LabelDAO();
	}

	public List<String> saveLabels(List<String> labels) {
		List<String> savedLabels = labelDAO.retrieveLabels();
		List<String> newLabels = new ArrayList<String>();
		for (String label : labels) {
			if (!savedLabels.contains(label)) {
				newLabels.add(label);
			}
		}
		if (newLabels.size() > 0) {
			return labelDAO.saveLabels(newLabels);
		}
		return Collections.emptyList();
	}

	public int deleteLabel(String label) {
		return labelDAO.deleteLabel(label);
	}

	public List<String> retrieveLabels() {
		return labelDAO.retrieveLabels();
	}
}
