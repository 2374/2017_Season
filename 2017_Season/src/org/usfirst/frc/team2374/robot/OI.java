package org.usfirst.frc.team2374.robot;

import org.usfirst.frc.team2374.robot.commands.belt.MoveBeltToPoint;
import org.usfirst.frc.team2374.robot.commands.grabber.CloseGrabber;
import org.usfirst.frc.team2374.robot.commands.grabber.OpenGrabber;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

	Joystick driver;
	JoystickButton leftBumper;
	JoystickButton rightBumper;
	JoystickButton buttonX;
	JoystickButton buttonA;
	JoystickButton buttonY;
	JoystickButton buttonB;
	JoystickButton buttonBack;
	JoystickButton buttonStart;

	public OI() {
		driver = new Joystick(RobotMap.driverJoy);
		leftBumper = new JoystickButton(driver, RobotMap.rsLeftBumper);
		rightBumper = new JoystickButton(driver, RobotMap.rsRightBumper);
		buttonX = new JoystickButton(driver, RobotMap.rsButtonX);
		buttonA = new JoystickButton(driver, RobotMap.rsButtonA);
		buttonY = new JoystickButton(driver, RobotMap.rsButtonY);
		buttonB = new JoystickButton(driver, RobotMap.rsButtonB);
		buttonBack = new JoystickButton(driver, RobotMap.rsButtonBack);
		buttonStart = new JoystickButton(driver, RobotMap.rsButtonStart);

		buttonB.whenPressed(new MoveBeltToPoint(0));
		buttonB.whenPressed(new CloseGrabber());
		buttonA.whenPressed(new OpenGrabber());
	}

	public double getDriverLeftY() {
		return driver.getRawAxis(RobotMap.rsLeftAxisY);
	}

	public double getDriverRightY() {
		return driver.getRawAxis(RobotMap.rsRightAxisY);
	}

	public double getLeftTrigger() {
		return quadraticScale(driver.getRawAxis(RobotMap.rsLeftTrigger));
	}

	public double getRightTrigger() {
		return quadraticScale(driver.getRawAxis(RobotMap.rsRightTrigger));
	}

	public boolean getButtonX() {
		return driver.getRawButton(RobotMap.rsButtonX);
	}

	public boolean getButtonA() {
		return driver.getRawButton(RobotMap.rsButtonA);
	}

	public boolean getButtonY() {
		return driver.getRawButton(RobotMap.rsButtonY);
	}

	public boolean getButtonBack() {
		return driver.getRawButton(RobotMap.rsButtonBack);
	}

	public boolean getButtonStart() {
		return driver.getRawButton(RobotMap.rsButtonStart);

	}

	public boolean getLeftBumper() {
		return leftBumper.get();
	}

	public boolean getRightBumper() {
		return rightBumper.get();
	}

	public void setRumble(boolean enable) {
		if (enable) {
			driver.setRumble(RumbleType.kLeftRumble, 1);
			driver.setRumble(RumbleType.kRightRumble, 1);
		} else {
			driver.setRumble(RumbleType.kLeftRumble, 0);
			driver.setRumble(RumbleType.kRightRumble, 0);
		}

	}

	public double deadZone(double axisValue, double deadValue) {
		if (Math.abs(axisValue) < deadValue)
			return 0;
		else
			return axisValue;
	}

	public double quadraticScale(double value) {
		return value * Math.abs(value);
	}

	public void toSmartDashboard() {
	}

}
