package com.hpe.findlover.service.impl;

import com.github.tobato.fastdfs.domain.FileInfo;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.hpe.findlover.service.UploadService;
import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;

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
	/**
	 * 将一段字符串生成一个文件上传
	 * @param content 文件内容
	 * @param fileExtension 文件后缀名
	 * @return
	 */
	@Override
	public String uploadFile(String content, String fileExtension) {
		byte[] buff = content.getBytes(Charset.forName("UTF-8"));
		ByteArrayInputStream stream = new ByteArrayInputStream(buff);
		StorePath storePath = storageClient.uploadFile(stream,buff.length, fileExtension,null);
		logger.debug("上传文件到：" + storePath.getFullPath());
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
		return storageClient.downloadFile(storePath.getGroup(), storePath.getPath(), new DownloadByteArray());

	}

	@Override
	public FileInfo getFileInfo(String filePath) {
		StorePath storePath = StorePath.praseFromUrl(filePath);
		FileInfo fileInfo;
		synchronized (storageClient) {
			fileInfo = storageClient.queryFileInfo(storePath.getGroup(), storePath.getPath());
		}
        logger.debug("获取图片信息："+filePath);
        return fileInfo;
	}
}
