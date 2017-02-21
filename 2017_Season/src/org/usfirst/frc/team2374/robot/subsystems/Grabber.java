package org.usfirst.frc.team2374.robot.subsystems;

import org.usfirst.frc.team2374.robot.RobotMap;
import org.usfirst.frc.team2374.robot.commands.grabber.GrabberWithJoystick;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// TODO: (Code review) FrappachinoFile
public class Grabber extends Subsystem {

	private SpeedController grabberController;
	private DigitalInput openLimitSwitch;
	private DigitalInput closeLimitSwitch;

	private static final double MAX_GRABBER_SPEED = 0.25;

	/*
	 * When the limit switches are open, which is the default condition, they
	 * return true
	 */
	public Grabber() {
		grabberController = new Spark(RobotMap.speedControllerGrabber);
		openLimitSwitch = new DigitalInput(RobotMap.limitSwitchGrabberOpen);
		closeLimitSwitch = new DigitalInput(RobotMap.limitSwitchGrabberClose);
	}

	@Override
	public void initDefaultCommand() {
		setDefaultCommand(new GrabberWithJoystick());
	}

	public void open() {
		grabberController.set(MAX_GRABBER_SPEED);
	}

	public void close() {
		grabberController.set(-MAX_GRABBER_SPEED);
	}

	public void stop() {
		grabberController.set(0);
	}

	public boolean isOpen() {
		return !openLimitSwitch.get();
	}

	public boolean isClose() {
		return !closeLimitSwitch.get();
	}

	public void toSmartDashboard() {
		SmartDashboard.putBoolean("grabber_open", isOpen());
		SmartDashboard.putBoolean("grabber_close", isClose());
	}

}
