package shelter.animal.dto.request;

import lombok.Builder;

@Builder
public record AddressPatchRequest (
        String street,
        String number,
        String city
){
}
