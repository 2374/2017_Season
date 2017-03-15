package org.usfirst.frc.team2374.robot;

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

	private Joystick driver;

	private JoystickButton leftBumper;
	private JoystickButton rightBumper;
	private JoystickButton buttonX;
	private JoystickButton buttonA;
	private JoystickButton buttonY;
	private JoystickButton buttonB;
	private JoystickButton buttonBack;
	private JoystickButton buttonStart;

	public OI() {
		driver = new Joystick(RobotMap.DRIVER_JOY);
		leftBumper = new JoystickButton(driver, RobotMap.RS_LEFT_BUMPER);
		rightBumper = new JoystickButton(driver, RobotMap.RS_RIGHT_BUMPER);
		buttonX = new JoystickButton(driver, RobotMap.RS_BUTTON_X);
		buttonA = new JoystickButton(driver, RobotMap.RS_BUTTON_A);
		buttonY = new JoystickButton(driver, RobotMap.RS_BUTTON_Y);
		buttonB = new JoystickButton(driver, RobotMap.RS_BUTTON_B);
		buttonBack = new JoystickButton(driver, RobotMap.RS_BUTTON_BACK);
		buttonStart = new JoystickButton(driver, RobotMap.RS_BUTTON_START);

		buttonB.whenPressed(new CloseGrabber());
		buttonA.whenPressed(new OpenGrabber());
	}

	public double getDriverLeftY() {
		return driver.getRawAxis(RobotMap.RS_LEFT_AXIS_Y);
	}

	public double getDriverRightY() {
		return driver.getRawAxis(RobotMap.RS_RIGHT_AXIS_Y);
	}

	public double getLeftTrigger() {
		return driver.getRawAxis(RobotMap.RS_LEFT_TRIGGER);
	}

	public double getRightTrigger() {
		return driver.getRawAxis(RobotMap.RS_RIGHT_TRIGGER);
	}

	public boolean getButtonX() {
		return buttonX.get();
	}

	public boolean getButtonA() {
		return buttonA.get();
	}

	public boolean getButtonY() {
		return buttonY.get();
	}

	public boolean getButtonBack() {
		return buttonBack.get();
	}

	public boolean getButtonStart() {
		return buttonStart.get();
	}

	public int getPOV() {
		return driver.getPOV(0);
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
