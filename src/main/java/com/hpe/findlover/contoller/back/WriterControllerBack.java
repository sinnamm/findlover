package com.hpe.findlover.contoller.back;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hpe.findlover.model.Writer;
import com.hpe.findlover.service.WriterService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 专栏作家控制器
 * @author hgh
 */
@Controller
@RequiresRoles("stage")
@RequestMapping("admin/writer")
public class WriterControllerBack {
    private Logger logger= LogManager.getLogger(WriterControllerBack.class);
    @Autowired
    private WriterService writerService;

    @GetMapping("list")
    public String userList() {
        return "back/writer/writer_list";
    }

    /**
     * 分页搜索
     * @param page 分页对象
     * @param identity 激活或者锁定
     * @param column 按列查询
     * @param keyword  按关键字查询
     * @return
     */
    @GetMapping("info")
    @ResponseBody
    public Object writerList(Page<Writer> page,@RequestParam String identity,@RequestParam String column,@RequestParam String keyword) {
        logger.info("接收参数：pageNum=" + page.getPageNum()
                + ",pageSize=" + page.getPageSize() + ",column=" + column + ",keyword=" + keyword);
        PageHelper.startPage(page.getPageNum(), page.getPageSize());

        List<Writer> writers = writerService.selectAllByIdentity(identity,column, "%" + keyword + "%");
        PageInfo<Writer> pageInfo = new PageInfo<>(writers);
        logger.info(JSONObject.toJSON(pageInfo));
        return pageInfo;
    }

    /**
     * 修改专栏作家状态、修改作家密码
     * @param id 作家ID
     * @param writer 作家对象
     * @return
     */
    @PutMapping("info/{id}")
    @ResponseBody
    public Object writerList(@PathVariable("id") int id, Writer writer) {
        writer.setId(id);
        logger.debug(writer);
        return writerService.updateByPrimaryKeySelective(writer);
    }

    @PostMapping("add")
    @ResponseBody
    public Object addWriter(Page<Writer> page,Writer writer,@RequestParam String identity,@RequestParam String column,@RequestParam(required = false) String keyword) {
        writer.setRegTime(new Date());
        writer.setEssayCount(0);
        writer.setStatus(1);
        writer.setPassword(new Md5Hash(writer.getPassword(),writer.getUsername()).toString());
        boolean result = writerService.insertUseGeneratedKeys(writer)>0;
        logger.info("接收参数：pageNum=" + page.getPageNum()
                + ",pageSize=" + page.getPageSize() + ",column=" + column + ",keyword=" + keyword);
        logger.debug(writer);
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        List<Writer> writers = writerService.selectAllByIdentity(identity,column, "%" + keyword + "%");
        PageInfo<Writer> pageInfo = new PageInfo<>(writers);

        Map<String, Object> map = new HashMap<>();
        map.put("result",result);
        map.put("writer",writer);
        map.put("page",pageInfo);
        return map;
    }

    @RequestMapping("checkPseudonym")
    @ResponseBody
    public String checkPseudonym(@RequestParam("pseudonym")String pseudonym){
        if (writerService.selectByPseudonym(pseudonym)!=null){
            return "{\"error\":\"该笔名已被注册！\"}";
        }else {
            return "{\"ok\":\"此笔名可用！\"}";
        }
    }

    @RequestMapping("checkUsername")
    @ResponseBody
    public String checkUsername(@RequestParam("username")String username){
        if (writerService.selectByUsername(username)!=null){
            return "{\"error\":\"该帐号已被注册！\"}";
        }else {
            return "{\"ok\":\"此帐号可用！\"}";
        }
    }

}
