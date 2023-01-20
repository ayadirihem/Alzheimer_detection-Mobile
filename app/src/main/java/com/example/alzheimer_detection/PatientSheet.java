package com.example.alzheimer_detection;

public class PatientSheet {
    String FullName;
    int Age;
    float EDUC;
    float SES;
    float MMSE;
    float CDR;
    float eTIV;
    float nWBV;
    float ASF;
    String gender;
    String group;

    public PatientSheet(){

    }
    public PatientSheet(String FullName, int Age, float EDUC, float SES, float MMSE, float CDR, float eTIV, float nWBV, float ASF, String gender, String group){
        this.FullName = FullName;
        this.Age = Age;
        this.EDUC = EDUC;
        this.SES = SES;
        this.MMSE = MMSE;
        this.CDR = CDR;
        this.eTIV = eTIV;
        this.nWBV = nWBV;
        this.ASF = ASF;
        this.gender = gender;
        this.group = group;
    }

    public float getASF() {
        return ASF;
    }

    public float getCDR() {
        return CDR;
    }

    public String getFullName() {
        return FullName;
    }

    public float getEDUC() {
        return EDUC;
    }

    public float geteTIV() {
        return eTIV;
    }

    public float getMMSE() {
        return MMSE;
    }

    public float getnWBV() {
        return nWBV;
    }

    public float getSES() {
        return SES;
    }

    public int getAge() {
        return Age;
    }

    public String getGender() {
        return gender;
    }

    public String getGroup() {
        return group;
    }

    public void setAge(int age) {
        Age = age;
    }

    public void setASF(float ASF) {
        this.ASF = ASF;
    }

    public void setCDR(float CDR) {
        this.CDR = CDR;
    }

    public void setEDUC(float EDUC) {
        this.EDUC = EDUC;
    }

    public void seteTIV(float eTIV) {
        this.eTIV = eTIV;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void setMMSE(float MMSE) {
        this.MMSE = MMSE;
    }

    public void setnWBV(float nWBV) {
        this.nWBV = nWBV;
    }

    public void setSES(float SES) {
        this.SES = SES;
    }

    @Override
    public String toString() {
        return "PatientSheet{" +
                "FullName='" + FullName + '\'' +
                ", Age=" + Age +
                ", EDUC=" + EDUC +
                ", SES=" + SES +
                ", MMSE=" + MMSE +
                ", CDR=" + CDR +
                ", eTIV=" + eTIV +
                ", nWBV=" + nWBV +
                ", ASF=" + ASF +
                ", gender='" + gender + '\'' +
                ", group='" + group + '\'' +
                '}';
    }
}
