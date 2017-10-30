package com.hpe.findlover.service;

import com.hpe.findlover.model.Writer;

public interface WriterService extends BaseService<Writer> {
    /**
     * 插入文章的同时生成作家
     * @return
     */
    boolean insertWriterAndEssay(String filePath,String pseudonym,String title);
}
