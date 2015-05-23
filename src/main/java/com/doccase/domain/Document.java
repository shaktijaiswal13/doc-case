package com.doccase.domain;

import java.util.List;

public class Document {

	private String id;
	private String name;
	private String scanned;
	private String url;
	private String coloured;
	private String signed;
	private String description;
	private String type;
	private List<String> labels;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getScanned() {
		return scanned;
	}

	public void setScanned(String scanned) {
		this.scanned = scanned;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getColoured() {
		return coloured;
	}

	public void setColoured(String coloured) {
		this.coloured = coloured;
	}

	public String getSigned() {
		return signed;
	}

	public void setSigned(String signed) {
		this.signed = signed;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<String> getLabels() {
		return labels;
	}

	public void setLabels(List<String> labels) {
		this.labels = labels;
	}

}
