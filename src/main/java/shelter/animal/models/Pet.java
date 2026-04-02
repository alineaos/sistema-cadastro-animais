package shelter.animal.models;

import shelter.animal.models.enums.PetSex;
import shelter.animal.models.enums.PetType;
import shelter.animal.repository.ValidateRepository;

public class Pet {
    private String name;
    private final PetType type;
    private final PetSex sex;
    private Address address;
    private Double age;
    private Double weight;
    private String breed;

    public Pet(String name, PetType type, PetSex sex, Address address, Double age, Double weight, String breed) {
        this.name = name;
        this.type = type;
        this.sex = sex;
        this.address = address;
        this.age = age;
        this.weight = weight;
        this.breed = breed;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setAge(Double age) {
        this.age = age;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getName() {
        return name;
    }

    public PetType getType() {
        return type;
    }

    public PetSex getSex() {
        return sex;
    }

    public Address getAddress() {
        return address;
    }

    public Double getAge() {
        return age;
    }

    public Double getWeight() {
        return weight;
    }

    public String getBreed() {
        return breed;
    }

    @Override
    public String toString() {
        return "1- " +
                this.getName() +
                "\n2- " +
                this.getType().getClassification() +
                "\n3- " +
                this.getSex().getClassification() +
                "\n4- " +
                this.getAddress() +
                "\n5- " +
                (this.getAge() == null ? ValidateRepository.NAO_INFORMADO : String.format("%.2f anos", this.getAge())) +
                "\n6- " +
                (this.getWeight() == null ? ValidateRepository.NAO_INFORMADO : String.format("%.2fkg", this.getWeight())) +
                "\n7- " +
                this.getBreed();
    }

    public String petFilteredString() {
        return this.getName() +
                ", " +
                this.getType().getClassification() +
                ", " +
                this.getSex().getClassification() +
                ", " +
                this.getAddress() +
                ", " +
                (this.getAge() == null ? ValidateRepository.NAO_INFORMADO : String.format("%.2f anos", this.getAge())) +
                ", " +
                (this.getWeight() == null ? ValidateRepository.NAO_INFORMADO : String.format("%.2fkg", this.getWeight())) +
                ", " +
                this.getBreed();
    }


}
