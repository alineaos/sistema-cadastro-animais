package shelter.animal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import shelter.animal.service.PetJpaService;

@Controller
@RequiredArgsConstructor
public class PetJpaController {
    private final PetJpaService petService;
}
