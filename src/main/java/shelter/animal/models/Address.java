package shelter.animal.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Address {
    private String street;
    private String number;
    private String city;

    @Override
    public String toString() {
        return String.format("%s, %s, %s", street, number, city);
    }
}
