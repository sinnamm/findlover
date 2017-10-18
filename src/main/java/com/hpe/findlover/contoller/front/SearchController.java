package com.hpe.findlover.contoller.front;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author sinnamm
 * @Date Create in  2017/10/17.
 */
@Controller
@RequestMapping("search")
public class SearchController {

    @GetMapping
    public String search() {
        return "front/search";
    }
}
