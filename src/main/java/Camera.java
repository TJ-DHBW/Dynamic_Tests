import java.io.*;
import java.util.UUID;

public class Camera implements ICamera{
    private String serialNumber;
    private boolean isOn;
    private IRLed[] irLeds;
    private MemoryCard memoryCard;
    private Chip[] chips;


    protected Camera(IRLed[] irLeds, MemoryCard memoryCard, Chip[] chips){
        this.serialNumber = UUID.randomUUID().toString();
        this.isOn = false;

        this.irLeds = irLeds;
        this.memoryCard = memoryCard;
        this.chips = chips;
    }


    @Override
    public void on() {
        isOn = true;
    }

    @Override
    public void off() {
        isOn = false;
    }

    @Override
    public char[][] getRawFacePicture(int faceID) {
        String idString = (faceID<10) ? "0" + faceID : faceID + "";

        char[][] rawFace = new char[21][];

        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File("src/main/resources/face" + idString + ".txt")));

            String line;
            int counter = 0;
            while((line = reader.readLine()) != null){
                rawFace[counter] = line.toCharArray();
                counter++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return rawFace;
    }

    @Override
    public int[] getFaceArea(char[][] face) {
        //TODO implement getFaceArea method
        return new int[0];
    }

    @Override
    public Picture extractFace(int id, char[][] face, int[] area) {
        //TODO implement extractFace method
        return null;
    }


    public static class Builder{
        private final MemoryCard memoryCard;

        Builder(MemoryCard memoryCard){
            this.memoryCard = memoryCard;
        }

        public Camera build(){
            IRLed[] irLeds = new IRLed[24];
            for(int i = 0; i<irLeds.length; i++){
                irLeds[i] = new IRLed();
            }

            return new Camera(irLeds, memoryCard, new Chip[]{new Chip(), new Chip()});
        }
    }
}
