package com.qfedu.core.vo;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 查询参数
 *
 */
public class Query extends LinkedHashMap<String, Object> {
	private static final long serialVersionUID = 1L;
    public Query(Map<String, Object> params){
        this.putAll(params);
        //分页参数
        Integer limit = Integer.parseInt(params.get("limit").toString());
        Integer offset = Integer.parseInt(params.get("offset").toString());
        this.put("limit", limit);
        this.put("offset", offset);
    }
}
