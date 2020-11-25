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

public class WeatherVo {
    //城市
    private String city;
    //风速
    private String win_speed;

    //空气质量
    private int air;
    //空气质量等级
    private String air_level;
    //湿度
    private int humidity;
    //当前温度
    private String tem;
    // 高温 白天
    private String tem1;

    //    低温 晚上的
    private String tem2;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getWin_speed() {
        return win_speed;
    }

    public void setWin_speed(String win_speed) {
        this.win_speed = win_speed;
    }

    public int getAir() {
        return air;
    }

    public void setAir(int air) {
        this.air = air;
    }

    public String getAir_level() {
        return air_level;
    }

    public void setAir_level(String air_level) {
        this.air_level = air_level;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public String getTem() {
        return tem;
    }

    public void setTem(String tem) {
        this.tem = tem;
    }

    public String getTem1() {
        return tem1;
    }

    public void setTem1(String tem1) {
        this.tem1 = tem1;
    }

    public String getTem2() {
        return tem2;
    }

    public void setTem2(String tem2) {
        this.tem2 = tem2;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public WeatherVo(String city, String win_speed, int air, String air_level, int humidity, String tem, String tem1, String tem2, Result result) {
        this.city = city;
        this.win_speed = win_speed;
        this.air = air;
        this.air_level = air_level;
        this.humidity = humidity;
        this.tem = tem;
        this.tem1 = tem1;
        this.tem2 = tem2;
        this.result = result;
    }

    private Result result;

    public static class Result {
        //        时间
        private String date;
        //星期
        private String week;
        //天气情况
        private String wea;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getWeek() {
            return week;
        }

        public void setWeek(String week) {
            this.week = week;
        }

        public String getWea() {
            return wea;
        }

        public void setWea(String wea) {
            this.wea = wea;
        }

        public List<String> getWin() {
            return win;
        }

        public void setWin(List<String> win) {
            this.win = win;
        }

        public Result(String date, String week, String wea, List<String> win) {
            this.date = date;
            this.week = week;
            this.wea = wea;
            this.win = win;
        }

        //风向早晚
        private List<String> win;
    }
}
