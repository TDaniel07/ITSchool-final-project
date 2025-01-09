package com.itschool.school_planner.dtos;

public class CreateSubjectDto {
    private String name;

    public CreateSubjectDto(){

    }

    public CreateSubjectDto(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
