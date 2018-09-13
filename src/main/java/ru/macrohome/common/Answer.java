package ru.macrohome.common;

import java.util.List;

public class Answer {
    public Answers answ;
    public String description;
    public List<Entities> list;

    public Answer(Answers answ, String description, List<Entities> list) {
        this.answ = answ;
        this.description = description;
        this.list = list;
    }

    public Answer(Answers answ, String description) {
        this.answ = answ;
        this.description = description;
    }

    public Answer(Answers answ) {
        this.answ = answ;
    }

    public Answer(Answers answ, List<Entities> list) {
        this.answ = answ;
        this.list = list;
    }
}
