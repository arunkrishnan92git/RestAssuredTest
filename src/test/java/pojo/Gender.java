package pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Gender{
    @JsonProperty("count")
    int count;
    public int getCount() {
        return this.count; }
    public void setCount(int count) {
        this.count = count; }

    @JsonProperty("name")
    String name;
    public String getName() {
        return this.name; }
    public void setName(String name) {
        this.name = name; }

    @JsonProperty("gender")
    String gender;
    public String getGender() {
        return this.gender; }
    public void setGender(String gender) {
        this.gender = gender; }

    @JsonProperty("probability")
    double probability;
    public double getProbability() {
        return this.probability; }
    public void setProbability(double probability) {
        this.probability = probability; }}
