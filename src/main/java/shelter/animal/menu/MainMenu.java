package shelter.animal.menu;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import shelter.animal.Filters.PetFilters;
import shelter.animal.service.PetService;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

@RequiredArgsConstructor
@Component
public class MainMenu implements CommandLineRunner {
    private final Scanner scanner;

    @Override
    public void run(String... args){
        runHomeMenu();
    }

    private void runHomeMenu() {
        while (true) {
            int option;
            do {
                showHomeMenu();
                option = validateParseInteger(scanner.nextLine());
            } while (option < 0 || option > 5);
            if (option == 0) return;
            processingHomeMenuOption(option);
        }
    }

    private void showHomeMenu() {
        System.out.println("****************");
        System.out.println("| MENU INICIAL |");
        System.out.println("****************");
        System.out.println("Escolha uma opção:");
        System.out.println("[1] Cadastrar um novo pet");
        System.out.println("[2] Listar todos os pets cadastrados");
        System.out.println("[3] Listar pets  por algum critério (nome, idade, raça, etc)");
        System.out.println("[4] Alterar os dados de um pet cadastrado");
        System.out.println("[5] Deletar um pet cadastrado");
        System.out.println("[0] Sair");
    }

    private void processingHomeMenuOption(int option) {
            try {
                if (option < 0 || option > 5) {
                    System.out.println("Erro: Número inválido. Por favor, digite um número entre 1 e 6");
                }
                switch (option) {
                    case 1 -> PetService.createPet();
                    case 2 -> PetService.listPet();
                    case 3 -> PetService.listPetWithFilter();
                    case 4 -> PetService.updatePet();
                    case 5 -> PetService.deletePet();
                    case 0 -> System.out.println("Programa encerrado");
                }
            } catch (InputMismatchException e) {
                System.out.println("Erro: Letras e caracteres especiais não são aceitos.");
            }
    }
    public static Map<String, String> searchPetWithFilterMenu() {
        Map<Integer, PetFilters> filters = PetFilters.filterMap();
        Map<String, String> parameters = new HashMap<>();
        Scanner sc = new Scanner(System.in);
        System.out.println("*****************");
        System.out.println("| BUSCA DE PETS |");
        System.out.println("*****************");

        int criteriasOption;

        do {
            System.out.println("Você deseja realizar a busca com 1 ou 2 parâmetros?");
            criteriasOption = sc.nextInt();
        } while (criteriasOption < 0 || criteriasOption > 2);


        for (int i = 1; i <= criteriasOption; i++) {
            int option;
            do {
                System.out.printf("Selecione o %dº critério\n", i);
                System.out.println("[1] Nome");
                System.out.println("[2] Tipo");
                System.out.println("[3] Sexo");
                System.out.println("[4] Idade");
                System.out.println("[5] Peso");
                System.out.println("[6] Raça");
                System.out.println("[7] Endereço");
                System.out.println("[8] Voltar para o menu inicial.");

                option = sc.nextInt();
                sc.nextLine();
            } while (option < 1 || option > 8);

            if (filters.containsKey(option)) {
                System.out.printf("Digite o/a %s do pet: ", filters.get(option).getPortugueseWord());
                String parameter = sc.nextLine();
                parameters.put(filters.get(option).getEnglishFilter(), parameter);
            } else if (option == 8) {
                System.out.println("Retornando para o menu inicial...");
                return parameters;
            }
        }
        return parameters;
    }

    private Integer validateParseInteger(String input){
        return Integer.parseInt(input);
    }
}
