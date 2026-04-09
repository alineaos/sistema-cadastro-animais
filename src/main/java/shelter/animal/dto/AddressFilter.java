package shelter.animal.dto;

import lombok.Builder;

@Builder
public record AddressFilter(
        String street,
        String number,
        String city
) {}
