package main;

import lejos.hardware.lcd.LCD;
import movement.Movements;
import network.Net;
import network.NetSettings;

public class ClientControl {
	
	public void run() {
		Movements movements = new Movements();
		
		int input = 8;	// any value between 1 & 100 will just wait for another input

		//0 to stop the client
		while ( input != 0){
			LCD.clear(4);
			LCD.drawString("waiting for cmd", 0, 4);
			
			short received = Net.receiveRobotCmd(NetSettings.getRobotPort());
			input = received;
			
			LCD.clear(5);
			LCD.drawString("received: " + received, 0, 5);
			

			// just a testing input
			if(input == 99) {
				movements.rotate(360);	// turns wheels by 360 degree
										// wheel has a perimeter of about 18 cm
										// therefore it should move 18 cm forward
			}
			//test input turn right 90 degrees
			if(input == 78){
			movements.rotate(-190);
			}

			//test input turn 90 degrees
			if(input == 77){
				movements.rotate(190);
			}
			
			// to drive forward the value of the entry should be between 101 and 200
			// 101: drive 1 cm forward;.......;200: drive 100 cm forward
			if ( (101<=input)&&(input<=200) ) {
				movements.driveForward(input-100);
				//Net.sendACK();
				
			// to drive backward the value of the entry should be between 201 and 300
			// 201: drive 1 cm backward;.......;300: drive 100 cm backward
			} else if ( (201<=input)&&(input<=300) ) {
				movements.driveBackward(input-200);
				//Net.sendACK();
				
			// to turn left the value of the entry should be between 301 and 500 
	        // 301 turn 1 degree left;.......;500: turn 200 degrees left 
			}else if ( (301<=input)&&(input<=500) ) {
				movements.turnLeft(input-300);
				//Net.sendACK();
				
			// to turn right the value of the entry should be between 501 and 700
			// 501 turn 1 degree right;.......;700: turn 200 degrees right
			}else if ( (501<=input)&&(input<=700) ) {
				movements.turnRight(input-500);
				//Net.sendACK();
            }
		} // time to get another input! :)
	}
}
