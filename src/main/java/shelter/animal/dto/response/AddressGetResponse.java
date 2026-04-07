package shelter.animal.dto.response;

public record AddressGetResponse(
        String street,
        String number,
        String city
) {
    @Override
    public String toString() {
        return String.format("%s, %s, %s", street, number, city);
    }
}
