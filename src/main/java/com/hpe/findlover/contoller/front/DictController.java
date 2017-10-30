package com.hpe.findlover.contoller.front;

import com.hpe.findlover.model.Dict;
import com.hpe.findlover.service.DictService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author hgh
 */
@RestController
@RequestMapping("/dicts")
public class DictController {
    private Logger logger = LogManager.getLogger(DictController.class);

    @Autowired
    private DictService dictService;

    /**
     * 查询出type为education的数据字典
     * @return 返回Json格式
     */
    @RequestMapping("{type}")
    public List<Dict> selectEducationDict(@PathVariable String type){
        List<Dict> dicts = null;
        try {
            dicts = dictService.selectDictByType(type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dicts;
    }
}
