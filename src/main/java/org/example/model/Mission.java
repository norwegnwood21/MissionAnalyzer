package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class Mission {
    private String missionId;
    private String date;
    private String location;
    private String outcome;     // SUCCESS, FAILURE
    private int damageCost;      // стоимость ущерба

    private Curse curse;

    private List<Sorcerer> sorcerers = new ArrayList<>();
    private List<Technique> techniques = new ArrayList<>();

    // Дополнительные поля
    private String comment;
    private String note;

    public Mission() {
    }


    public String getMissionId() {
        return missionId;
    }

    public void setMissionId(String missionId) {
        this.missionId = missionId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public int getDamageCost() {
        return damageCost;
    }

    public void setDamageCost(int damageCost) {
        this.damageCost = damageCost;
    }

    public Curse getCurse() {
        return curse;
    }

    public void setCurse(Curse curse) {
        this.curse = curse;
    }

    public List<Sorcerer> getSorcerers() {
        return sorcerers;
    }

    public void setSorcerers(List<Sorcerer> sorcerers) {
        this.sorcerers = sorcerers;
    }

    public List<Technique> getTechniques() {
        return techniques;
    }

    public void setTechniques(List<Technique> techniques) {
        this.techniques = techniques;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }


    public void printMissionInfo() {
        System.out.println("МИССИЯ: " + missionId);
        System.out.println("Дата: " + date);
        System.out.println("Место: " + location);
        System.out.println("Результат: " + outcome);
        System.out.println("Ущерб: " + damageCost + " йен");

        System.out.println("\n Проклятие");
        if (curse != null) {
            System.out.println(curse);
        } else {
            System.out.println("Нет информации о проклятии");
        }

        System.out.println("\n Маги");
        if (sorcerers.isEmpty()) {
            System.out.println("Нет информации об участниках");
        } else {
            for (int i = 0; i < sorcerers.size(); i++) {
                System.out.println((i + 1) + ". " + sorcerers.get(i));
            }
        }

        System.out.println("\n Техника");
        if (techniques.isEmpty()) {
            System.out.println("Нет информации о техниках");
        } else {
            for (int i = 0; i < techniques.size(); i++) {
                System.out.println((i + 1) + ". " + techniques.get(i));
            }
        }

        // под комментарии
        if (comment != null && !comment.isEmpty()) {
            System.out.println("\n Комментарий");
            System.out.println(comment);
        }

        if (note != null && !note.isEmpty()) {
            System.out.println("\n Примечание ");
            System.out.println(note);
        }


    }
}