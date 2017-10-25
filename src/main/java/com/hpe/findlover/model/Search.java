package com.hpe.findlover.model;

/**
 * @author sinnamm
 * @Date Create in  2017/10/19.
 * @Description 用户存放搜索条件的类
 */

public class Search {
    private Integer id;
    private String sex;
    private Integer ageLow;
    private Integer ageHigh;
    private String workplace;
    private String workProvince;
    private String workCity;
    private Integer heightLow;
    private Integer heightHigh;
    private String job;
    private String marryStatus;
    private Double salaryLow;
    private Double salaryHigh;

    /*以下为VIP的搜索条件*/

    private String education;
    private Integer liveCondition;
    private String birthplace;
    private String birthProvince;
    private String birthCity;
    private String zodiac;
    private String animal;
    private String nation;
    private String religion;

    public Search() {
    }

    /**
     * 非VIP用户搜索条件封装
     */
    public Search(String sex, Integer ageLow, Integer ageHigh, String workplace, String workProvince, String workCity, Integer heightLow, Integer heightHigh, String job,String marryStatus, Double salaryLow, Double salaryHigh) {
        this.sex = sex;
        this.ageLow = ageLow;
        this.ageHigh = ageHigh;
        this.workplace = workplace;
        this.workProvince = workProvince;
        this.workCity = workCity;
        this.heightLow = heightLow;
        this.heightHigh = heightHigh;
        this.job = job;
        this.marryStatus = marryStatus;
        this.salaryLow = salaryLow;
        this.salaryHigh = salaryHigh;
    }

    /**
     * VIP用户搜索条件封装
     */
    public Search(String sex, Integer ageLow, Integer ageHigh, String workplace, String workProvince, String workCity, Integer heightLow, Integer heightHigh, String job, String marryStatus,Double salaryLow, Double salaryHigh, String education, Integer liveCondition, String birthplace, String birthProvince, String birthCity, String zodiac, String animal, String nation, String religion) {
        this.sex = sex;
        this.ageLow = ageLow;
        this.ageHigh = ageHigh;
        this.workplace = workplace;
        this.workProvince = workProvince;
        this.workCity = workCity;
        this.heightLow = heightLow;
        this.heightHigh = heightHigh;
        this.job = job;
        this.marryStatus= marryStatus;
        this.salaryLow = salaryLow;
        this.salaryHigh = salaryHigh;
        this.education = education;
        this.liveCondition = liveCondition;
        this.birthplace = birthplace;
        this.birthProvince = birthProvince;
        this.birthCity = birthCity;
        this.zodiac = zodiac;
        this.animal = animal;
        this.nation = nation;
        this.religion = religion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAgeLow() {
        return ageLow;
    }

    public void setAgeLow(Integer ageLow) {
        this.ageLow = ageLow;
    }

    public Integer getAgeHigh() {
        return ageHigh;
    }

    public void setAgeHigh(Integer ageHigh) {
        this.ageHigh = ageHigh;
    }

    public String getWorkplace() {
        return workplace;
    }

    public void setWorkplace(String workplace) {
        this.workplace = workplace;
    }

    public Integer getHeightLow() {
        return heightLow;
    }

    public void setHeightLow(Integer heightLow) {
        this.heightLow = heightLow;
    }

    public Integer getHeightHigh() {
        return heightHigh;
    }

    public void setHeightHigh(Integer heightHigh) {
        this.heightHigh = heightHigh;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getMarryStatus() {
        return marryStatus;
    }

    public void setMarryStatus(String marryStatus) {
        this.marryStatus = marryStatus;
    }

    public Double getSalaryLow() {
        return salaryLow;
    }

    public void setSalaryLow(Double salaryLow) {
        this.salaryLow = salaryLow;
    }

    public Double getSalaryHigh() {
        return salaryHigh;
    }

    public void setSalaryHigh(Double salaryHigh) {
        this.salaryHigh = salaryHigh;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public Integer getLiveCondition() {
        return liveCondition;
    }

    public void setLiveCondition(Integer liveCondition) {
        this.liveCondition = liveCondition;
    }

    public String getBirthplace() {
        return birthplace;
    }

    public void setBirthplace(String birthplace) {
        this.birthplace = birthplace;
    }

    public String getZodiac() {
        return zodiac;
    }

    public void setZodiac(String zodiac) {
        this.zodiac = zodiac;
    }

    public String getAnimal() {
        return animal;
    }

    public void setAnimal(String animal) {
        this.animal = animal;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getWorkProvince() {
        return workProvince;
    }

    public void setWorkProvince(String workProvince) {
        this.workProvince = workProvince;
    }

    public String getWorkCity() {
        return workCity;
    }

    public void setWorkCity(String workCity) {
        this.workCity = workCity;
    }

    public String getBirthProvince() {
        return birthProvince;
    }

    public void setBirthProvince(String birthProvince) {
        this.birthProvince = birthProvince;
    }

    public String getBirthCity() {
        return birthCity;
    }

    public void setBirthCity(String birthCity) {
        this.birthCity = birthCity;
    }

    @Override
    public String toString() {
        return "Search{" +
                "sex='" + sex + '\'' +
                ", ageLow=" + ageLow +
                ", ageHigh=" + ageHigh +
                ", workplace='" + workplace + '\'' +
                ", workProvince='" + workProvince + '\'' +
                ", workCity='" + workCity + '\'' +
                ", heightLow=" + heightLow +
                ", heightHigh=" + heightHigh +
                ", job='" + job + '\'' +
                ", marryStatus='" + marryStatus + '\'' +
                ", salaryLow=" + salaryLow +
                ", salaryHigh=" + salaryHigh +
                ", education='" + education + '\'' +
                ", liveCondition=" + liveCondition +
                ", birthplace='" + birthplace + '\'' +
                ", birthProvince='" + birthProvince + '\'' +
                ", birthCity='" + birthCity + '\'' +
                ", zodiac='" + zodiac + '\'' +
                ", animal='" + animal + '\'' +
                ", nation='" + nation + '\'' +
                ", religion='" + religion + '\'' +
                '}';
    }
}
