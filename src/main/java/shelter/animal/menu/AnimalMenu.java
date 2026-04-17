package shelter.animal.menu;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shelter.animal.controller.AnimalController;
import shelter.animal.dto.response.AnimalGetResponse;
import shelter.animal.dto.response.AnimalPostResponse;
import shelter.animal.models.enums.AgeUnit;
import shelter.animal.models.enums.AnimalSex;
import shelter.animal.models.enums.AnimalType;
import shelter.animal.utils.AppConstants;
import shelter.animal.utils.ConsoleUtils;
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
public class AnimalMenu {
    private final Scanner scanner;
    private final ConsoleUtils console;
    private final InputValidator validator;
    private final AnimalController controller;

    public void runAnimalMenu() {
        while (true) {
            int option;
            do {
                showAnimalMenu();
                option = validator.parseInteger(scanner.nextLine());
            } while (option < 0 | option > 6);
            if (option == 0) return;
            processingAnimalMenuOption(option);
        }
    }

    private void processingAnimalMenuOption(int option) {
        switch (option) {
            case 1 -> {
                console.clear();
                handleSave();
            }
            case 2 -> {
                console.clear();
                handleFindAll();
            }
            case 3 -> {
                console.clear();
                handleFindById();
            }
            case 4 -> {
                console.clear();
                handleFindByCriteria();
            }
            case 5 -> {
                console.clear();
                handleDelete();
            }
            case 6 -> {
                console.clear();
                handleUpdate();
            }
            default -> throw new IllegalArgumentException("Opção inválida.");
        }
    }

    private void showAnimalMenu() {
        System.out.println();
        System.out.println("**************************");
        System.out.println("* GERENCIADOR DE ANIMAIS *");
        System.out.println("**************************");
        System.out.println("Escolha uma opção: ");
        System.out.println("[1] Cadastrar um novo animal");
        System.out.println("[2] Listar todos os animais cadastrados");
        System.out.println("[3] Listar animal por ID");
        System.out.println("[4] Listar por critérios");
        System.out.println("[5] Deletar cadastro");
        System.out.println("[6] Atualizar cadastro");
        System.out.println("[0] Voltar ao menu anterior");
        System.out.print("Opção: ");
    }

//region ANIMAL ACTIONS (HANDLES)
// ========================================================================================================
// METHODS TO HANDLE USER ACTIONS
// ========================================================================================================

    private void handleSave() {
        System.out.println("Para cadastrar um animal, preencha a ficha a seguir.");

        System.out.print("Nome do animal: ");
        String name = scanner.nextLine();

        AnimalType animalType = runAnimalTypeMenu();
        AnimalSex animalSex = runAnimalSexMenu();

        System.out.println("\nEm qual endereço e bairro ele foi encontrado?");
        System.out.print("Nome da rua: ");
        String street = parseTextInputNonBlank("O nome da rua não pode estar em branco. Digite novamente: ");

        System.out.print("\nNúmero ou deixe em branco: ");
        String number = parseAddressNumberInput();

        System.out.print("\nCidade: ");
        String city = parseTextInputNonBlank("O nome da cidade não pode estar em branco. Digite novamente: ");

        System.out.print("\nIdade aproximada do animal: ");
        Integer age = parseNullableInteger();

        AgeUnit ageUnit = null;
        if (age != null) ageUnit = runAgeUnitMenu();


        System.out.print("\nPeso aproximado do animal: ");
        Double weight = parseNullableDouble();

        System.out.print("\nRaça do animal: ");
        String breed = scanner.nextLine();

        AnimalPostResponse postResponse = controller.save(name, animalType, animalSex, street, number, city, age, ageUnit, weight, breed);

        System.out.printf("\nO Animal com nome '%s' foi cadastrado com sucesso! Id: %d%n", postResponse.name(), postResponse.id());
    }

    private void handleFindAll() {
        System.out.println("Listando todos os animais cadastrados...");
        List<AnimalGetResponse> getResponses = controller.findAll();
        printAnimalTable(getResponses);
    }

    private void handleFindById() {
        System.out.print("\nDigite o ID: ");
        Long id = validator.parseLong(scanner.nextLine());

        System.out.printf("Localizando o animal com id %d...\n", id);
        AnimalGetResponse getResponse = controller.findById(id);

        printAnimalTable(Collections.singletonList(getResponse));
    }

    private void handleFindByCriteria() {
        Map<String, Object> filters = new HashMap<>();
        while (true) {
            System.out.println("Selecione o parâmetro (ou 0 para iniciar a busca).");
            showAnimalFilterMenu();
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

                case 2 -> filters.put("type", runAnimalTypeMenu());

                case 3 -> filters.put("sex", runAnimalSexMenu());

                case 4 -> {
                    System.out.print("\nDigite a idade ou digite 0 para buscar por 'Não informado': ");
                    filters.put("age", validator.parseInteger(scanner.nextLine()));
                    if (filters.get("age") != null && (Integer) filters.get("age") != 0) {
                        filters.put("ageUnit", runAgeUnitMenu());
                    }
                }

                case 5 -> {
                    System.out.print("\nDigite o peso ou digite 0 para buscar por 'Não informado': ");
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

        List<AnimalGetResponse> getResponses = controller.findByCriteria(filters);

        System.out.println("Buscando cadastros...");
        printAnimalTable(getResponses);
    }

    private void handleDelete() {
        System.out.println("Digite o ID do cadastro a ser deletado ou 0 para cancelar. ");
        System.out.print("ID: ");
        Long id = validator.parseLong(scanner.nextLine());

        if (id == 0) return;

        System.out.printf("ID selecionado: %d\n", id);
        if (!confirmAction("Atenção: Você tem certeza que deseja deletar o cadastro selecionado? Essa ação é irreversível."))
            return;

        controller.delete(id);
        System.out.println("Cadastro deletado com sucesso.");
    }

    private void handleUpdate() {
        System.out.println("Digite o ID do cadastro a ser atualizado.");
        System.out.print("ID: ");
        Long id = validator.parseLong(scanner.nextLine());
        AnimalGetResponse animalFromDb = controller.findById(id);
        printAnimalTable(Collections.singletonList(animalFromDb));
        System.out.println("\nLembre-se: Não é possível alterar o id, tipo ou sexo do animal cadastrado.");

        System.out.printf("Nome atual: %s\n", animalFromDb.name());
        System.out.print("Digite o novo nome ou enter para manter o atual: ");
        String newName = scanner.nextLine();

        System.out.printf("Rua atual: %s\n", animalFromDb.address().street());
        System.out.print("Digite a nova rua ou enter para manter a atual: ");
        String newStreet = scanner.nextLine();

        System.out.printf("Número atual: %s\n", animalFromDb.address().number());
        System.out.print("Digite o novo número ou enter para manter o atual: ");
        String newNumber = scanner.nextLine();

        System.out.printf("Cidade atual: %s\n", animalFromDb.address().city());
        System.out.print("Digite a nova cidade ou enter para manter a atual: ");
        String newCity = scanner.nextLine();

        System.out.printf("Idade atual: %s %s\n", animalFromDb.age(), animalFromDb.ageUnit().getLabel());
        System.out.print("Digite a nova idade ou enter para manter a atual: ");
        Integer newAge = parseNullableInteger();

        AgeUnit newAgeUnit = null;
        if (newAge != null) newAgeUnit = runAgeUnitMenu();

        System.out.printf("Peso atual: %s\n", animalFromDb.weight());
        System.out.print("Digite o novo peso ou enter para manter o atual: ");
        Double newWeight = parseNullableDouble();

        System.out.printf("Raça atual: %s\n", animalFromDb.breed());
        System.out.print("Digite a nova raça ou enter para manter a atual: ");
        String newBreed = scanner.nextLine();

        controller.update(id, newName, newStreet, newNumber, newCity, newAge, newAgeUnit, newWeight, newBreed);

        System.out.println("Cadastro atualizado com sucesso.");
    }

//endregion

    //region SUBMENUS (AUX MENUS)
// ========================================================================================================
// METHODS TO HELP WITH I/O
// ========================================================================================================
    private AnimalType runAnimalTypeMenu() {
        System.out.println("\nQual o tipo do animal?");
        showAnimalTypeMenu();
        System.out.print("Digite o código: ");
        int option = validator.parseInteger(scanner.nextLine());
        return processingAnimalTypeMenu(option);
    }

    private void showAnimalTypeMenu() {
        for (AnimalType type : AnimalType.values()) {
            System.out.printf("[%d] %s%n", type.getCode(), type.getLabel());
        }
    }

    private AnimalType processingAnimalTypeMenu(int option) {
        return AnimalType.selectByCode(option);
    }

    private AnimalSex runAnimalSexMenu() {
        System.out.println("\nQual o sexo do animal?");
        showAnimalSexMenu();
        System.out.print("Digite o código: ");
        int option = validator.parseInteger(scanner.nextLine());
        return processingAnimalSexMenu(option);
    }

    private void showAnimalSexMenu() {
        System.out.println("[1] Fêmea");
        System.out.println("[2] Macho");
    }

    private AnimalSex processingAnimalSexMenu(int option) {
        return AnimalSex.selectByCode(option);
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

    private Integer parseNullableInteger() {
        String numberInput = scanner.nextLine();
        if (numberInput.isEmpty()) {
            return null;
        }
        return validator.parseInteger(numberInput);
    }

    private Double parseNullableDouble() {
        String numberInput = scanner.nextLine();
        if (numberInput.isEmpty()) {
            return null;
        }
        return validator.parseDouble(numberInput);
    }

    private void showAnimalFilterMenu() {
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

    private boolean confirmAction(String text) {
        System.out.println(text);
        while (true) {
            System.out.println("[1] Confirmar");
            System.out.println("[2] Cancelar");
            System.out.print("Opção: ");
            Integer input = validator.parseInteger(scanner.nextLine());

            if (input == 1) {
                return true;
            } else if (input == 2) {
                System.out.println("Ação cancelada pelo usuário.");
                return false;
            } else {
                System.out.println("Opção inválida. Digite novamente");
            }
        }
    }
//endregion

    //region AUX PRINT INTERFACE
// ========================================================================================================
// METHODS TO HELP WITH PRINTS (Table formatting)
// ========================================================================================================
    private void printAnimalTable(List<AnimalGetResponse> getResponses) {
        if (getResponses.isEmpty()) {
            System.out.println("Nenhum registro encontrado.");
            return;
        }

        int nameColumLength = getColumnLargestLength(getResponses, AnimalGetResponse::name, 4);
        int addressColumLength = getColumnLargestLength(getResponses, p -> p.address().toString(), 8);
        int breedColumLength = getColumnLargestLength(getResponses, AnimalGetResponse::breed, 4);

        String tablePattern = "[%-3s] %-" + nameColumLength + "s | %-8s | %-4s | %-" + addressColumLength
                + "s | %-13s | %-13s | %-" + breedColumLength + "s | %s%n";
        System.out.printf(tablePattern,
                "ID", "Nome", "Tipo", "Sexo", "Endereço", "Idade", "Peso", "Raça", "Cadastrado em");

        getResponses.forEach(
                p -> {
                    String formattedAge = ageFormatter(p.age(), p.ageUnit());
                    String formattedWeight = weightFormatter(p.weight());

                    System.out.printf(tablePattern,
                            p.id(),
                            p.name(),
                            p.type().getLabel(),
                            p.sex().getAbbreviation(),
                            p.address(),
                            formattedAge,
                            formattedWeight,
                            p.breed(),
                            dateFormatter(p.createdAt()));
                });
    }

    private String dateFormatter(LocalDateTime createdAt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return formatter.format(createdAt);
    }

    private String ageFormatter(Integer age, AgeUnit ageUnit) {
        if (age == null && ageUnit == null) {
            return AppConstants.NAO_INFORMADO;
        } else {
            return age + " " + ageUnit.getLabel();
        }
    }

    private String weightFormatter(Double weight) {
        if (weight == null) {
            return AppConstants.NAO_INFORMADO;
        } else {
            return weight + "kg";
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
