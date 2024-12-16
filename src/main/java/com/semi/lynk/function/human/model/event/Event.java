package com.semi.lynk.function.human.model.event;

public class Event {
    private String id; // 유일 식별자 (UUID 또는 DB 식별자)
    private String title; // 이벤트 제목
    private String start; // 시작 날짜 (ISO 8601 형식)
    private String end;   // 종료 날짜 (ISO 8601 형식)

    // 기본 생성자 및 Getters/Setters
    public Event() {}

    public Event(String id, String title, String start, String end) {
        this.id = id;
        this.title = title;
        this.start = start;
        this.end = end;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getStart() { return start; }
    public void setStart(String start) { this.start = start; }
    public String getEnd() { return end; }
    public void setEnd(String end) { this.end = end; }
}
