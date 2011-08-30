package com.tenline.pinecone.fishshow.application.server.renrenapi.services.impl;

import java.util.TreeMap;

import org.json.simple.JSONObject;

import com.tenline.pinecone.fishshow.application.server.renrenapi.RenrenApiInvoker;
import com.tenline.pinecone.fishshow.application.server.renrenapi.services.AdminService;

/**
 * @author 李勇(yong.li@opi-corp.com) 2011-2-17
 */
public class AdminServiceImpl extends AbstractService implements AdminService {

    public AdminServiceImpl(RenrenApiInvoker renrenApiInvoker) {
        super(renrenApiInvoker);
    }

    public JSONObject getAllocation() {
        TreeMap<String, String> params = new TreeMap<String, String>();
        params.put("method", "admin.getAllocation");
        return this.getResultJSONObject(params);
    }
}
