package shelter.animal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shelter.animal.repository.PetJpaRepository;

@RequiredArgsConstructor
@Service
public class PetJpaService {
    private final PetJpaRepository petRepository;
}
