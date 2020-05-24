package com.example.myapplication;

public class DataContent {

    private String email, username, password, name, description, achievemntName, achievementDescription;
    private int userId, gameId, achievementId;

    private static DataContent instance = new DataContent();

    private DataContent(){}

    public static DataContent getInstance(){
        return instance;
    }

    public void setEmail(String email){this.email = email;}
    public void setUsername(String username){this.username = username;}
    public void setPassword(String password){this.password = password;}
    public void setName (String name){this.name = name;}
    public void setGameId(int gameId){this.gameId = gameId;}
    public void setAchievementId(int achievementId){this.achievementId = achievementId;}
    public void setDescription(String description){this.description = description;}
    public void setAchievemntName(String achievemntName){this.achievemntName = achievemntName;}

    public void setAchievementDescription(String achievementDescription) {
        this.achievementDescription = achievementDescription;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmail(){return email;}
    public String getUsername(){return username;}
    public String getPassword(){return password;}
    public String getName(){return name;}

    public int getAchievementId() {
        return achievementId;
    }

    public int getGameId() {
        return gameId;
    }

    public int getUserId() {
        return userId;
    }

    public String getAchievementDescription() {
        return achievementDescription;
    }

    public String getAchievemntName() {
        return achievemntName;
    }

    public String getDescription() {
        return description;
    }
}
