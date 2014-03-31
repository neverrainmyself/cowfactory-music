package com.springapp.mvc.domain;

import com.springapp.mvc.Utils.GuidGenerator;
import com.springapp.mvc.command.UserCommand;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: zhangchen
 * Date: 1/17/14
 * Time: 4:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class Music {
    private String title;
    private String subTitle;
    private String content;
    private Status status;
    private MusicType musicType;
    private String path;
    private String fileName;
    private String fileSize;
    private int liked;
    private int listened;
    private String createdAt;
    private String guid;
    private String userGuid;
    private String headerImage;

    private int comments;
    private List<User> likedBy;

    /**
     * Only when load music detail
     */
    private User user;

    public Music() {
        this.guid = GuidGenerator.createGuid();
    }

    public Map<String, String> map() {
        Map<String, String> musicMap = new HashMap<String, String>();
        safeValue(musicMap,"title",this.title);
        safeValue(musicMap, "subTitle", this.subTitle);
        safeValue(musicMap, "content", this.content);
        safeValue(musicMap, "status", this.status.toString());
        safeValue(musicMap, "musicType", this.musicType.toString());
        safeValue(musicMap, "path", this.path);
        safeValue(musicMap, "fileName", this.fileName);
        safeValue(musicMap, "fileSize", this.fileSize);
        safeValue(musicMap, "liked", String.valueOf(this.liked));
        safeValue(musicMap, "createdAt", this.createdAt);
        safeValue(musicMap, "userGuid", this.userGuid);
        safeValue(musicMap, "headerImage", this.headerImage);
        safeValue(musicMap, "listened", String.valueOf(this.listened));
        return musicMap;
    }

    private void safeValue(Map<String, String> musicMap, String name,String value) {
        if(StringUtils.hasText(value)){
            musicMap.put(name, value);
        }
    }


    public String getUserGuid() {
        return userGuid;
    }

    public String getTitle() {
        return title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public String getContent() {
        return content;
    }

    public Status getStatus() {
        return status;
    }

    public void change(String header, String subHeader, String content) {
        this.title = header;
        this.subTitle = subHeader;
        this.content = content;
    }

    public void toDraft() {
        this.status = Status.DRAFT;
    }

    public MusicType getMusicType() {
        return musicType;
    }

    public String getPath() {
        return path;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void addFile(String path, String fileName, long length) {
        this.path = path;
        this.fileName = fileName;
        this.fileSize = String.valueOf(length);
    }

    public void removeMusic() {
        this.path = null;
        this.fileName = null;
        this.fileSize = "0";
    }

    public boolean isInit() {
        return status.equals(Status.INIT);
    }

    public int getLiked() {
        return liked;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public static Music instance() {
        return new Music();
    }

    public Music changeTitle(String title) {
        this.title = title;
        return this;
    }

    public Music changeSubTitle(String subTitle) {
        this.subTitle = subTitle;
        return this;
    }

    public Music changeContent(String content) {
        this.content = content;
        return this;
    }

    public Music changeStatus(Status status) {
        this.status = status;
        return this;
    }

    public Music changeMusicType(MusicType musicType) {
        this.musicType = musicType;
        return this;
    }

    public Music changeLiked(Integer liked) {
        this.liked = liked;
        return this;
    }

    public Music changeCreatedAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Music changePath(String path) {
        this.path = path;
        return this;
    }

    public Music changeFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public Music changeFileSize(String fileSize) {
        this.fileSize = fileSize;
        return this;
    }

    public String getGuid() {
        return guid;
    }


    public Music changeUserGuid(String guid) {
        this.userGuid = guid;
        return this;
    }

    public Music changeGuid(String guid) {
        this.guid = guid;
        return this;
    }

    public Music changeHeaderImage(String imageGuid) {
        this.headerImage = imageGuid;
        return this;
    }

    public String getHeaderImage() {
        return headerImage;
    }

    public Music changeUser(User user) {
        this.user = user;
        return this;

    }

    public Music changeListened(int listened) {
        this.listened = listened;
        return this;

    }


    public User getUser() {
        return user;
    }

    public int getListened() {
        return listened;
    }

    public void listened() {
        this.listened++;
    }
    public void liked() {
        this.liked++;
    }
    public void unlike() {
        this.liked--;
    }

    public boolean isVerify() {
        return this.status.equals(Status.VERIFY);
    }

    public boolean isPublish() {
        return this.status.equals(Status.PUBLISH);
    }



    public List<User> getLikedBy() {
        return likedBy;
    }

    public int getComments() {
        return comments;
    }

    public void changeLikedBy(List<User> users) {
        this.likedBy = users;
    }

    public void changeComment(int amount) {
        this.comments = amount;
    }
}
