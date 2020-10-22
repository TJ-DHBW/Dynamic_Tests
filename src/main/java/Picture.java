import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class Picture {
    private int faceID;
    private char[][] content;
    private long nanoTimeStamp;

    Picture(int faceID, char[][] content){
        this.faceID = faceID;
        this.content = content;
        this.nanoTimeStamp = LocalDateTime.now().toEpochSecond(ZoneOffset.ofHours(1));
    }

    public char[][] getContent() {
        return content;
    }
}
