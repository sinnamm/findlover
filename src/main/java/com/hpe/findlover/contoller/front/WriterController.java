package com.hpe.findlover.contoller.front;

import com.hpe.findlover.service.UploadService;
import com.hpe.findlover.service.WriterService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("writer")
public class WriterController {
    private Logger logger = LogManager.getLogger(WriterController.class);

    @Autowired
    private UploadService uploadService;
    @Autowired
    private WriterService writerService;


    /**
     * 跳转作者专栏页面
     * @return
     */
    @RequestMapping
    public String writeUI() {
        return "front/writer";
    }

    /**
     * 文章保存
     * @param essays
     * @throws Exception
     */
    @RequestMapping("upload")
    public Object uploadEssay(String essays,String pseudonym,String title, Model model) throws Exception {
        if(essays!=null) {
            logger.debug(essays);
            String filePath = uploadService.uploadFile(essays, "txt");
            //提交文章的同时生成文章并且生成该作家
            //boolean result = writerService.insertWriterAndEssay(filePath, pseudonym, title);
            model.addAttribute("msg", essays);
        }
        //return essays;
        return "front/result";
    }


}
