package shelter.animal.menu;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shelter.animal.controller.PetJpaController;
import shelter.animal.dto.response.PetGetResponse;
import shelter.animal.dto.response.PetPostResponse;
import shelter.animal.models.enums.AgeUnit;
import shelter.animal.models.enums.PetSex;
import shelter.animal.models.enums.PetType;
import shelter.animal.utils.AppConstants;
import shelter.animal.utils.InputValidator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class PetMenu {
    private final Scanner scanner;
    private final InputValidator validator;
    private final PetJpaController controller;

    public void runPetMenu() {
        while (true) {
            int option;
            do {
                showPetMenu();
                option = validator.parseInteger(scanner.nextLine());
            } while (option < 0 | option > 6);
            if (option == 0) return;
            processingPetMenuOption(option);
        }
    }

    private void processingPetMenuOption(int option) {
        switch (option) {
            case 1 -> handleSave();
            case 2 -> handleFindAll();
            case 3 -> handleFindById();
            case 4 -> handleFindByCriteria();
            case 5 -> handleDelete();
            case 6 -> handleUpdate();
            default -> throw new IllegalArgumentException("Opção inválida.");
        }
    }

    private void showPetMenu() {
        System.out.println("**************************");
        System.out.println("* GERENCIADOR DE ANIMAIS *");
        System.out.println("**************************");
        System.out.println("Escolha uma opção: ");
        System.out.println("[1] Cadastrar um novo pet");
        System.out.println("[2] Listar todos os pets cadastrados");
        System.out.println("[3] Listar pet por ID");
        System.out.println("[4] Listar por critérios");
        System.out.println("[5] Deletar cadastro");
        System.out.println("[6] Atualizar cadastro");
        System.out.println("[0] Voltar ao menu anterior");
        System.out.print("Opção: ");
    }

//region PET ACTIONS (HANDLES)
// ========================================================================================================
// METHODS TO HANDLE USER ACTIONS
// ========================================================================================================

    private void handleSave() {
        System.out.println("Para cadastrar um pet, preencha a ficha a seguir.");

        System.out.print("Nome do pet: ");
        String name = scanner.nextLine();

        PetType petType = runPetClassificationMenu();
        PetSex petSex = runPetSexMenu();

        System.out.println("\nEm qual endereço e bairro ele foi encontrado?");
        System.out.print("Nome da rua: ");
        String street = parseTextInputNonBlank("O nome da rua não pode estar em branco. Digite novamente: ");

        System.out.print("\nNúmero ou deixe em branco: ");
        String number = parseAddressNumberInput();

        System.out.print("\nCidade: ");
        String city = parseTextInputNonBlank("O nome da cidade não pode estar em branco. Digite novamente: ");

        System.out.print("\nIdade aproximada do pet: ");
        Double age = parseNullableDouble();

        AgeUnit ageUnit = null;
        if (age != null) ageUnit = runAgeUnitMenu();


        System.out.print("\nPeso aproximado do pet: ");
        Double weight = parseNullableDouble();

        System.out.print("\nRaça do pet: ");
        String breed = scanner.nextLine();

        PetPostResponse petPostResponse = controller.save(name, petType, petSex, street, number, city, age, ageUnit, weight, breed);

        System.out.printf("\nO Pet com nome '%s' foi cadastrado com sucesso! Id: %d%n", petPostResponse.name(), petPostResponse.id());
    }

    private void handleFindAll() {
        List<PetGetResponse> petGetResponseList = controller.findAll();
        printPetTable(petGetResponseList);
    }

    private void handleFindById() {
        System.out.print("\nDigite o ID: ");
        Long id = validator.parseLong(scanner.nextLine());

        System.out.printf("Localizando o pet com id %d...\n", id);
        PetGetResponse petGetResponse = controller.findById(id);

        printPetTable(Collections.singletonList(petGetResponse));
    }

    private void handleFindByCriteria() {
        Map<String, Object> filters = new HashMap<>();
        while (true) {
            System.out.println("Selecione o parâmetro (ou 0 para iniciar a busca).");
            showPetFilterMenu();
            int option = validator.parseInteger(scanner.nextLine());

            if (option == 0) break;

            if (isAlreadySelected(option, filters)) {
                System.out.println("Esse filtro já foi aplicado. Escolha outro ou digite 0 para buscar.");
                continue;
            }

            switch (option) {
                case 1 -> {
                    System.out.print("\nDigite o nome a ser buscado: ");
                    filters.put("name", scanner.nextLine());
                }

                case 2 -> filters.put("type", runPetClassificationMenu());

                case 3 -> filters.put("sex", runPetSexMenu());

                case 4 -> {
                    System.out.print("\nDigite a idade: ");
                    filters.put("age", validator.parseDouble(scanner.nextLine()));
                    filters.put("ageUnit", runAgeUnitMenu());
                }

                case 5 -> {
                    System.out.print("\nDigite o peso: ");
                    filters.put("weight", validator.parseDouble(scanner.nextLine()));
                }

                case 6 -> {
                    System.out.print("\nDigite a raça: ");
                    filters.put("breed", scanner.nextLine());
                }

                case 7 -> {
                    System.out.println("Preencha o endereço completo.");
                    System.out.print("Digite a rua: ");
                    filters.put("number", parseTextInputNonBlank("O nome da rua não pode estar em branco. Digite novamente: "));

                    System.out.print("\nDigite o número ou deixe em branco: ");
                    filters.put("number", parseAddressNumberInput());

                    System.out.print("\nDigite a cidade: ");
                    filters.put("city", parseTextInputNonBlank("O nome da rua não pode estar em branco. Digite novamente: "));
                }

                case 8 -> {
                    System.out.println("Digite a data no formato DD/MM/AAAA");
                    filters.put("createdAt", validator.parseDate(scanner.nextLine()));
                }
            }
            if (filters.size() >= 8) {
                System.out.println("Todos os filtros já foram preenchidos.");
            }

        }

        List<PetGetResponse> petGetResponseList = controller.findByCriteria(filters);

        printPetTable(petGetResponseList);
    }

    private void handleDelete(){
        System.out.println("Digite o ID do cadastro a ser deletado. ");
        System.out.print("ID: ");
        Long id = validator.parseLong(scanner.nextLine());

        controller.delete(id);
        System.out.println("Cadastro deletado com sucesso.");
    }

    private void handleUpdate(){
        System.out.println("Digite o ID do cadastro a ser atualizado.");
        System.out.print("ID: ");
        Long id = validator.parseLong(scanner.nextLine());
        PetGetResponse petFromDb = controller.findById(id);
        printPetTable(Collections.singletonList(petFromDb));
        System.out.println("\nLembre-se: Não é possível alterar o id, tipo ou sexo do pet cadastrado.");

        System.out.printf("Nome atual: %s\n", petFromDb.name());
        System.out.print("Digite o novo nome ou enter para manter o atual: ");
        String newName = scanner.nextLine();

        System.out.printf("Rua atual: %s\n", petFromDb.address().street());
        System.out.print("Digite a nova rua ou enter para manter a atual: ");
        String newStreet = scanner.nextLine();

        System.out.printf("Número atual: %s\n", petFromDb.address().number());
        System.out.print("Digite o novo número ou enter para manter o atual: ");
        String newNumber = scanner.nextLine();

        System.out.printf("Cidade atual: %s\n", petFromDb.address().city());
        System.out.print("Digite a nova cidade ou enter para manter a atual: ");
        String newCity = scanner.nextLine();

        System.out.printf("Idade atual: %s %s\n", petFromDb.age(), petFromDb.ageUnit().getLabel());
        System.out.print("Digite a nova idade ou enter para manter a atual: ");
        Double newAge = parseNullableDouble();

        AgeUnit newAgeUnit = null;
        if (newAge != null) newAgeUnit = runAgeUnitMenu();

        System.out.printf("Peso atual: %s\n", petFromDb.weight());
        System.out.print("Digite o novo peso ou enter para manter o atual: ");
        Double newWeight = parseNullableDouble();

        System.out.printf("Raça atual: %s\n", petFromDb.breed());
        System.out.print("Digite a nova raça ou enter para manter a atual: ");
        String newBreed = scanner.nextLine();

        controller.update(id, newName, newStreet, newNumber, newCity, newAge, newAgeUnit, newWeight, newBreed);
    }

//endregion

    //region SUBMENUS (AUX MENUS)
// ========================================================================================================
// METHODS TO HELP WITH I/O
// ========================================================================================================
    private PetType runPetClassificationMenu() {
        System.out.println("\nQual o tipo do animal?");
        showPetClassificationMenu();
        System.out.print("Digite o código: ");
        int option = validator.parseInteger(scanner.nextLine());
        return processingPetClassificationMenu(option);
    }

    private void showPetClassificationMenu() {
        for (PetType type : PetType.values()) {
            System.out.printf("[%d] %s%n", type.getCode(), type.getLabel());
        }
    }

    private PetType processingPetClassificationMenu(int option) {
        return PetType.selectByCode(option);
    }

    private PetSex runPetSexMenu() {
        System.out.println("\nQual o sexo do animal?");
        showPetSexMenu();
        System.out.print("Digite o código: ");
        int option = validator.parseInteger(scanner.nextLine());
        return processingPetSexMenu(option);
    }

    private void showPetSexMenu() {
        System.out.println("[1] Fêmea");
        System.out.println("[2] Macho");
    }

    private PetSex processingPetSexMenu(int option) {
        return PetSex.selectByCode(option);
    }

    private AgeUnit runAgeUnitMenu() {
        System.out.println("\nA idade foi digitada em meses ou em anos?");
        showAgeUnitMenu();
        System.out.print("Digite o código: ");
        int option = validator.parseInteger(scanner.nextLine());
        return processingAgeUnitMenu(option);
    }

    private void showAgeUnitMenu() {
        System.out.println("[1] Anos");
        System.out.println("[2] Meses");
    }

    private AgeUnit processingAgeUnitMenu(int option) {
        return AgeUnit.selectByCode(option);
    }

    private String parseTextInputNonBlank(String invalidMessage) {
        String input = "";
        while (input.isBlank()) {
            input = scanner.nextLine();
            if (input.isBlank()) {
                System.out.println("\n" + invalidMessage);
            }
        }
        return input;
    }

    private String parseAddressNumberInput() {
        final Pattern addressNumberRegex = Pattern.compile("^(\\d+[A-Za-z]?|\\s*)$");
        String input;
        do {
            input = scanner.nextLine();
            if (!addressNumberRegex.matcher(input).matches()) {
                System.out.println("Número inválido. Digite novamente: ");
            }
        } while (!addressNumberRegex.matcher(input).matches());
        return input;
    }

    private Double parseNullableDouble() {
        String numberInput = scanner.nextLine();
        if (numberInput.isEmpty()) {
            return null;
        }
        return validator.parseDouble(numberInput);
    }

    private void showPetFilterMenu() {
        System.out.println("[1] Nome");
        System.out.println("[2] Tipo");
        System.out.println("[3] Sexo");
        System.out.println("[4] Idade");
        System.out.println("[5] Peso");
        System.out.println("[6] Raça");
        System.out.println("[7] Endereço");
        System.out.println("[8] Dia do cadastro");
        System.out.print("Opção: ");
    }

    private boolean isAlreadySelected(int choice, Map<String, Object> filters) {
        return switch (choice) {
            case 1 -> filters.containsKey("name");
            case 2 -> filters.containsKey("type");
            case 3 -> filters.containsKey("sex");
            case 4 -> filters.containsKey("age");
            case 5 -> filters.containsKey("weight");
            case 6 -> filters.containsKey("breed");
            case 7 -> filters.containsKey("city");
            case 8 -> filters.containsKey("createdAt");
            default -> false;
        };
    }
//endregion

    //region AUX PRINT INTERFACE
// ========================================================================================================
// METHODS TO HELP WITH PRINTS (Table formatting)
// ========================================================================================================
    private void printPetTable(List<PetGetResponse> petGetResponseList) {
        if (petGetResponseList.isEmpty()) {
            System.out.println("Lista vazia. Nenhum pet para exibir.");
            return;
        }

        int nameColumLength = getColumnLargestLength(petGetResponseList, PetGetResponse::name, 4);
        int addressColumLength = getColumnLargestLength(petGetResponseList, p -> p.address().toString(), 8);
        int breedColumLength = getColumnLargestLength(petGetResponseList, PetGetResponse::breed, 4);

        String tablePattern = "[%-3s] %-" + nameColumLength + "s | %-8s | %-4s | %-" + addressColumLength
                + "s | %-13s | %-13s | %-" + breedColumLength + "s | %s%n";
        System.out.printf(tablePattern,
                "ID", "Nome", "Tipo", "Sexo", "Endereço", "Idade", "Peso", "Raça", "Cadastrado em");

        petGetResponseList.forEach(
                p -> {
                    String formattedAge = ageFormatter(p.age(), p.ageUnit());

                    System.out.printf(tablePattern,
                            p.id(),
                            p.name(),
                            p.type().getLabel(),
                            p.sex().getAbbreviation(),
                            p.address(),
                            formattedAge,
                            p.weight() == null ? AppConstants.NAO_INFORMADO : p.weight() + "kg",
                            p.breed(),
                            dateFormatter(p.createdAt()));
                });
    }

    private String dateFormatter(LocalDateTime createdAt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return formatter.format(createdAt);
    }

    private String ageFormatter(Double age, AgeUnit ageUnit) {
        if (age == null && ageUnit == null) {
            return AppConstants.NAO_INFORMADO;
        } else {
            return age + " " + ageUnit.getLabel();
        }
    }

    private <T> int getColumnLargestLength(List<T> list, Function<T, String> column, int defaultIfEmpty) {
        int max = list.stream()
                .map(column)
                .map(s -> s == null ? "" : s)
                .mapToInt(String::length)
                .max()
                .orElse(defaultIfEmpty);

        return Math.max(max, defaultIfEmpty);
    }
//endregion
}
