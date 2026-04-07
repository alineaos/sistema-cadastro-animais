package shelter.animal.menu;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import shelter.animal.exceptions.BusinessException;
import shelter.animal.exceptions.InvalidRequestException;
import shelter.animal.utils.InputValidator;

import java.util.Scanner;

@RequiredArgsConstructor
@Component
@Log4j2
public class MainMenuJpa implements CommandLineRunner {
    private final Scanner scanner;
    private final InputValidator validator;
    private final PetMenu petMenu;
    private final MainMenuLegacy mainMenuLegacy;

    @Override
    public void run(String... args){
        do {
            try {
                System.out.println("\n**********************************");
                System.out.println("* SISTEMA DE CADASTRO DE ANIMAIS *");
                System.out.println("*         MENU PRINCIPAL         *");
                System.out.println("**********************************\n");
                System.out.println("Escolha uma opção:");
                System.out.println("[1] Gerenciar Animais (Versão 2.0, em desenvolvimento)");
                System.out.println("[2] Gerenciar Animais (Versão 1.0)");
                System.out.println("[0] Encerrar o programa");
                System.out.print("Opção: ");

                int option = validator.parseInteger(scanner.nextLine());

                if (option == 0) return;

                switch (option) {
                    case 1 -> petMenu.runPetMenu();
                    case 2 -> mainMenuLegacy.runHomeMenu();
                    default -> System.out.println("Opção inválida");
                }
            } catch (BusinessException  e) {
                log.warn("Aviso: {}", e.getMessage());
                System.out.println("Aviso: " + e.getMessage());
            } catch (InvalidRequestException e){
                e.getConstraintViolations().forEach(constraint ->
                        log.warn("{} {}", e.getMessage(), constraint.getMessageTemplate()));

                System.out.print("Dados inválidos:");
                e.getConstraintViolations()
                        .forEach(constraint -> System.out.printf(" '%s' ", constraint.getMessageTemplate()));
            }
        }while (true);
    }
}
