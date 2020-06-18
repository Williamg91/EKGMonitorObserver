import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        SerialPort[] commPorts = SerialPort.getCommPorts();
        for (int i = 0, commPortsLength = commPorts.length; i < commPortsLength; i++) {
            SerialPort port = commPorts[i];
            System.out.println(i +": " +port.getSystemPortName());
        }
        System.out.println("Select Port: ");
        Scanner scanner = new Scanner(System.in);
        int selectedPort = scanner.nextInt();
        SerialPort port = commPorts[selectedPort];
        port.openPort();
        port.setBaudRate(115200);
        port.addDataListener(new SerialPortDataListener() {
            @Override
            public int getListeningEvents() {
                return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
            }

            @Override
            public void serialEvent(SerialPortEvent serialPortEvent) {
                if (serialPortEvent.getEventType()!=SerialPort.LISTENING_EVENT_DATA_AVAILABLE){
                    System.out.println("Reading");
                    return;
                }
                byte[] newData = new byte[port.bytesAvailable()];
                int numRead = port.readBytes(newData, newData.length);
                System.out.print(new String(newData));
            }
        });

    }
}
