package com.hpe.findlover.contoller;

import com.github.tobato.fastdfs.domain.FileInfo;
import com.hpe.findlover.service.UploadService;
import com.hpe.findlover.util.Constant;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@Controller
@RequestMapping("file")
public class FastDFSController {
	@Autowired
	private UploadService uploadService;

	/**
	 * 从FastDFS服务器获取图片，以流的形式返回给客户端
	 * @param filePath
	 * @param response
	 * @throws IOException
	 */
	@GetMapping
	public void getFile(@RequestParam("path") String filePath, HttpServletResponse response) throws IOException {
		if("male".equals(filePath)){
			filePath = Constant.MALE_PHOTO;
		}else if("female".equals(filePath)){
			filePath = Constant.FEMALE_PHOTO;
		}
		FileInfo fileInfo = uploadService.getFileInfo(filePath);
		if (fileInfo != null && fileInfo.getFileSize() != 0) {
			byte[] buffer = uploadService.downloadFile(filePath);
			// 清空response
			response.reset();
			// 设置response的Header
			response.addHeader("Content-Disposition", "attachment");
			response.addHeader("Content-Length", String.valueOf(buffer.length));
			// 通过文件流的形式写到客户端
			OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
			response.setContentType("application/octet-stream");
			toClient.write(buffer);
			// 写完以后关闭文件流
			toClient.flush();
			toClient.close();
		}
	}
}
