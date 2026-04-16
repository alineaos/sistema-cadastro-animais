package shelter.animal.utils;

import org.springframework.stereotype.Component;

@Component
public class ConsoleUtils {

    public void clear(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
