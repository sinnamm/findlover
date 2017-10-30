package com.hpe.findlover.service;

import com.github.tobato.fastdfs.domain.FileInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UploadService {
	/**
	 * 上传文件并返回生成的文件路径
	 *
	 * @param file
	 * @return
	 */
	String uploadFile(MultipartFile file) throws IOException;

	String uploadFile(String content, String fileExtension);

	boolean deleteFile(String filePath);

	byte[] downloadFile(String filePath);

	FileInfo getFileInfo(String filePath);
}
