import arduino.Arduino;

public class Inpakrobot {
    Arduino inpakRobot;

    public Inpakrobot(){
        inpakRobot = new Arduino("COM5",9600);
        inpakRobot.openConnection();
    }

    public void verstuurRichting(String richting){
        inpakRobot.serialWrite(richting);
    }
}
