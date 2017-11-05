package com.hpe.findlover.contoller.back;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hpe.findlover.model.Message;
import com.hpe.findlover.model.UserAsset;
import com.hpe.findlover.service.MessageService;
import com.hpe.findlover.service.UserAssetService;
import com.hpe.findlover.util.LoverUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author sinnamm
 * @Date Create in  2017/10/27.
 */

@Controller
@RequiresRoles("stage")
@RequestMapping("admin/other_says")
public class OtherSaysControllerBack {

    @Autowired
    private MessageService messageService;
    @Autowired
    private UserAssetService userAssetService;

    private Logger logger = LogManager.getLogger(UserControllerBack.class);

    @GetMapping("/user_message")
    public String userMessage(){
        logger.info("用户动态管理");
        return "back/othersays/user_message";
    }

    @GetMapping("/getMessages")
    @ResponseBody
    public String getMessages(Page<Message> page,  @RequestParam String column, @RequestParam String keyword){

        logger.info("接收参数：pageNum=" + page.getPageNum() + ",pageSize=" + page.getPageSize() + ",column=" + column + ",keyword=" + keyword);

        PageHelper.startPage(page.getPageNum(), page.getPageSize(),"pub_time desc");
        List<Message> messages = messageService.selectMessageByColumn(column, "%" + keyword + "%");

        // 遍历list查出所有相对应的Asset和Detail数据
        for (Message message :
                messages) {
            UserAsset asset = userAssetService.selectByPrimaryKey(message.getUserBasic().getId());
            if (asset != null) {
                message.getUserBasic().setVip(LoverUtil.getDiffOfHours(asset.getVipDeadline()) > 0);
                message.getUserBasic().setStar(LoverUtil.getDiffOfHours(asset.getStarDeadline()) > 0);
            }
        }

        PageInfo pageInfo = new PageInfo(messages);
        return JSON.toJSONStringWithDateFormat(pageInfo,"yyyy-MM-dd HH:mm:ss", SerializerFeature.DisableCircularReferenceDetect);

    }

    @PutMapping("/deleteOne/{messageId}")
    @ResponseBody
    public String deleteOne(@PathVariable int messageId){
        logger.info("动态id为:"+messageId);
        int result = messageService.deleteByPrimaryKey(messageId);
        if (result>0){
            return "success";
        }else {
            return "error";
        }

    }


    @PutMapping("/deleteMany")
    @ResponseBody
    public String deleteMany(@RequestParam(value = "ids[]") int[] ids) throws Exception {
        Set<Integer> idSet = new HashSet<>();
        for (int id : ids) {
            idSet.add(id);
        }
        idSet.forEach(logger::info);
        boolean result = messageService.deleteByPrimaryKeyList(idSet);
        if (result){
            return "success";
        }else {
            return "error";
        }
    }
}
