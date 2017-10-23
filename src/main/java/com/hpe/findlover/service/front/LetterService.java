package com.hpe.findlover.service.front;

import com.hpe.findlover.model.Letter;
import com.hpe.findlover.model.LetterUser;
import com.hpe.findlover.service.BaseService;

import java.util.List;
import java.util.Map;


public interface LetterService extends BaseService<Letter> {
    List<LetterUser> selectOther(int userid);
}
