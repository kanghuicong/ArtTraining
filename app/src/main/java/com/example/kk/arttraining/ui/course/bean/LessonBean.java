package com.example.kk.arttraining.ui.course.bean;

/**
 * 作者：wschenyongyin on 2016/12/15 14:12
 * 说明:课堂
 */
public class LessonBean {
    String lesson_id;
    private String name;
    private String chapter_name;
    private String course_name;
    private String is_free;

    public LessonBean() {
    }

    public String getChapter_name() {
        return chapter_name;
    }

    public void setChapter_name(String chapter_name) {
        this.chapter_name = chapter_name;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getIs_free() {
        return is_free;
    }

    public void setIs_free(String is_free) {
        this.is_free = is_free;
    }

    public String getLesson_id() {
        return lesson_id;
    }

    public void setLesson_id(String lesson_id) {
        this.lesson_id = lesson_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "LessonBean{" +
                "chapter_name='" + chapter_name + '\'' +
                ", lesson_id='" + lesson_id + '\'' +
                ", name='" + name + '\'' +
                ", course_name='" + course_name + '\'' +
                ", is_free='" + is_free + '\'' +
                '}';
    }
}
