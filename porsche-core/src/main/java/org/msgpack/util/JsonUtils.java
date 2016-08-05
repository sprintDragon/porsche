/**
 *  JsonUtils.java Created on 2015/1/8 10:01
 *
 *  Copyright (c) 2015 by www.jd.com.
 */
package org.msgpack.util;

import com.alibaba.fastjson.JSON;

/**
 * Title: 包装了JSON的基本行为，隐藏后面的实现 <br>
 * <p/>
 * Description: <br>
 * <p/>
 * Company: <a href=www.jd.com>京东</a><br>
 *
 * @author <a href=mailto:zhanggeng@jd.com>章耿</a>
 */
public class JsonUtils {

    /**
     * 对象转为json字符串
     *
     * @param object
     *         对象
     * @return json字符串
     */
    public static final String toJSONString(Object object) {
        return JSON.toJSONString(object);
    }

    /**
     * 解析为指定对象
     *
     * @param text
     *         json字符串
     * @param clazz
     *         指定类
     * @param <T>
     *         指定对象
     * @return 指定对象
     */
    public static final <T> T parseObject(String text, Class<T> clazz) {
        return JSON.parseObject(text, clazz);
    }
}