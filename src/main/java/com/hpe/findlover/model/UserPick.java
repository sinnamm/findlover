package com.hpe.findlover.model;

import javax.persistence.*;

/**
 * @author Gss
 */
@Table(name = "user_pick")
public class UserPick {

    @Transient
    private String workProvince;
    @Transient
    private String workCity;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 根据性取向生成，用户不可修改
     */
    private String sex;

    @Column(name = "age_low")
    private Integer ageLow;

    @Column(name = "age_high")
    private Integer ageHigh;

    private String workplace;

    private String birthplace;

    @Column(name = "marry_status")
    private String marryStatus;

    private String education;

    @Column(name = "salary_low")
    private Double salaryLow;

    @Column(name = "salary_high")
    private Double salaryHigh;

    @Column(name = "height_low")
    private Integer heightLow;

    @Column(name = "height_high")
    private Integer heightHigh;

    private String job;

    private Integer drink;

    private Integer smoke;

    public UserPick() {
    }

    public UserPick(Integer id,String sex) {
        this.id =id;
        this.sex =sex;
    }


    public UserPick(Integer id,String sex, Integer ageLow, Integer ageHigh, String workplace, String birthplace, String marryStatus, String education, Double salaryLow, Double salaryHigh, Integer heightLow, Integer heightHigh, String job, Integer drink, Integer smoke) {
        this.id=id;
        this.sex = sex;
        this.ageLow = ageLow;
        this.ageHigh = ageHigh;
        this.workplace = workplace;
        this.birthplace = birthplace;
        this.marryStatus = marryStatus;
        this.education = education;
        this.salaryLow = salaryLow;
        this.salaryHigh = salaryHigh;
        this.heightLow = heightLow;
        this.heightHigh = heightHigh;
        this.job = job;
        this.drink = drink;
        this.smoke = smoke;
    }

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取根据性取向生成，用户不可修改
     *
     * @return sex - 根据性取向生成，用户不可修改
     */
    public String getSex() {
        return sex;
    }

    /**
     * 设置根据性取向生成，用户不可修改
     *
     * @param sex 根据性取向生成，用户不可修改
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * @return age_low
     */
    public Integer getAgeLow() {
        return ageLow;
    }

    /**
     * @param ageLow
     */
    public void setAgeLow(Integer ageLow) {
        this.ageLow = ageLow;
    }

    /**
     * @return age_high
     */
    public Integer getAgeHigh() {
        return ageHigh;
    }

    /**
     * @param ageHigh
     */
    public void setAgeHigh(Integer ageHigh) {
        this.ageHigh = ageHigh;
    }

    /**
     * @return workplace
     */
    public String getWorkplace() {
        return workplace;
    }

    /**
     * @param workplace
     */
    public void setWorkplace(String workplace) {
        this.workplace = workplace;
    }

    /**
     * @return birthplace
     */
    public String getBirthplace() {
        return birthplace;
    }

    /**
     * @param birthplace
     */
    public void setBirthplace(String birthplace) {
        this.birthplace = birthplace;
    }

    /**
     * @return marry_status
     */
    public String getMarryStatus() {
        return marryStatus;
    }

    /**
     * @param marryStatus
     */
    public void setMarryStatus(String marryStatus) {
        this.marryStatus = marryStatus;
    }

    /**
     * @return education
     */
    public String getEducation() {
        return education;
    }

    /**
     * @param education
     */
    public void setEducation(String education) {
        this.education = education;
    }

    /**
     * @return salary_low
     */
    public Double getSalaryLow() {
        return salaryLow;
    }

    /**
     * @param salaryLow
     */
    public void setSalaryLow(Double salaryLow) {
        this.salaryLow = salaryLow;
    }

    /**
     * @return salary_high
     */
    public Double getSalaryHigh() {
        return salaryHigh;
    }

    /**
     * @param salaryHigh
     */
    public void setSalaryHigh(Double salaryHigh) {
        this.salaryHigh = salaryHigh;
    }

    /**
     * @return height_low
     */
    public Integer getHeightLow() {
        return heightLow;
    }

    /**
     * @param heightLow
     */
    public void setHeightLow(Integer heightLow) {
        this.heightLow = heightLow;
    }

    /**
     * @return height_high
     */
    public Integer getHeightHigh() {
        return heightHigh;
    }

    /**
     * @param heightHigh
     */
    public void setHeightHigh(Integer heightHigh) {
        this.heightHigh = heightHigh;
    }

    /**
     * @return job
     */
    public String getJob() {
        return job;
    }

    /**
     * @param job
     */
    public void setJob(String job) {
        this.job = job;
    }

    /**
     * @return drink
     */
    public Integer getDrink() {
        return drink;
    }

    /**
     * @param drink
     */
    public void setDrink(Integer drink) {
        this.drink = drink;
    }

    /**
     * @return smoke
     */
    public Integer getSmoke() {
        return smoke;
    }

    /**
     * @param smoke
     */
    public void setSmoke(Integer smoke) {
        this.smoke = smoke;
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

    @Override
    public String toString() {
        return "UserPick{" +
                "id=" + id +
                ", sex='" + sex + '\'' +
                ", ageLow=" + ageLow +
                ", ageHigh=" + ageHigh +
                ", workplace='" + workplace + '\'' +
                ", workProvince='" + workProvince + '\'' +
                ", workCity='" + workCity + '\'' +
                ", birthplace='" + birthplace + '\'' +
                ", marryStatus='" + marryStatus + '\'' +
                ", education='" + education + '\'' +
                ", salaryLow=" + salaryLow +
                ", salaryHigh=" + salaryHigh +
                ", heightLow=" + heightLow +
                ", heightHigh=" + heightHigh +
                ", job='" + job + '\'' +
                ", drink=" + drink +
                ", smoke=" + smoke +
                '}';
    }
}