package shelter.animal.models;

public class Address {
    private String street;
    private String number;
    private String city;


    public Address(String street, String number, String city) {
        this.street = street;
        this.number = number;
        this.city = city;
    }

    @Override
    public String toString() {
        return String.format("%s, %s, %s", street, number, city);
    }
}
