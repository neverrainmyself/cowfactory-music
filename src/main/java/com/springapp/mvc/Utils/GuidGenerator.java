package com.springapp.mvc.Utils;

import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: zhangchen
 * Date: 1/17/14
 * Time: 2:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class GuidGenerator {
    public static String createGuid() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
