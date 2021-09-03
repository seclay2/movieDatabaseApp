package io.github.seclay2.api.entity;

public class Platform {

    private Integer id;
    private String name;
    private float monthlyCost;

    public Platform(Integer id, String name, float monthlyCost) {
        this.id = id;
        this.name = name;
        this.monthlyCost = monthlyCost;
    }

    public Platform(String name, float monthlyCost) {
        this.name = name;
        this.monthlyCost = monthlyCost;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getMonthlyCost() {
        return monthlyCost;
    }

    public void setMonthlyCost(float monthlyCost) {
        this.monthlyCost = monthlyCost;
    }

    @Override
    public String toString() {
        return "Platform{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", monthlyCost=" + monthlyCost +
                '}';
    }
}
