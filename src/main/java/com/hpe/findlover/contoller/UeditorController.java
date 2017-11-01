package com.hpe.findlover.contoller;

import com.hpe.findlover.service.UploadService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("ueditor")
public class UeditorController {
    private Logger logger = LogManager.getLogger(UeditorController.class);

    @Autowired
    private UploadService uploadService;
    /**
     * ueditor返回的配置文件，该config.json文件从资源目录下读取
     * @return
     */
    @RequestMapping("uploadconfig")
    @ResponseBody
    @Cacheable(value = "dict-cache")
    public String uploadConfig(){
        BufferedReader reader = null;
        StringBuffer laststr = new StringBuffer();
        try{
            ClassPathResource resource = new ClassPathResource("static/plugins/ueditor/jsp/config.json");
            //InputStream stream = this.getClass().getClassLoader().getResourceAsStream("config.json");
            InputStreamReader inputStreamReader = new InputStreamReader(resource.getInputStream(), "UTF-8");
            reader = new BufferedReader(inputStreamReader);
            String tempString = null;
            while((tempString = reader.readLine()) != null){
                laststr.append(tempString);
            }
            reader.close();
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        logger.debug("返回的ueditor配置文件内容："+laststr);
        return laststr.toString();
    }

    /**
     * 选择图片上传时对应的响应映射方法
     * @param upfile
     * @return
     * @throws Exception
     */
    @RequestMapping("uploadfile")
    @ResponseBody
    public Object uploadFile(MultipartFile upfile) throws Exception {
        //@RequestParam(value = "editormd-image-file", required = false)
        String uploadPhotoPath = null;
        if (upfile != null) {
            uploadPhotoPath = uploadService.uploadFile(upfile);
            logger.debug("文件名字" + upfile.getName());
            logger.debug("文件类型" + upfile.getContentType());
            logger.debug("文件大小" + upfile.getSize());
            logger.debug("源文件名称" + upfile.getOriginalFilename());
            logger.error("上传路径" + uploadPhotoPath);
        }
        logger.error("上传路径" + uploadPhotoPath);
        Map<String, Object> map = new HashMap<>();
        //uedit要求返回格式
        if(uploadPhotoPath!=null) {
            map.put("url", uploadPhotoPath);
            map.put("title", upfile.getOriginalFilename());
            map.put("original", upfile.getOriginalFilename());
            map.put("state", "SUCCESS");
        }
        return map;
    }
}
