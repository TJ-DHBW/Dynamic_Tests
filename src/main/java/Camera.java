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
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return rawFace;
    }

    @Override
    public int[] getFaceArea(char[][] face) {
        int x1 = -1, x2 = -1, y1 = -1, y2 = -1;

        //Find the first +
        boolean found = false;
        for(int i = 0; i<face.length; i++){
            for (int j = 0; j < face[i].length; j++) {
                if(face[i][j] == '+'){
                    x1 = i;
                    y1 = j+1;
                    found = true;
                    break;
                }
            }
            if(found) break;
        }

        //Find the last +
        found = false;
        for (int i = face.length-1; i > 0; i--) {
            for(int j = face[i].length-1; j > 0; j--){
                if(face[i][j] == '+'){
                    x2 = i;
                    y2 = j-1;
                    found = true;
                    break;
                }
            }
            if(found) break;
        }

        return new int[]{x1, y1, x2, y2};
    }

    @Override
    public Picture extractFace(int id, char[][] face, int[] area) {
        char[][] onlyFace = new char[area[2]-area[0]+1][area[3]-area[1]+1];

        for(int i = area[0]; i <= area[2]; i++){
            if (area[3] + 1 - area[1] >= 0)
                System.arraycopy(face[i], area[1], onlyFace[i - area[0]], 0, area[3] + 1 - area[1]);
        }

        Picture facePicture = new Picture(id, onlyFace);
        memoryCard.store(facePicture);

        return facePicture;
    }

    //region Getter
    public boolean isOn() {
        return isOn;
    }

    public IRLed[] getIrLeds() {
        return irLeds;
    }

    public MemoryCard getMemoryCard() {
        return memoryCard;
    }

    public Chip[] getChips() {
        return chips;
    }
    //endregion

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
