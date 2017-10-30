package com.hpe.findlover;

import com.github.tobato.fastdfs.domain.StorePath;
import com.hpe.findlover.contoller.FastDFSController;
import com.hpe.findlover.service.UploadService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FastDFSTest {
	@Autowired
	private FastDFSController controller;
	@Autowired
	private UploadService uploadService;
	@Test
	public void test1(){
		String url = "group1/M00/00/00/wKhvglntm1yAdSeuAACE4ckXuu0086.jpg";
		System.out.println(StorePath.praseFromUrl(url));
	}
	@Test
	public void test2(){
		String url = "group1/M00/00/00/rBEuvln20X2AKMePAAAXK2q5LxQ380.jpg";
		System.out.println(uploadService.deleteFile(url));
	}
	@Test
	public void testUpload() throws IOException {
		File file = new File("F://Akali.bmp");
		String result = uploadService.uploadFile(new MultipartFile() {
			@Override
			public String getName() {
				return file.getName();
			}

			@Override
			public String getOriginalFilename() {
				return file.getName();
			}

			@Override
			public String getContentType() {
				return null;
			}

			@Override
			public boolean isEmpty() {
				return false;
			}

			@Override
			public long getSize() {
				return file.length();
			}

			@Override
			public byte[] getBytes() throws IOException {
				return null;
			}

			@Override
			public InputStream getInputStream() throws IOException {
				return new FileInputStream(file);
			}

			@Override
			public void transferTo(File file) throws IOException, IllegalStateException {

			}
		});
		System.err.println(result);
	}
}
