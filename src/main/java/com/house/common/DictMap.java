package com.house.common;

import java.util.HashMap;

public class DictMap {

    public final static HashMap<Integer, String> HOUSE_TYPE = new HashMap<Integer, String>() {{
        put(1, "住宅/小区/公寓");
        put(2, "商住");
    }};


}
