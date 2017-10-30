package com.hpe.findlover.contoller.front;

import com.hpe.findlover.service.UploadService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("writer")
public class WriterController {

    @Value("${fdfs.trackerList}")
    private String ip;
    private Logger logger = LogManager.getLogger(WriterController.class);
    @Autowired
    private UploadService uploadService;

    @RequestMapping
    public String writeUI() {
        logger.debug("fdfs.trackerList地址为" + ip);
        return "front/writer";
    }

    @RequestMapping("upload")
    @ResponseBody
    public void uploadEssay(String essays) throws Exception {
        logger.debug(essays);
    }

    @RequestMapping("uploadconfig")
    @ResponseBody
    public String uploadConfig(){
        BufferedReader reader = null;
        String laststr = "";
        try{
            InputStream stream = this.getClass().getClassLoader().getResourceAsStream("config.json");
            InputStreamReader inputStreamReader = new InputStreamReader(stream, "UTF-8");
            reader = new BufferedReader(inputStreamReader);
            String tempString = null;
            while((tempString = reader.readLine()) != null){
                laststr += tempString;
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
        logger.error(laststr);
        return laststr;
    }

    @RequestMapping("uploadfile")
    @ResponseBody
    public Object uploadFile(MultipartFile upfile) throws Exception {
        //@RequestParam(value = "editormd-image-file", required = false)
        String uploadPhotoPath = null;
        if (upfile != null) {
            //uploadPhotoPath = uploadService.uploadFile(file);
            logger.debug("文件名字" + upfile.getName());
            logger.debug("文件类型" + upfile.getContentType());
            logger.debug("文件大小" + upfile.getSize());
            logger.debug("源文件名称" + upfile.getOriginalFilename());
            logger.error("上传路径" + uploadPhotoPath);
        }
        logger.error("上传路径" + upfile);
        Map<String, Object> map = new HashMap<>();
        /*map.put("success",1);
        map.put("message","上传成功");
        map.put("url",uploadPhotoPath);*/
        map.put("url", "xxxxx");
        map.put("size", "12232");
        map.put("type", "png");
        map.put("state", "SUCCESS");
        return map;
    }
}
