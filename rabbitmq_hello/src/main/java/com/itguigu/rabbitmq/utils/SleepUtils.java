package com.itguigu.rabbitmq.utils;

/**
 * @Author: IsaiahLu
 * @date: 2023/2/1 14:21
 * 沉睡工具类
 */
public class SleepUtils {
    public static void sleep(int second){
        try {
            Thread.sleep(1000*second);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        } finally {
        }
    }
}
