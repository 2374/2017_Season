package org.usfirst.frc.team2374.robot.subsystems;

import org.usfirst.frc.team2374.robot.RobotMap;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Grabber extends Subsystem {
	
	private SpeedController grabberController;
	private DigitalInput grabberLimitSwitch;
	
	private static final double MAX_GRABBER_SPEED = 0.5;
	
	public Grabber() {
		grabberController = new Spark(RobotMap.speedControllerGrabber);
		grabberLimitSwitch = new DigitalInput(RobotMap.limitSwitchGrabber);
	}
	
	@Override
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
	
	public void openGrabber() {
		grabberController.set(MAX_GRABBER_SPEED);
	}
	
	public void closeGrabber() {
		grabberController.set(0);
	}
	
	public boolean isGrabberOpen() {
		return grabberLimitSwitch.get();
	}
	
}
