/*
 * Copyright (C) 2020 xuexiangjys(xuexiangjys@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.xuexiang.templateproject.adapter.entity;

import java.io.Serializable;

public class User implements Serializable {
    private String image;

    private String nickname;

    public User(String image, String nickname, int is_fans) {

        this.image = image;
        this.nickname = nickname;
        this.is_fans = is_fans;
    }

    private int is_fans;

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return this.image;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setIs_fans(int is_fans) {
        this.is_fans = is_fans;
    }

    public int getIs_fans() {
        return this.is_fans;
    }
}
