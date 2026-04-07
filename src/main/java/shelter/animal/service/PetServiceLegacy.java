package shelter.animal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shelter.animal.exceptions.PetValidateException;
import shelter.animal.models.Pet;
import shelter.animal.repository.FileRepositoryLegacy;
import shelter.animal.repository.PetRepositoryLegacy;
import shelter.animal.repository.ValidateRepositoryLegacy;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

@Service
@RequiredArgsConstructor
public class PetServiceLegacy {
    private final PetRepositoryLegacy repository;
    private final ValidateRepositoryLegacy validateRepositoryLegacy;
    private final FileRepositoryLegacy fileRepositoryLegacy;
    private static final Scanner SCANNER = new Scanner(System.in);

    public void createPet() {
        List<String> questions = fileRepositoryLegacy.readForm();
        String name = null, type = null, sex = null, breed = null;
        String street = null, number = null, city = null;
        Double age = null, weight = null;

        System.out.println("Para cadastrar um pet, responda as seguintes perguntas:");
        for (int i = 1; i <= (questions.size()); i++) {
            System.out.println(questions.get(i - 1));
            boolean isValid = false;
            switch (i) {
                case 1:
                    do {
                        try {
                            name = validateRepositoryLegacy.validateName(SCANNER.nextLine());
                            isValid = true;
                        } catch (PetValidateException e) {
                            System.out.println("Erro: " + e.getMessage());
                            System.out.println("Digite o nome do pet novamente.");
                        }
                    } while (!isValid);
                    break;

                case 2:
                    do {
                        type = SCANNER.nextLine();
                        isValid = validateRepositoryLegacy.validateType(type);
                        if (!isValid) typeAgain();
                    } while (!isValid);
                    break;

                case 3:
                    do {
                        sex = SCANNER.nextLine();
                        isValid = validateRepositoryLegacy.validateSex(sex);
                        if (!isValid) typeAgain();
                    } while (!isValid);
                    break;

                case 4:
                    do {
                        System.out.print("Digite a rua: ");
                        street = SCANNER.nextLine();
                        isValid = validateRepositoryLegacy.validateStreet(street);
                        if (!isValid) typeAgain();
                    } while (!isValid);

                    System.out.print("Digite o número: ");
                    number = SCANNER.nextLine();
                    number = validateRepositoryLegacy.defaultIfEmpty(number);

                    do {
                        System.out.print("Digite a cidade: ");
                        city = SCANNER.nextLine();
                        isValid = validateRepositoryLegacy.validateCity(city);
                        if (!isValid) typeAgain();
                    } while (!isValid);
                    break;

                case 5:
                    do {
                        try {
                            String auxAge = SCANNER.nextLine();
                            if (auxAge.isBlank()) break;
                            age = validateRepositoryLegacy.validateAge(auxAge);
                            isValid = true;
                        } catch (PetValidateException | NumberFormatException e) {
                            System.out.println("Erro: " + e.getMessage());
                            System.out.println("Digite a idade novamente.");
                        }
                    } while (!isValid);

                    if (age != null) {
                        String date;
                        System.out.println("A idade é em meses ou anos?");
                        do {
                            date = SCANNER.nextLine();
                            if (date.isEmpty()) {
                                age = null;
                                break;
                            }
                            isValid = validateRepositoryLegacy.validateDate(date);
                            if (!isValid) typeAgain();
                            if (date.equalsIgnoreCase("meses")) age /= 12;
                        } while (!isValid);
                    }
                    break;

                case 6:
                    do {
                        try {
                            String auxWeight = SCANNER.nextLine();
                            if (auxWeight.isBlank()) break;
                            weight = validateRepositoryLegacy.validateWeight(auxWeight);
                            isValid = true;
                        } catch (PetValidateException | NumberFormatException e) {
                            System.out.println("Erro: " + e.getMessage());
                            System.out.println("Digite o peso novamente.");
                        }
                    } while (!isValid);
                    break;

                case 7:
                    do {
                        try {
                            breed = validateRepositoryLegacy.validateBreed(SCANNER.nextLine());
                            isValid = true;
                        } catch (PetValidateException e) {
                            System.out.println("Erro: " + e.getMessage());
                            System.out.println("Digite o nome do pet novamente.");
                        }
                    } while (!isValid);
            }
        }
        assert sex != null;
        repository.createPet(name, type, sex.charAt(0), breed, street, number, city, age, weight);
    }

    private void typeAgain() {
        System.out.println("Por favor, digite novamente: ");
    }

    public void listPet() {
        System.out.println("Lista dos pets cadastrados no sistema:");
        System.out.println("--------------------------------------");
        repository.listPet();
    }

    public Map<Integer, Pet> listPetWithFilter() {
        return repository.listPetWithFilter();
    }

    public void updatePet() {
        System.out.println("Buscando por cadastros...");
        Map<Integer, Pet> filteredList = listPetWithFilter();
        if (filteredList.isEmpty()) {
            return;
        }

        System.out.println("De qual pet da lista acima você deseja alterar os dados? Digite o número: ");
        System.out.println("Digite 0 para voltar ao menu inicial");
        int id = SCANNER.nextInt();
        if (id == 0) {
            System.out.println("Retornando para o Menu Inicial...");
            return;
        }

        Pet petToUpdate = null;
        for (Map.Entry<Integer, Pet> petEntry : filteredList.entrySet()) {
            if (petEntry.getKey() == id) {
                petToUpdate = petEntry.getValue();
                break;
            }
        }

        String newData;
        Integer option = updatePetMenu();
        if (option == null || option == 6) return;
        SCANNER.nextLine();
        System.out.println("Digite o novo dado: ");
        newData = SCANNER.nextLine();
        assert petToUpdate != null;
        repository.updatePet(option, petToUpdate, newData);
    }

    private Integer updatePetMenu() {
        int option;
        do {
            System.out.println("Qual dado do pet você deseja alterar?");
            System.out.println("[1] Nome");
            System.out.println("[2] Idade");
            System.out.println("[3] Peso");
            System.out.println("[4] Raça");
            System.out.println("[5] Endereço");
            System.out.println("[6] Voltar para o menu inicial");
            option = SCANNER.nextInt();
            if (option == 6) return null;
        } while (option < 0 || option > 6);
        return option;
    }

    public void deletePet() {
        System.out.println("Buscando por cadastros...");
        Map<Integer, Pet> filteredList = listPetWithFilter();
        Pet petToDelete = null;
        if (filteredList.isEmpty()) {
            return;
        }
        System.out.println("Qual pet da lista você deseja deletar? Digite o número: ");
        System.out.println("Digite 0 para voltar ao menu inicial");
        int id = SCANNER.nextInt();
        if (id == 0) {
            System.out.println("Retornando para o Menu Inicial...");
            return;
        }
        for (Map.Entry<Integer, Pet> petEntry : filteredList.entrySet()) {
            if (petEntry.getKey() == id) {
                petToDelete = petEntry.getValue();
                break;
            }
        }
        String confirmation;
        do {
            System.out.println("Pet selecionado:");
            System.out.println(petToDelete);
            System.out.println("Você tem certeza que deseja excluir o cadastro do pet?");
            System.out.println("Digite [S] para Sim e [N] para Não");
            confirmation = SCANNER.next();
        } while (!confirmation.equalsIgnoreCase("S") && !confirmation.equalsIgnoreCase("N"));
        boolean isConfirmed = confirmation.equalsIgnoreCase("S");

        repository.deletePet(petToDelete, isConfirmed);
    }
}
