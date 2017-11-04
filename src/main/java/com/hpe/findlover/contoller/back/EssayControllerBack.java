package com.hpe.findlover.contoller.back;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hpe.findlover.model.Admin;
import com.hpe.findlover.model.Essay;
import com.hpe.findlover.service.EssayService;
import com.hpe.findlover.service.UploadService;
import com.hpe.findlover.util.SessionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

/**
 * 专栏作家控制器
 *
 * @author hgh
 */
@Controller
@RequiresRoles("stage")
@RequestMapping("admin/essay")
public class EssayControllerBack {
    private Logger logger = LogManager.getLogger(EssayControllerBack.class);
    @Autowired
    private EssayService essayService;
    @Autowired
    private UploadService uploadService;

    @GetMapping("list")
    public String userList() {
        return "back/writer/essay_list";
    }

    /**
     * 分页搜索
     *
     * @param page     分页对象
     * @param identity 激活或者锁定
     * @param column   按列查询
     * @param keyword  按关键字查询
     * @return
     */
    @GetMapping("info")
    @ResponseBody
    public Object essayList(Page<Essay> page, @RequestParam String identity, @RequestParam String column, @RequestParam String keyword) {
        logger.info("接收参数：pageNum=" + page.getPageNum()
                + ",pageSize=" + page.getPageSize() + ",column=" + column + ",keyword=" + keyword);
        PageHelper.startPage(page.getPageNum(), page.getPageSize());

        List<Essay> essays = essayService.selectAllByIdentity(identity, column, "%" + keyword + "%");
        PageInfo<Essay> pageInfo = new PageInfo<>(essays);
        logger.info(JSONObject.toJSON(pageInfo));
        return pageInfo;
    }

    /**
     * 跳转文章详情界面前读取出文章数据
     * @param id
     * @param model
     * @return
     */
    @GetMapping("detail/{id}")
    public Object essayDetail(@PathVariable int id, Model model) {
        Essay essay = essayService.selectByPrimaryKey(id);
        String filename = essay.getFilename();
        byte[] bytes = uploadService.downloadFile(filename);
        try {
            String content = new String(bytes, "utf-8");
            model.addAttribute("essay", content);
            model.addAttribute("essayObj", essay);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "error";
        }
        return "back/writer/essay_detail";
    }

    /**
     * 修改专栏作家文章审核状态
     *
     * @param id    作家ID
     * @param essay 作家对象
     * @return
     */
    @PutMapping("info/{id}")
    @ResponseBody
    public Object essayList(@PathVariable("id") int id, Essay essay) {
        essay.setId(id);
        logger.debug(essay);
        Essay resultEssay = essayService.selectByPrimaryKey(id);
        //修改专栏作家审核状态时，生成发布时间
        if(resultEssay.getPubTime()==null){
            Admin admin = SessionUtils.getSessionAttr("admin",Admin.class);
            essay.setAdminId(admin.getId());
            essay.setPubTime(new Date());
        }
        return essayService.updateByPrimaryKeySelective(essay);
    }

    /**
     * 删除文章
     *
     * @param id    作家ID
     * @param essay 作家对象
     * @return
     */
    @DeleteMapping("delete/{id}")
    @ResponseBody
    public Object deleteEssay(@PathVariable("id") int id, Essay essay) {
        essay.setId(id);
        logger.debug(essay);
        Essay resultEssay = essayService.selectByPrimaryKey(id);
        //删除txt文件
        boolean result1 = uploadService.deleteFile(resultEssay.getFilename());
        //删除文章列表配图
        boolean result2 = uploadService.deleteFile(resultEssay.getPhoto());
        //return essayService.updateByPrimaryKeySelective(essay);
        return result1 && result2 && essayService.deleteByPrimaryKey(id)>0;
    }

}
