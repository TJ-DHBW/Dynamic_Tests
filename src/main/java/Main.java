public class Main {
    public static void main(String[] args){
        Camera camera = new Camera.Builder(new MemoryCard()).build();
        char[][] rawFace = camera.getRawFacePicture(1);
        int[] faceBounds = camera.getFaceArea(rawFace);
    }
}
