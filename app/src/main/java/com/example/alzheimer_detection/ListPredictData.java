package com.example.alzheimer_detection;

public class ListPredictData {
    private String id;
    private String FullName;
    private String Age;
    private String Group;
    float EDUC;
    float SES;
    float MMSE;
    float CDR;
    float eTIV;
    float nWBV;
    float ASF;
    String gender;

    public ListPredictData(String id, String fullName, String age, String group, float EDUC, float SES, float MMSE, float CDR, float eTIV, float nWBV, float ASF, String gender) {
        this.id = id;
        FullName = fullName;
        Age = age;
        Group = group;
        this.EDUC = EDUC;
        this.SES = SES;
        this.MMSE = MMSE;
        this.CDR = CDR;
        this.eTIV = eTIV;
        this.nWBV = nWBV;
        this.ASF = ASF;
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getEDUC() {
        return EDUC;
    }

    public void setEDUC(float EDUC) {
        this.EDUC = EDUC;
    }

    public float getSES() {
        return SES;
    }

    public void setSES(float SES) {
        this.SES = SES;
    }

    public float getMMSE() {
        return MMSE;
    }

    public void setMMSE(float MMSE) {
        this.MMSE = MMSE;
    }

    public float getCDR() {
        return CDR;
    }

    public void setCDR(float CDR) {
        this.CDR = CDR;
    }

    public float geteTIV() {
        return eTIV;
    }

    public void seteTIV(float eTIV) {
        this.eTIV = eTIV;
    }

    public float getnWBV() {
        return nWBV;
    }

    public void setnWBV(float nWBV) {
        this.nWBV = nWBV;
    }

    public float getASF() {
        return ASF;
    }

    public void setASF(float ASF) {
        this.ASF = ASF;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getGroup() {
        return Group;
    }

    public void setGroup(String group) {
        Group = group;
    }

    @Override
    public String toString() {
        return "ListPredictData{" +
                "FullName='" + FullName + '\'' +
                ", Age='" + Age + '\'' +
                ", Group='" + Group + '\'' +
                '}';
    }
}
