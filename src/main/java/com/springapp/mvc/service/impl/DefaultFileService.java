package com.springapp.mvc.service.impl;

import com.springapp.mvc.Utils.SecurityUtils;
import com.springapp.mvc.domain.Image;
import com.springapp.mvc.domain.Music;
import com.springapp.mvc.service.FileService;
import org.apache.commons.fileupload.FileItemStream;
import org.springframework.stereotype.Service;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.*;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: apple
 * Date: 13-8-17
 * Time: 上午9:43
 * To change this template use File | Settings | File Templates.
 */
@Service
public class DefaultFileService implements FileService {

    @Override
    public Image uploadImage(String guid, FileItemStream item) throws IOException {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            String contentType = item.getContentType();
            String fileName = item.getName();
            inputStream = item.openStream();

            String userGuid = SecurityUtils.loadUser().getGuid();
            String mainFolderPath = "/Users/zhangchen/Documents/servers/apache-tomcat-7.0.42/imagefile";
            String filePath = mainFolderPath + "/" + userGuid;
            File mainFolder = new File(mainFolderPath);
            if (!mainFolder.exists()) {
                mainFolder.mkdir();
            }

            File userdir = new File(filePath);
            if (!userdir.exists()) {
                userdir.mkdir();
            }

            File imageFile = new File(filePath + "/" + fileName);
            if (!imageFile.exists()) {
                outputStream = new FileOutputStream(imageFile);
                int read = 0;
                byte[] bytes = new byte[1024];
                while ((read = inputStream.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, read);
                }
            }

            return Image.instance().changePath(imageFile.getAbsolutePath()).changeFileName(fileName).changeFileSize(String.valueOf(imageFile.length()))
                    .changeContentType(contentType);
        } catch (IOException e) {
            throw e;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    // outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    @Override
    public void uploadMusic(Music music, FileItemStream item) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            String fileName = item.getName();
            inputStream = item.openStream();

            String userGuid = SecurityUtils.loadUser().getGuid();
            String mainFolderPath = "/Users/zhangchen/Documents/servers/apache-tomcat-7.0.42/musics";
            String filePath = mainFolderPath + "/" + userGuid;
            File mainFolder = new File(mainFolderPath);
            if (!mainFolder.exists()) {
                mainFolder.mkdir();
            }

            File userdir = new File(filePath);
            if (!userdir.exists()) {
                userdir.mkdir();
            }

            File musicFile = new File(filePath + "/" + fileName);
            if (!musicFile.exists()) {
                outputStream = new FileOutputStream(musicFile);
                int read = 0;
                byte[] bytes = new byte[1024];
                while ((read = inputStream.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, read);
                }
            }
            music.changePath(musicFile.getAbsolutePath()).changeFileName(fileName).changeFileSize(String.valueOf(musicFile.length()));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }


}
