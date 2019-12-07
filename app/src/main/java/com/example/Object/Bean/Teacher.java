package com.example.Object.Bean;

import com.example.Object.Bean.Student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 15151 on 2019/10/19.
 */

public class Teacher {

    private String name;//教师姓名
    private String number;//教师号
    private List<Student> mLesson= new ArrayList<Student>();//课程下学生
    private Map<String, Integer> map = new HashMap<String,Integer>();//学生分数
    public Teacher(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
