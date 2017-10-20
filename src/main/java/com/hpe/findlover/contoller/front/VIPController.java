package com.hpe.findlover.contoller.front;

import com.hpe.findlover.model.UserAsset;
import com.hpe.findlover.model.UserBasic;
import com.hpe.findlover.service.front.UserAssetService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tools.ant.taskdefs.Get;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("vip")
public class VIPController {

    @Autowired
    UserAssetService userAssetService;
    private Logger logger = LogManager.getLogger(VIPController.class);
    @GetMapping
    public String vip(HttpServletRequest request)throws Exception{
        UserBasic userBasic=(UserBasic) request.getSession().getAttribute("user");
        UserAsset userAsset=userAssetService.selectByPrimaryKey(userBasic.getId());
        request.setAttribute("userAsset",userAsset);
        if (userAsset==null||userAsset.getVipDeadline()==null){
            request.setAttribute("vip","false");
        }else if(userAsset.getVipDeadline().before(new Date())){
            request.setAttribute("vip","false");
        }else{
            request.setAttribute("vip","true");
        }
        return "front/vip";
    }
    @PostMapping
    @ResponseBody
    public List<String> vip(HttpSession session, String VIPBuyWay, String VIPBuyDay , String VIPBuyMoney) throws Exception{
        int VIPBuyMoney1=-1;
        long month=(long)2592*(long)1000000;
        List list=new LinkedList<String>();

        if ("牵手币".equals(VIPBuyWay)){
            VIPBuyMoney1 = Integer.parseInt(VIPBuyMoney);
        }
        UserBasic user=(UserBasic) session.getAttribute("user");
        UserAsset userAsset= userAssetService.selectByPrimaryKey(user.getId());
        if (("牵手币".equals(VIPBuyWay))){
            if (userAsset == null||userAsset.getAsset()<VIPBuyMoney1){
                list.add("牵手币余额不足");
                list.add("error");
                return list;
            }else if (userAsset.getAsset()>=VIPBuyMoney1){
                userAsset.setAsset(userAsset.getAsset()-VIPBuyMoney1);
                if(userAsset.getVipDeadline()==null || new Date().compareTo(userAsset.getVipDeadline())>0){
                    userAsset.setVipDeadline(new Date());
                }else {
                    userAsset.setVipDeadline(new Date(userAsset.getVipDeadline().getTime()+Integer.parseInt(VIPBuyDay)*month));
                }
            }
            if (userAssetService.updateByPrimaryKey(userAsset)){
                list.add("VIP购买成功");
                list.add("success");
                return list;
            }else{
                list.add("VIP购买失败");
                list.add("error");
                return list;
            }
        }else{
            if (userAsset==null){
                userAsset.setId(user.getId());
                userAsset.setVipDeadline(new Date((new Date()).getTime()+Integer.parseInt(VIPBuyDay)*month));
                userAsset.setCost(Double.parseDouble(VIPBuyMoney.substring(1)));
            }else{
                if(userAsset.getCost()!=null){
                    userAsset.setCost(userAsset.getCost()+Double.parseDouble(VIPBuyMoney.substring(1)));
                }else{
                    userAsset.setCost(Double.parseDouble(VIPBuyMoney.substring(1)));
                }
                if(userAsset.getVipDeadline()==null || new Date().compareTo(userAsset.getVipDeadline())>0){
                    userAsset.setVipDeadline(new Date());
                }else {
                    userAsset.setVipDeadline(new Date(userAsset.getVipDeadline().getTime()+Integer.parseInt(VIPBuyDay)*month));
                }
                if (userAssetService.updateByPrimaryKey(userAsset)){
                    list.add("VIP购买成功");
                    list.add("success");
                    return list;
                }else{
                    list.add("VIP购买失败，正在退款");
                    list.add("error");
                    return list;
                }
            }
        }
        return list;
    }
}
