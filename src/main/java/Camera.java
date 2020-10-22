public class Camera implements ICamera{
    private String serialNumber;
    private boolean isOn;

    protected Camera(){
        //TODO implement constructor for camera
    }


    @Override
    public void on() {
        //TODO implement on method
    }

    @Override
    public void off() {
        //TODO implement off method
    }

    @Override
    public char[][] getRawFacePicture(int faceID) {
        //TODO implement getRawFacePicture method
        return new char[0][];
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
        //TODO implement Builder for Camera
    }
}
