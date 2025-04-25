package taskmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Iterator;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    public static int findMaxByIterator(Iterator<Integer> iterator) {
        int max = 0;
        while (iterator.hasNext()) {
            if (iterator.next() > max) {
                max = iterator.next();
            } else {
                iterator.next();
            }
        }
        return max;
    }
}



