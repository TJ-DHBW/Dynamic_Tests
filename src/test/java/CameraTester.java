import org.junit.jupiter.api.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CameraTester {
    private Camera camera;


    @BeforeEach
    public void setUp(){
        camera = new Camera.Builder(new MemoryCard()).build();
    }

    @Test
    @Order(1)
    @DisplayName("Camera completeness")
    public void completeCamera(){
        Assertions.assertNotNull(camera);

        //Test LEDs
        Assertions.assertNotNull(camera.getIrLeds());
        Assertions.assertEquals(24, camera.getIrLeds().length);

        //Test MemoryCard
        Assertions.assertNotNull(camera.getMemoryCard());

        //Test Chips
        Assertions.assertNotNull(camera.getChips());
        Assertions.assertEquals(2, camera.getChips().length);
    }

    @Test
    @Order(2)
    @DisplayName("Camera on/off")
    public void testPowerSwitch(){
        Assertions.assertFalse(camera.isOn());

        camera.on();
        Assertions.assertTrue(camera.isOn());

        camera.off();
        Assertions.assertFalse(camera.isOn());
    }

    @TestFactory
    @Order(3)
    Stream<DynamicTest> testGetFaceArea(){
        List<DynamicTest> tests = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/faceBoundaries.txt")));

            for (int i = 1; i < 25; i++) {
                Integer[] correctFaceBoundaries = new Integer[0];
                correctFaceBoundaries = Arrays.stream(reader.readLine().split(",")).map(Integer::parseInt).collect(Collectors.toList()).toArray(correctFaceBoundaries);
                Integer[] finalCorrectFaceBoundaries = correctFaceBoundaries;

                int[] faceBoundaries = camera.getFaceArea(camera.getRawFacePicture(i));

                DynamicTest test = DynamicTest.dynamicTest("Test the detection of face " + i, () -> {
                    Assertions.assertEquals(finalCorrectFaceBoundaries[0], faceBoundaries[0]);
                    Assertions.assertEquals(finalCorrectFaceBoundaries[1], faceBoundaries[1]);
                    Assertions.assertEquals(finalCorrectFaceBoundaries[2], faceBoundaries[2]);
                    Assertions.assertEquals(finalCorrectFaceBoundaries[3], faceBoundaries[3]);
                });

                tests.add(test);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return tests.stream();
    }

    @TestFactory
    @Order(4)
    Stream<DynamicTest> testExtractFaceCreatesAndSavesPicture(){
        List<DynamicTest> tests = new ArrayList<>();

        for (int i = 1; i < 25; i++) {
            char[][] faceData = camera.getRawFacePicture(i);
            Picture picture = camera.extractFace(i, faceData, camera.getFaceArea(faceData));

            //We test this separate, because we create all tests before running them.
            Assertions.assertEquals(i, camera.getMemoryCard().getStore().size());

            DynamicTest test = DynamicTest.dynamicTest("Testing creation of Picture "+ i, () -> Assertions.assertNotNull(picture));
            tests.add(test);
        }

        return tests.stream();
    }

    @TestFactory
    @Order(5)
    Stream<DynamicTest> testPictureSize(){
        List<DynamicTest> tests = new ArrayList<>();

        for(int i = 1; i <= 25; i++){
            char[][] faceData = camera.getRawFacePicture(i);
            Picture picture = camera.extractFace(i, faceData, camera.getFaceArea(faceData));

            DynamicTest test = DynamicTest.dynamicTest("Testing Picture size of Picture " + i, () ->{
                Assertions.assertNotNull(picture.getContent());
                Assertions.assertEquals(10, picture.getContent().length);
                for (int j = 0; j < picture.getContent().length; j++) {
                    Assertions.assertEquals(10, picture.getContent()[j].length);
                }
            });
            tests.add(test);
        }

        return tests.stream();
    }
}
