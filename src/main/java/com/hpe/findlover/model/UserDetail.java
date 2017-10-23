package com.hpe.findlover.model;

import javax.persistence.*;

@Table(name = "user_detail")
public class UserDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String realname;

    private String cardnumber;

    private String birthplace;

    private Double weight;

    private String animal;

    private String zodiac;

    private String nation;

    private String religion;

    private String graduation;

    private String hobby;

    private String signature;

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
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
     * @return realname
     */
    public String getRealname() {
        return realname;
    }

    /**
     * @param realname
     */
    public void setRealname(String realname) {
        this.realname = realname;
    }

    /**
     * @return cardnumber
     */
    public String getCardnumber() {
        return cardnumber;
    }

    /**
     * @param cardnumber
     */
    public void setCardnumber(String cardnumber) {
        this.cardnumber = cardnumber;
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
     * @return weight
     */
    public Double getWeight() {
        return weight;
    }

    /**
     * @param weight
     */
    public void setWeight(Double weight) {
        this.weight = weight;
    }

    /**
     * @return animal
     */
    public String getAnimal() {
        return animal;
    }

    /**
     * @param animal
     */
    public void setAnimal(String animal) {
        this.animal = animal;
    }

    /**
     * @return zodiac
     */
    public String getZodiac() {
        return zodiac;
    }

    /**
     * @param zodiac
     */
    public void setZodiac(String zodiac) {
        this.zodiac = zodiac;
    }

    /**
     * @return nation
     */
    public String getNation() {
        return nation;
    }

    /**
     * @param nation
     */
    public void setNation(String nation) {
        this.nation = nation;
    }

    /**
     * @return religion
     */
    public String getReligion() {
        return religion;
    }

    /**
     * @param religion
     */
    public void setReligion(String religion) {
        this.religion = religion;
    }

    /**
     * @return graduation
     */
    public String getGraduation() {
        return graduation;
    }

    /**
     * @param graduation
     */
    public void setGraduation(String graduation) {
        this.graduation = graduation;
    }

    /**
     * @return hobby
     */
    public String getHobby() {
        return hobby;
    }

    /**
     * @param hobby
     */
    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    @Override
    public String toString() {
        return "UserDetail{" +
                "id=" + id +
                ", realname='" + realname + '\'' +
                ", cardnumber='" + cardnumber + '\'' +
                ", birthplace='" + birthplace + '\'' +
                ", weight=" + weight +
                ", animal='" + animal + '\'' +
                ", zodiac='" + zodiac + '\'' +
                ", nation='" + nation + '\'' +
                ", religion='" + religion + '\'' +
                ", graduation='" + graduation + '\'' +
                ", hobby='" + hobby + '\'' +
                ", signature='" + signature + '\'' +
                '}';
    }
}