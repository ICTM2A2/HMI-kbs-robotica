import arduino.Arduino;

public class Pakrobot {
    Arduino pakRobot;

    public Pakrobot() {
        pakRobot = new Arduino("COM3", 9600);
        pakRobot.openConnection();
    }

    public void verstuurCoord(String coord, int count) {
        pakRobot.serialWrite(coord);

        while (!pakRobot.serialRead().trim().contains("Done")) {
        }

        System.out.println("Done!");
    }
}

