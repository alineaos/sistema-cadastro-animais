package shelter.animal.utils;

import org.springframework.stereotype.Component;
import shelter.animal.exceptions.ConsoleException;

@Component
public class ConsoleUtils {

    public void clear(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void sleep(int seconds){
        try{
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            throw new ConsoleException("A espera foi interrompida inesperadamente. ", e);
        }
    }
}
