package com.bridgelabz.note.model;

import java.util.Arrays;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class Image {

	private CommonsMultipartFile fileData;
	private byte[] fileDataBytes;
	private String fileName;
	private String fileContentType;

	public CommonsMultipartFile getFileData() {
		return fileData;
	}

	public void setFileData(CommonsMultipartFile fileData) {
		this.fileData = fileData;
	}

	public byte[] getFileDataBytes() {
		return fileDataBytes;
	}

	public void setFileDataBytes(byte[] fileDataBytes) {
		this.fileDataBytes = fileDataBytes;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	@Override
	public String toString() {
		return "Image [fileData=" + fileData + ", fileDataBytes=" + Arrays.toString(fileDataBytes) + ", fileName="
				+ fileName + ", fileContentType=" + fileContentType + "]";
	}
}
