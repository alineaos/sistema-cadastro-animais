package shelter.animal.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import shelter.animal.menu.MainMenuLegacy;
import shelter.animal.models.Address;
import shelter.animal.models.Pet;
import shelter.animal.models.enums.PetSex;
import shelter.animal.models.enums.PetType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class PetRepositoryLegacy {
    private final ValidateRepositoryLegacy validateRepositoryLegacy;
    private final FileRepositoryLegacy fileRepositoryLegacy;

    public void createPet(String name, String type, char sex, String breed, String street, String number, String city, Double age, Double weight) {
        Address address = new Address(street, number, city);
        PetType petType = PetType.selectByType(type);
        PetSex petSex = PetSex.selectSex(sex);
        Pet petCreated = new Pet(name, petType, petSex, address, age, weight, breed);
        fileRepositoryLegacy.savePet(petCreated);
    }

    public void listPet() {
        List<String> allPetsList = fileRepositoryLegacy.petsFileReader();
        int i = 1;
        for (String pet : allPetsList) {
            System.out.printf("%d. %s\n", i, pet);
            i++;
        }
    }

    public Map<Integer, Pet> listPetWithFilter() {
        Map<String, String> parameters = MainMenuLegacy.searchPetWithFilterMenu();
        Map<Integer, Pet> filteredList = new HashMap<>();
        List<Pet> allPets = fileRepositoryLegacy.fileToPet();
        int i = 0;
        for (Pet pet : allPets) {
            boolean matchesAll = true;

            for (Map.Entry<String, String> entry : parameters.entrySet()) {
                String criteriaKey = entry.getKey();
                String criteriaValue = entry.getValue();

                if (!validateRepositoryLegacy.petMatchesFilters(pet, criteriaKey, criteriaValue)) {
                    matchesAll = false;
                    break;
                }
            }

            if (matchesAll) {
                i++;
                filteredList.put(i, pet);
            }
        }
        if (!filteredList.isEmpty()) {
            System.out.println("Foram encontrados os seguintes pets com os critérios selecionados:");
            for (Map.Entry<Integer, Pet> petEntry : filteredList.entrySet()) {
                System.out.println(petEntry.getKey() + "- " + petEntry.getValue().petFilteredString());
            }
        } else {
            System.out.println("Nenhum pet encontrado com os critérios selecionados.");
        }

        return filteredList;
    }

    public void updatePet(int option, Pet petToUpdate, String newData) {
        boolean hasUpdatedName = false;
        String oldPetName = petToUpdate.getName();
        Pet updatedPet = validateRepositoryLegacy.dataToUpdate(petToUpdate, option, newData);
        if (!updatedPet.getName().equalsIgnoreCase(oldPetName)) hasUpdatedName = true;
        fileRepositoryLegacy.updatePet(updatedPet, hasUpdatedName, oldPetName);
    }

    public void deletePet(Pet petToDelete, boolean isConfirmed) {
        if (isConfirmed) {
            fileRepositoryLegacy.deletePet(petToDelete);
        } else {
            System.out.println("Ação cancelada pelo usuário.");
        }
    }

}
