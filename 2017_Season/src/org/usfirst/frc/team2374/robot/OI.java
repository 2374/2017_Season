package org.usfirst.frc.team2374.robot;

import org.usfirst.frc.team2374.robot.commands.auto.LoadGear;
import org.usfirst.frc.team2374.robot.commands.belt.MoveBeltToPoint;
import org.usfirst.frc.team2374.robot.commands.belt.ResetBelt;
import org.usfirst.frc.team2374.robot.commands.grabber.CloseGrabber;
import org.usfirst.frc.team2374.robot.commands.grabber.OpenGrabber;
import org.usfirst.frc.team2374.robot.commands.oi.ClearScheduler;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
	private JoystickButton buttonL3;
	private JoystickButton buttonR3;

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
		buttonL3 = new JoystickButton(driver, RobotMap.RS_L3);
		buttonR3 = new JoystickButton(driver, RobotMap.RS_R3);

		buttonA.whenPressed(new CloseGrabber());
		buttonB.whenPressed(new OpenGrabber());
		buttonX.whenPressed(new MoveBeltToPoint(0));
		buttonY.whenPressed(new LoadGear());
		buttonL3.whenPressed(new ClearScheduler());

		SmartDashboard.putData("ResetBelt", new ResetBelt());
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

	public boolean getButtonR3() {
		return buttonR3.get();
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

	public static double deadZone(double axisValue, double deadValue) {
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
