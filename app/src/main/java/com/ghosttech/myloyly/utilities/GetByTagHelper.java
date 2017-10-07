package com.ghosttech.myloyly.utilities;

/**
 * Created by Asus on 8/22/2017.
 */

public class GetByTagHelper {
    String strGetByTagTitle, strGetByTagTime, strGetByTagTAG, getByTagImageID, strInstructions, strIngredients, strSteps;
    int ItemID;

    public String getStrIngredients() {
        return strIngredients;
    }

    public void setItemID(int itemID) {
        ItemID = itemID;
    }

    public int getItemID() {
        return ItemID;
    }

    public String getStrSteps() {
        return strSteps;
    }

    public void setStrSteps(String strSteps) {
        this.strSteps = strSteps;
    }

    public String getStrInstructions() {
        return strInstructions;
    }

    public void setStrIngredients(String strIngredients) {
        this.strIngredients = strIngredients;
    }

    public void setStrInstructions(String strInstructions) {
        this.strInstructions = strInstructions;
    }

    public String getGetByTagImageID() {
        return getByTagImageID;
    }

    public void setGetByTagImageID(String getByTagImageID) {
        this.getByTagImageID = getByTagImageID;
    }

    public String getStrGetByTagTAG() {
        return strGetByTagTAG;
    }

    public String getStrGetByTagTime() {
        return strGetByTagTime;
    }

    public String getStrGetByTagTitle() {
        return strGetByTagTitle;
    }


    public void setStrGetByTagTAG(String strGetByTagTAG) {
        this.strGetByTagTAG = strGetByTagTAG;
    }

    public void setStrGetByTagTime(String strGetByTagTime) {
        this.strGetByTagTime = strGetByTagTime;
    }

    public void setStrGetByTagTitle(String strGetByTagTitle) {
        this.strGetByTagTitle = strGetByTagTitle;
    }
}
