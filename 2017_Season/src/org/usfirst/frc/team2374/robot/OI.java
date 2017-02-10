package org.usfirst.frc.team2374.robot;

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
	JoystickButton buttonB;

	public OI() {
		driver = new Joystick(RobotMap.driverJoy);
		leftBumper = new JoystickButton(driver, RobotMap.rsLeftBumper);
		rightBumper = new JoystickButton(driver, RobotMap.rsRightBumper);
		buttonX = new JoystickButton(driver, RobotMap.rsButtonX);
		buttonA = new JoystickButton(driver, RobotMap.rsButtonA);
		buttonB = new JoystickButton(driver, RobotMap.rsButtonB);

		// buttonB.whenPressed(new PlaceGear());
		// leftBumper.whenPressed(new OpenGrabber());
		// rightBumper.whenPressed(new CloseGrabber());
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

	public boolean getButtonB() {
		return driver.getRawButton(RobotMap.rsButtonB);
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
