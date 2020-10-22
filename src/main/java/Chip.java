import java.util.UUID;

public class Chip {
    private String uuid = UUID.randomUUID().toString();
    private Core[] cores;

    Chip(){
        cores = new Core[]{new Core(), new Core()};
    }
}
