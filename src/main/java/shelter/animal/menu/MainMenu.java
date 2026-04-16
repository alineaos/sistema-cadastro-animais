package shelter.animal.menu;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import shelter.animal.exceptions.BusinessException;
import shelter.animal.exceptions.InvalidRequestException;
import shelter.animal.exceptions.NotFoundException;
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
    public void run(String... args){
        do {
            console.clear();
            try {
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

                if (option == 0) {
                    System.out.println("Sistema encerrado.");
                    return;
                }


                switch (option) {
                    case 1 -> {
                        console.clear();
                        animalMenu.runAnimalMenu();
                    }
                    default -> System.out.println("Opção inválida");
                }
            } catch (BusinessException | NotFoundException e) {
                log.warn("Aviso: {}", e.getMessage());
                System.out.println("Aviso: " + e.getMessage());
            } catch (InvalidRequestException e){
                e.getConstraintViolations().forEach(constraint ->
                        log.info("{} {}", e.getMessage(), constraint.getMessageTemplate()));
                System.out.print("Dados inválidos:");
                e.getConstraintViolations()
                        .forEach(constraint -> System.out.printf(" '%s' ", constraint.getMessageTemplate()));
            }
        }while (true);
    }
}
