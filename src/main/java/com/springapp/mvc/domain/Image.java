package com.springapp.mvc.domain;

import com.springapp.mvc.Utils.GuidGenerator;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: zhangchen
 * Date: 2/8/14
 * Time: 11:26 AM
 * To change this template use File | Settings | File Templates.
 */
public class Image{
    private String path;
    private String fileName;
    private String fileSize;
    private String contentType;
    private String guid;

    public Map<String, String> map() {
        Map<String, String> imageMap = new HashMap<String, String>();
        safeValue(imageMap,"path",this.path);
        safeValue(imageMap, "fileName", this.fileName);
        safeValue(imageMap, "fileSize", this.fileSize);
        safeValue(imageMap, "contentType", this.contentType);
        return imageMap;
    }

    private void safeValue(Map<String, String> musicMap, String name,String value) {
        if(StringUtils.hasText(value)){
            musicMap.put(name, value);
        }
    }
    public Image() {
        this.guid = GuidGenerator.createGuid();
    }

    public String getPath() {
        return path;
    }

    public String getFileName() {
        return fileName;
    }


    public String getContentType() {
        return contentType;
    }

    public String getFileSize() {
        return fileSize;
    }

    public static Image instance() {
        return new Image();
    }

    public Image changePath(String path) {
        this.path = path;
        return this;
    }

    public Image changeFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public Image changeFileSize(String fileSize) {
        this.fileSize = fileSize;
        return this;
    }

    public Image changeContentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public Image changeGuid(String imageGuid) {
        this.guid = imageGuid;
        return this;
    }

    public String getGuid() {
        return guid;
    }


}
