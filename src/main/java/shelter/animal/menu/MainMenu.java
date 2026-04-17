package shelter.animal.menu;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import shelter.animal.exceptions.BusinessException;
import shelter.animal.exceptions.ConsoleException;
import shelter.animal.utils.ConsoleUtils;
import shelter.animal.utils.InputValidator;

import java.util.Scanner;

@RequiredArgsConstructor
@Component
public class MainMenu implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(MainMenu.class);

    private final Scanner scanner;
    private final ConsoleUtils console;
    private final InputValidator validator;
    private final AnimalMenu animalMenu;

    @Override
    public void run(String... args) {
        do {
            try {
                console.clear();
                System.out.println();
                System.out.println("**********************************");
                System.out.println("* SISTEMA DE CADASTRO DE ANIMAIS *");
                System.out.println("*         MENU PRINCIPAL         *");
                System.out.println("**********************************\n");
                System.out.println("Escolha uma opção:");
                System.out.println("[1] Gerenciar Animais");
                System.out.println("[0] Encerrar o programa");
                System.out.print("Opção: ");

                int option = validator.parseInteger(scanner.nextLine());

                switch (option) {
                    case 1 -> {
                        console.clear();
                        animalMenu.runAnimalMenu();
                    }
                    case 0 -> {
                        System.out.println("Sistema encerrado.");
                        return;
                    }
                    default -> System.out.println("Opção inválida");
                }
            } catch (BusinessException e) {
                log.warn("Aviso: {}", e.getMessage());
                System.out.println("Aviso: " + e.getMessage());
                console.sleep(2);
            } catch (Exception e) {
                log.error("Erro: {}", e.getMessage());
                System.out.println("Erro: " + e.getMessage());
                console.sleep(3);
            }
        } while (true);
    }
}
