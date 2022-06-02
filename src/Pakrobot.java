import arduino.Arduino;

public class Pakrobot {
    Arduino pakRobot;

    public Pakrobot() {
        pakRobot = new Arduino("COM3", 9600);
        pakRobot.openConnection();
    }

    public void verstuurCoord(String coord) {
        PakrobotMultiThread pmt = new PakrobotMultiThread();

        pakRobot.serialWrite(coord);

        while (!pakRobot.serialRead().trim().contains("Done")) {
        }

        System.out.println("Done!");
    }
}

class PakrobotMultiThread extends Thread{
    @Override
    public void run() {
        super.run();


    }
}