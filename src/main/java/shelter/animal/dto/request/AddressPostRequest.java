package shelter.animal.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record AddressPostRequest(
        @NotBlank(message = "O campo rua não pode estar em branco")
        String street,
        @NotNull(message = "O campo número não pode estar vazio.")
        String number,
        @NotBlank(message = "O campo cidade não pode estar em branco")
        String city
) {}
