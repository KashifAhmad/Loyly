package com.ghosttech.myloyly.utilities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Asus on 7/16/2017.
 */

public class EssentialOilHelper implements Parcelable {
    int intImageID;
    String strTitle, strDescription, strLanguages, strBotanicalName,
            strPlantFamily, strOrigin, strPartOfPlant, strNote, strStrength,
            strFragrance, strEffect, strHarmones, strContraindications, strExtraction,
            strBlends, strProperties, strChakra, strColor, strElement, strDidYouKnow;


    public EssentialOilHelper() {

    }

    protected EssentialOilHelper(Parcel in) {
        strTitle = in.readString();
        strDescription = in.readString();
        intImageID = in.readInt();
        strBotanicalName = in.readString();
        strContraindications = in.readString();
        strEffect = in.readString();
        strLanguages = in.readString();
        strFragrance = in.readString();
        strHarmones = in.readString();
        strNote = in.readString();
        strOrigin = in.readString();
        strPartOfPlant = in.readString();
        strExtraction = in.readString();
        strProperties = in.readString();
        strChakra = in.readString();
        strContraindications = in.readString();
        strColor = in.readString();
        strElement = in.readString();
        strDidYouKnow = in.readString();
        strContraindications = in.readString();
    }

    public static final Creator<EssentialOilHelper> CREATOR = new Creator<EssentialOilHelper>() {
        @Override
        public EssentialOilHelper createFromParcel(Parcel in) {
            return new EssentialOilHelper(in);
        }

        @Override
        public EssentialOilHelper[] newArray(int size) {
            return new EssentialOilHelper[size];
        }
    };


    public void setIntImageID(int intImageID) {
        this.intImageID = intImageID;
    }

    public int getIntImageID() {
        return intImageID;
    }

    public void setStrDescription(String strDescription) {
        this.strDescription = strDescription;
    }

    public void setStrProperties(String strProperties) {
        this.strProperties = strProperties;
    }

    public String getStrProperties() {
        return strProperties;
    }

    public void setStrTitle(String strTitle) {
        this.strTitle = strTitle;
    }

    public String getStrDescription() {
        return strDescription;
    }

    public String getStrBotanicalName() {
        return strBotanicalName;
    }

    public void setStrChakra(String strChakra) {
        this.strChakra = strChakra;
    }

    public void setStrColor(String strColor) {
        this.strColor = strColor;
    }
    public void setStrDidYouKnow(String strDidYouKnow) {
        this.strDidYouKnow = strDidYouKnow;
    }

    public void setStrElement(String strElement) {
        this.strElement = strElement;
    }

    public String getStrChakra() {
        return strChakra;
    }

    public String getStrColor() {
        return strColor;
    }

    public String getStrDidYouKnow() {
        return strDidYouKnow;
    }

    public String getStrElement() {
        return strElement;
    }

    public void setStrContraindications(String strContraindications) {
        this.strContraindications = strContraindications;
    }

    public void setStrEffect(String strEffect) {
        this.strEffect = strEffect;
    }

    public void setStrFragrance(String strFragrance) {
        this.strFragrance = strFragrance;
    }

    public void setStrBotanicalName(String strBotanicalName) {
        this.strBotanicalName = strBotanicalName;
    }

    public void setStrHarmones(String strHarmones) {
        this.strHarmones = strHarmones;
    }

    public void setStrLanguages(String strLanguages) {
        this.strLanguages = strLanguages;
    }

    public void setStrNote(String strNote) {
        this.strNote = strNote;
    }

    public void setStrOrigin(String strOrigin) {
        this.strOrigin = strOrigin;
    }

    public void setStrPartOfPlant(String strPartOfPlant) {
        this.strPartOfPlant = strPartOfPlant;
    }

    public void setStrPlantFamily(String strPlantFamily) {
        this.strPlantFamily = strPlantFamily;
    }

    public void setStrStrength(String strStrength) {
        this.strStrength = strStrength;
    }

    public String getStrContraindications() {
        return strContraindications;
    }

    public String getStrEffect() {
        return strEffect;
    }

    public String getStrFragrance() {
        return strFragrance;
    }

    public String getStrHarmones() {
        return strHarmones;
    }

    public String getStrLanguages() {
        return strLanguages;
    }

    public String getStrNote() {
        return strNote;
    }

    public String getStrOrigin() {
        return strOrigin;
    }

    public String getStrPartOfPlant() {
        return strPartOfPlant;
    }

    public String getStrPlantFamily() {
        return strPlantFamily;
    }

    public String getStrStrength() {
        return strStrength;
    }

    public void setStrBlends(String strBlends) {
        this.strBlends = strBlends;
    }

    public void setStrExtraction(String strExtraction) {
        this.strExtraction = strExtraction;
    }

    public String getStrBlends() {
        return strBlends;
    }

    public String getStrExtraction() {
        return strExtraction;
    }

    public String getStrTitle() {
        return strTitle;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(strTitle);
        parcel.writeString(strDescription);
        parcel.writeInt(intImageID);
        parcel.writeString(strBotanicalName);
        parcel.writeString(strContraindications);
        parcel.writeString(strEffect);
        parcel.writeString(strFragrance);
        parcel.writeString(strHarmones);
        parcel.writeString(strLanguages);
        parcel.writeString(strNote);
        parcel.writeString(strPartOfPlant);
        parcel.writeString(strStrength);
        parcel.writeString(strBlends);
        parcel.writeString(strExtraction);
        parcel.writeString(strProperties);
        parcel.writeString(strChakra);
        parcel.writeString(strColor);
        parcel.writeString(strElement);
        parcel.writeString(strDidYouKnow);


    }
}
