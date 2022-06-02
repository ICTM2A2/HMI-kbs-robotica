import arduino.Arduino;

public class Pakrobot {
    Arduino pakRobot;

    public Pakrobot() {
//        pakRobot = new Arduino("COM3", 9600);
//        pakRobot.openConnection();
    }

    public void verstuurCoord(String coord, int count) {
//        pakRobot.serialWrite(coord);

        for (int i = 0; i < count; i++) {
//            while (!pakRobot.serialRead().trim().contains("Moved")) {
//            }

            System.out.println("Moved");
        }

//        while (!pakRobot.serialRead().trim().contains("Done!")) {
//        }

        System.out.println("Done!");
    }
}

