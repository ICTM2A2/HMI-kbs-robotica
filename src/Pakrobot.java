import arduino.Arduino;

import java.lang.reflect.Method;

public class Pakrobot {
    Arduino pakRobot;

    public Pakrobot(){
        pakRobot = new Arduino("COM3", 9600);
        pakRobot.openConnection();
    }

    public void verstuurCoord(String coord, int count){

        pakRobot.serialWrite(coord);

        while(!pakRobot.serialRead().trim().contains("Moved")){
            ;
        }

        System.out.println("Moved");


        while(!pakRobot.serialRead().trim().contains("Done!")){
            ;
        }

        System.out.println("Done!");
    }
}
