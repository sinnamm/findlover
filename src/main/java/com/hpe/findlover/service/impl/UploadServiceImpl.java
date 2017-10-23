package com.hpe.findlover.service.impl;

import com.github.tobato.fastdfs.domain.FileInfo;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.hpe.findlover.service.UploadService;
import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author Gss
 */
@Service
public class UploadServiceImpl implements UploadService {
	private Logger logger = LogManager.getLogger(UploadServiceImpl.class);
	@Autowired
	private FastFileStorageClient storageClient;

	@Override
	public String uploadFile(MultipartFile file) throws IOException {
		StorePath storePath = storageClient.uploadFile(file.getInputStream(), file.getSize(), FilenameUtils.getExtension(file.getOriginalFilename()), null);
		logger.debug("上传文件：" + file.getOriginalFilename() + "到：" + storePath.getFullPath());
		return storePath.getFullPath();
	}

	@Override
	public boolean deleteFile(String filePath) {
		StorePath storePath = StorePath.praseFromUrl(filePath);
		try {
			storageClient.deleteFile(storePath.getGroup(), storePath.getPath());
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public byte[] downloadFile(String filePath) {
		StorePath storePath = StorePath.praseFromUrl(filePath);
		logger.debug("下载文件：" + filePath);
		return storageClient.downloadFile(storePath.getGroup(), storePath.getPath(), null);
	}

	@Override
	public FileInfo getFileInfo(String filePath) {
		StorePath storePath = StorePath.praseFromUrl(filePath);
		return storageClient.queryFileInfo(storePath.getGroup(), storePath.getPath());
	}
}
