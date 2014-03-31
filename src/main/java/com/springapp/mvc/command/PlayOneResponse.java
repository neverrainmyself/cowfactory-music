package com.springapp.mvc.command;

/**
 * Created with IntelliJ IDEA.
 * User: zhangchen
 * Date: 2/25/14
 * Time: 3:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class PlayOneResponse {
    private String currentGuid;
    private String currentName;
    private String nextGuid;
    private String nextName;

    public String getCurrentGuid() {
        return currentGuid;
    }

    public String getCurrentName() {
        return currentName;
    }

    public String getNextGuid() {
        return nextGuid;
    }

    public String getNextName() {
        return nextName;
    }
}
