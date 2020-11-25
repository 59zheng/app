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

import java.util.List;

public class Trend {
    /*
     * 轮播
     * */
    private List<Banner> banner;
    /*
     * 土地
     * */
    private List<Land> land;

    public Trend(List<Banner> banner, List<Land> land) {
        this.banner = banner;
        this.land = land;
    }

    public void setBanner(List<Banner> banner) {
        this.banner = banner;
    }

    public List<Banner> getBanner() {
        return this.banner;
    }

    public void setLand(List<Land> land) {
        this.land = land;
    }

    public List<Land> getLand() {
        return this.land;
    }


    public static class Banner {
        //        id
        private int id;

        public Banner(int id, String title, String image, String url) {
            this.id = id;
            this.title = title;
            this.image = image;
            this.url = url;
        }

        //
        private String title;

        private String image;

        private String url;

        public void setId(int id) {
            this.id = id;
        }

        public int getId() {
            return this.id;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTitle() {
            return this.title;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getImage() {
            return this.image;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUrl() {
            return this.url;
        }
    }

    public static class Equipment {
        //        是否注册
        private String is_longin;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getAlready() {
            return already;
        }

        public void setAlready(int already) {
            this.already = already;
        }

        public int getSurplus() {
            return surplus;
        }

        public void setSurplus(int surplus) {
            this.surplus = surplus;
        }

        public Equipment(String is_longin, String temperature, String humidity, String light, int eid, int id, int already, int surplus) {
            this.is_longin = is_longin;
            this.temperature = temperature;
            this.humidity = humidity;
            this.light = light;
            this.eid = eid;
            this.id = id;
            this.already = already;
            this.surplus = surplus;
        }

        //温度
        private String temperature;
        //湿度
        private String humidity;
        //光照
        private String light;
//设备id
        private int eid;

        private int id;
//已经使用多少天
        private int already;
//剩余多少太难
        private int surplus;

        public void setIs_longin(String is_longin) {
            this.is_longin = is_longin;
        }

        public String getIs_longin() {
            return this.is_longin;
        }

        public void setTemperature(String temperature) {
            this.temperature = temperature;
        }

        public String getTemperature() {
            return this.temperature;
        }

        public void setHumidity(String humidity) {
            this.humidity = humidity;
        }

        public String getHumidity() {
            return this.humidity;
        }

        public void setLight(String light) {
            this.light = light;
        }

        public String getLight() {
            return this.light;
        }

        public void setEid(int eid) {
            this.eid = eid;
        }

        public int getEid() {
            return this.eid;
        }
    }

    //    按钮类
    public static class Content {
        public Content(int id, String name, String command, String icon) {
            this.id = id;
            this.name = name;
            this.command = command;
            this.icon = icon;
        }

        //id
        private int id;
        //名称
        private String name;
        //动作指令
        private String command;
        //图标
        private String icon;

        public void setId(int id) {
            this.id = id;
        }

        public int getId() {
            return this.id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

        public void setCommand(String command) {
            this.command = command;
        }

        public String getCommand() {
            return this.command;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getIcon() {
            return this.icon;
        }
    }

    //操作以及按钮
    public static class Control {
        public Control(int action_id, String action_name, List<Content> content) {
            this.action_id = action_id;
            this.action_name = action_name;
            this.content = content;
        }

        //操作id
        private int action_id;
        //操作名称
        private String action_name;
        //按钮操作集合
        private List<Content> content;

        public void setAction_id(int action_id) {
            this.action_id = action_id;
        }

        public int getAction_id() {
            return this.action_id;
        }

        public void setAction_name(String action_name) {
            this.action_name = action_name;
        }

        public String getAction_name() {
            return this.action_name;
        }

        public void setContent(List<Content> content) {
            this.content = content;
        }

        public List<Content> getContent() {
            return this.content;
        }
    }

    public static class Source {
        public Source(int action_id, String action_name) {
            this.action_id = action_id;
            this.action_name = action_name;
        }

        private int action_id;

        private String action_name;

        public void setAction_id(int action_id) {
            this.action_id = action_id;
        }

        public int getAction_id() {
            return this.action_id;
        }

        public void setAction_name(String action_name) {
            this.action_name = action_name;
        }

        public String getAction_name() {
            return this.action_name;
        }
    }

    public static class Land {
        public Land(int id, int crop, String crop_name, int is_equipment, String equipment_code, Equipment equipment, List<Control> control, List<Source> source) {
            this.id = id;
            this.crop = crop;
            this.crop_name = crop_name;
            this.is_equipment = is_equipment;
            this.equipment_code = equipment_code;
            this.equipment = equipment;
            this.control = control;
            this.source = source;
        }

        //土地id
        private int id;
        //产品id
        private int crop;
        //产品名称
        private String crop_name;
        //是否有设备
        private int is_equipment;
        //设备通信唯一码
        private String equipment_code;
        //当前土地状态
        private Equipment equipment;
        //操作项集合
        private List<Control> control;
        //溯源相关项
        private List<Source> source;

        public void setId(int id) {
            this.id = id;
        }

        public int getId() {
            return this.id;
        }

        public void setCrop(int crop) {
            this.crop = crop;
        }

        public int getCrop() {
            return this.crop;
        }

        public void setCrop_name(String crop_name) {
            this.crop_name = crop_name;
        }

        public String getCrop_name() {
            return this.crop_name;
        }

        public void setIs_equipment(int is_equipment) {
            this.is_equipment = is_equipment;
        }

        public int getIs_equipment() {
            return this.is_equipment;
        }

        public void setEquipment_code(String equipment_code) {
            this.equipment_code = equipment_code;
        }

        public String getEquipment_code() {
            return this.equipment_code;
        }

        public void setEquipment(Equipment equipment) {
            this.equipment = equipment;
        }

        public Equipment getEquipment() {
            return this.equipment;
        }

        public void setControl(List<Control> control) {
            this.control = control;
        }

        public List<Control> getControl() {
            return this.control;
        }

        public void setSource(List<Source> source) {
            this.source = source;
        }

        public List<Source> getSource() {
            return this.source;
        }
    }


}
