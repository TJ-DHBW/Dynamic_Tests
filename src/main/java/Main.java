import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args){
        Camera camera = new Camera.Builder(new MemoryCard()).build();
        char[][] rawFace = camera.getRawFacePicture(1);
        int[] faceBounds = camera.getFaceArea(rawFace);
        Picture pic = camera.extractFace(1, rawFace, faceBounds);
    }
}
