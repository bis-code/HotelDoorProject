package com.project.HotelDoor.data;

public class Stage {
    private String stage;
    private String previousStage;

    public Stage()
    {
        stage = null;
        previousStage = null;
    }

    public String getStage() {
        return stage;
    }

    public String getPreviousStage()
    {
        return previousStage;
    }

    public void setPreviousStage(String previousStage)
    {
        this.previousStage = previousStage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }
}
