package com.tenline.pinecone.fishshow.application.server.renrenapi.services.impl;

import java.util.TreeMap;

import com.tenline.pinecone.fishshow.application.server.renrenapi.RenrenApiInvoker;
import com.tenline.pinecone.fishshow.application.server.renrenapi.services.PageService;

/**
 * @author 李勇(yong.li@opi-corp.com) 2011-2-17
 */
public class PageServiceImpl extends AbstractService implements PageService {

    public PageServiceImpl(RenrenApiInvoker renrenApiInvoker) {
        super(renrenApiInvoker);
    }

    public boolean isFan(int userId, int pageId) {
        TreeMap<String, String> params = new TreeMap<String, String>();
        params.put("method", "pages.isFan");
        params.put("page_id", String.valueOf(pageId));
        params.put("uid", String.valueOf(userId));

        return this.getResultBoolean(params);
    }
}
