package com.example.Object.Bean;

import com.example.Object.Bean.Lesson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 15151 on 2019/10/19.
 */

public class Student {
    private String name;//学生姓名
    private String number;//学号
    private List<Lesson> mLesson= new ArrayList<Lesson>();//所添加课程
    private Map<String, Integer> map = new HashMap<String,Integer>();//课程分数

    public Student(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
