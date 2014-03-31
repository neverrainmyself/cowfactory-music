package com.springapp.mvc.service;

import com.springapp.mvc.domain.Image;
import com.springapp.mvc.domain.Music;
import org.apache.commons.fileupload.FileItemStream;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: apple
 * Date: 13-8-17
 * Time: 上午9:43
 * To change this template use File | Settings | File Templates.
 */
public interface FileService {
    Image uploadImage(String guid, FileItemStream item) throws IOException;

    void uploadMusic(Music guid, FileItemStream item);
}
