package com.hpe.findlover.contoller.front;

import com.hpe.findlover.model.UserAsset;
import com.hpe.findlover.model.UserBasic;
import com.hpe.findlover.service.UserAssetService;
import com.hpe.findlover.util.LoverUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping
public class VIPController {
    @Autowired
    private UserAssetService userAssetService;
    private Logger logger = LogManager.getLogger(VIPController.class);

    @GetMapping("vip")
    public String vip(HttpServletRequest request) throws Exception {
        return "front/vip";
    }

    @PostMapping("vip")
    @ResponseBody
    public List<String> vip(HttpSession session, String vipBuyWay, String vipBuyDay, String vipBuyMoney) throws Exception {
        int vipBuyMoney1 = -1;
        List<String> list = new ArrayList<String>();
        if ("牵手币".equals(vipBuyWay)) {
            vipBuyMoney1 = Integer.parseInt(vipBuyMoney);
        }
        UserBasic user = (UserBasic) session.getAttribute("user");
        UserAsset userAsset = userAssetService.selectByPrimaryKey(user.getId());
        if (("牵手币".equals(vipBuyWay))) {
            if (userAsset == null || userAsset.getAsset() < vipBuyMoney1) {
                list.add("牵手币余额不足");
                list.add("error");
                return list;
            } else {
                userAsset.setAsset(userAsset.getAsset() - vipBuyMoney1);
                if (userAsset.getVipDeadline() == null || userAsset.getVipDeadline().before(new Date())) {
                    userAsset.setVipDeadline(LoverUtil.addMonth(new Date(), Integer.parseInt(vipBuyDay)));
                } else {
                    userAsset.setVipDeadline(LoverUtil.addMonth(userAsset.getVipDeadline(), Integer.parseInt(vipBuyDay)));
                }
            }
        } else if (userAsset == null) {
            userAsset = new UserAsset();
            userAsset.setId(user.getId());
            Calendar cld = Calendar.getInstance();
            cld.setTime(new Date());
            cld.add(Calendar.MONTH, Integer.parseInt(vipBuyDay));
            userAsset.setVipDeadline(cld.getTime());
            userAsset.setCost(Double.parseDouble(vipBuyMoney.substring(1)));
            if (userAssetService.insertSelective(userAsset)) {
                list.add("VIP购买成功");
                user.setVip(true);
                session.setAttribute("user",user);
                list.add("success");
            } else {
                list.add("VIP购买失败，正在退款");
                list.add("error");
            }
            return list;
        } else {
            if (userAsset.getVipDeadline() == null || userAsset.getVipDeadline().before(new Date())) {
                userAsset.setVipDeadline(LoverUtil.addMonth(new Date(), Integer.parseInt(vipBuyDay)));
            } else {
                userAsset.setVipDeadline(LoverUtil.addMonth(userAsset.getVipDeadline(), Integer.parseInt(vipBuyDay)));
            }
        }
        if (userAssetService.updateByPrimaryKey(userAsset)) {
            list.add("VIP购买成功");
            user.setVip(true);
            session.setAttribute("user",user);
            list.add("success");
        } else {
            list.add("VIP购买失败");
            list.add("error");
        }
        return list;
    }

    @GetMapping("star")
    public String star() {
        return "front/vip";
    }

    @PostMapping("star")
    @ResponseBody
    public List<String> star(HttpSession session, String starBuyWay, int starBuyDay, double starBuyMoney) throws Exception {
        List<String> list = new ArrayList<>();
        UserBasic userBasic = (UserBasic) session.getAttribute("user");
        UserAsset userAsset = userAssetService.selectByPrimaryKey(userBasic.getId());
        if ("牵手币".equals(starBuyWay)) {
            if (userAsset == null || userAsset.getAsset() < starBuyMoney) {
                list.add("星级会员购买失败，余额不足");
                list.add("error");
                return list;
            } else {
                userAsset.setAsset(userAsset.getAsset() - (int) starBuyMoney);
                if (userAsset.getStarDeadline().before(new Date())) {
                    userAsset.setStarDeadline(LoverUtil.addDay(new Date(), starBuyDay));
                } else {
                    userAsset.setStarDeadline(LoverUtil.addDay(userAsset.getStarDeadline(), starBuyDay));
                }
            }
        } else {
            if (userAsset == null) {
                userAsset = new UserAsset();
                userAsset.setId(userBasic.getId());
                userAsset.setStarDeadline(LoverUtil.addDay(new Date(), starBuyDay));
                userAsset.setCost(starBuyMoney);
                if (userAssetService.insertSelective(userAsset)) {
                    list.add("星级会员购买成功");
                    userBasic.setStar(true);
                    session.setAttribute("user",userBasic);
                    list.add("success");
                } else {
                    list.add("星级会员购买失败");
                    list.add("error");
                }
                return list;
            } else if (userAsset.getStarDeadline().before(new Date())) {
                userAsset.setStarDeadline(LoverUtil.addDay(new Date(), starBuyDay));
                userAsset.setCost(userAsset.getCost() + starBuyMoney);
            } else {
                userAsset.setStarDeadline(LoverUtil.addDay(userAsset.getStarDeadline(), starBuyDay));
                userAsset.setCost(userAsset.getCost() + starBuyMoney);
            }
        }
        if (userAssetService.updateByPrimaryKey(userAsset)) {
            list.add("星级会员购买成功");
            userBasic.setStar(true);
            session.setAttribute("user",userBasic);
            list.add("success");
        } else {
            list.add("星级会员购买失败");
            list.add("error");
        }
        return list;
    }
}
