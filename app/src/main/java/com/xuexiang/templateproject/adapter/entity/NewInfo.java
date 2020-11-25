package com.xuexiang.templateproject.adapter.entity;

import com.xuexiang.templateproject.adapter.entity.User;

import java.io.Serializable;
import java.util.List;

public class NewInfo  implements Serializable {
    private int id;

    public NewInfo(int user_id, String content, String media, int turn_num, int comment_num, int up_num, String address, int power, String addtime, int up, User user, List<Hot> hot) {
        this.user_id = user_id;
        this.content = content;
        this.media = media;
        this.turn_num = turn_num;
        this.comment_num = comment_num;
        this.up_num = up_num;
        this.address = address;
        this.power = power;
        this.addtime = addtime;
        this.up = up;
        this.user = user;
        this.hot = hot;
        this.turn = turn;
    }

    private int user_id;

    private String content;

    private String media;

    private int turn_num;

    private int comment_num;

    private int up_num;

    private String address;

    private int power;

    private String addtime;

    private int up;

    private User user;

    private List<Hot> hot;

    private List<Turn> turn;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getUser_id() {
        return this.user_id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return this.content;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public String getMedia() {
        return this.media;
    }

    public void setTurn_num(int turn_num) {
        this.turn_num = turn_num;
    }

    public int getTurn_num() {
        return this.turn_num;
    }

    public void setComment_num(int comment_num) {
        this.comment_num = comment_num;
    }

    public int getComment_num() {
        return this.comment_num;
    }

    public void setUp_num(int up_num) {
        this.up_num = up_num;
    }

    public int getUp_num() {
        return this.up_num;
    }

    public NewInfo() {
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return this.address;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getPower() {
        return this.power;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getAddtime() {
        return this.addtime;
    }

    public void setUp(int up) {
        this.up = up;
    }

    public int getUp() {
        return this.up;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return this.user;
    }

    public List<Hot> getHot() {
        return hot;
    }

    public List<Turn> getTurn() {
        return turn;
    }

    public void setTurn(List<Turn> turn) {
        this.turn = turn;
    }

    public void setHot(List<Hot> hot) {
        this.hot = hot;
    }


}
