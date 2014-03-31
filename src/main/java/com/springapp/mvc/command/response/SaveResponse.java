package com.springapp.mvc.command.response;

/**
 * Created with IntelliJ IDEA.
 * User: zhangchen
 * Date: 2/27/14
 * Time: 6:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class SaveResponse {
    private boolean successded=true;
    private String guid;
    private String errorMessage;

    public SaveResponse(boolean successded, String guid) {
        this.successded = successded;
        this.guid = guid;
    }

    public SaveResponse() {
    }

    public boolean isSuccessded() {
        return successded;
    }

    public String getGuid() {
        return guid;
    }

    public static SaveResponse instance() {
        return new SaveResponse();
    }

    public SaveResponse noHeader() {
        this.errorMessage = "没有添加标题";
        this.successded = false;
        return this;
    }

    public SaveResponse noSubHeader() {
        this.successded = false;
        this.errorMessage = "没有添加副标题";
        return this;
    }

    public SaveResponse changeGuid(String guid) {
        this.guid = guid;
        return this;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public SaveResponse noMusic() {
        this.successded = false;
        this.errorMessage = "没有发现音乐文件";
        return this;
    }
}
