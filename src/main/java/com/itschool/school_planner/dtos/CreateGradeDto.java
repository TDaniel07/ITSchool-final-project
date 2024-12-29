package com.itschool.school_planner.dtos;

public class CreateGradeDto {
    int value;

    public CreateGradeDto(){

    }

    public CreateGradeDto(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
