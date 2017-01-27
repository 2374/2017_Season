package org.usfirst.frc.team2374.robot.subsystems;

import org.usfirst.frc.team2374.robot.RobotMap;
import org.usfirst.frc.team2374.robot.commands.drivetrain.DriveWithJoystick;
import org.usfirst.frc.team2374.util.MultiEncoderPIDSource;
import org.usfirst.frc.team2374.util.MultiSpeedController;
import org.usfirst.frc.team2374.util.SimplePIDOutput;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drivetrain extends Subsystem {

	private Talon left0, left1, right0, right1;
	private Victor left2, right2;
	private MultiSpeedController masterLeft, masterRight;
	private Encoder eLeft, eRight;
	private RobotDrive robotDrive;
	private AHRS navX;

	private PIDController gyroPID;
	private SimplePIDOutput gyroOut;
	private static final double gyroP = 0;
	private static final double gyroI = 0;
	private static final double gyroD = 0;

	private PIDController drivePID;
	private MultiEncoderPIDSource driveIn;
	private SimplePIDOutput driveOut;
	private static final double driveP = 0;
	private static final double driveI = 0;
	private static final double driveD = 0;

	public static final double MAX_AUTO_SPEED = 0.75;
	private static final double WHEEL_DIAMETER = 6; // inches

	public Drivetrain() {
		left0 = new Talon(RobotMap.talonDriveTalonLeft0);
		left1 = new Talon(RobotMap.talonDriveTalonLeft1);
		left2 = new Victor(RobotMap.talonDriveVictorLeft);
		right0 = new Talon(RobotMap.talonDriveTalonRight0);
		right1 = new Talon(RobotMap.talonDriveTalonRight1);
		right2 = new Victor(RobotMap.talonDriveVictorRight);

		masterLeft = new MultiSpeedController(left0, left1, left2);
		masterRight = new MultiSpeedController(right0, right1, right2);

		robotDrive = new RobotDrive(masterLeft, masterRight);
		robotDrive.setSafetyEnabled(true);
		robotDrive.setExpiration(0.2);

		navX = new AHRS(SPI.Port.kMXP);
		navX.setPIDSourceType(PIDSourceType.kDisplacement);
		gyroPID = new PIDController(gyroP, gyroI, gyroD, navX, gyroOut);
		gyroPID.setContinuous();
		gyroPID.setInputRange(-180, 180);

		driveIn = new MultiEncoderPIDSource(eLeft, eRight);
		driveIn.setPIDSourceType(PIDSourceType.kDisplacement);
		drivePID = new PIDController(driveP, driveI, driveD, driveIn, driveOut);
	}

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new DriveWithJoystick());
	}

	public void tankDrive(double left, double right) {
		robotDrive.tankDrive(left, right);
	}

	public void arcadeDrive(double move, double rotate) {
		robotDrive.arcadeDrive(move, rotate);
	}

	public void setDrivePIDSpeed(double speed) {
		double output = Math.min(speed, MAX_AUTO_SPEED);
		drivePID.setOutputRange(-output, output);
	}

	public void setGyroPIDSpeed(double speed) {
		double output = Math.min(speed, MAX_AUTO_SPEED);
		gyroPID.setOutputRange(-output, output);
	}

	public void setDrivePIDSetPoint(double inches) {
		drivePID.setSetpoint(inchesToRotations(inches));
	}

	public void setGyroPIDSetPoint(double angle) {
		gyroPID.setSetpoint(angle);
	}

	public double getDrivePIDOutput() {
		return driveOut.get();
	}

	public double getGyroPIDOutput() {
		return gyroOut.get();
	}

	public void enableDrivePID(boolean enable) {
		if (enable)
			drivePID.enable();
		else
			drivePID.disable();
	}

	public void enableGyroPID(boolean enable) {
		if (enable)
			drivePID.enable();
		else
			drivePID.disable();
	}

	public void resetEncoders() {
		eLeft.reset();
		eRight.reset();
	}

	public void resetGyro() {
		navX.reset();
	}

	public double getAngle() {
		return navX.getYaw();
	}

	public double getLeftDistanceInches() {
		return rotationsToInches(eLeft.getDistance());
	}

	public double getRightDistanceInches() {
		return rotationsToInches(eRight.getDistance());
	}

	public double getLeftVelocityInchesPerSecond() {
		return rpmToInchesPerSecond(eLeft.getRate());
	}

	public double getRightVelocityInchesPerSecond() {
		return rpmToInchesPerSecond(eRight.getRate());
	}

	private static double rotationsToInches(double rotations) {
		return rotations * (WHEEL_DIAMETER * Math.PI);
	}

	private static double inchesToRotations(double inches) {
		return inches / (WHEEL_DIAMETER * Math.PI);
	}

	private static double rpmToInchesPerSecond(double rpm) {
		return rotationsToInches(rpm) / 60;
	}

	public void toSmartDashboard() {
		SmartDashboard.putNumber("left_position", getLeftDistanceInches());
		SmartDashboard.putNumber("right_position", getRightDistanceInches());
		SmartDashboard.putNumber("left_velocity", getLeftVelocityInchesPerSecond());
		SmartDashboard.putNumber("right_velocity", getRightVelocityInchesPerSecond());
		SmartDashboard.putNumber("drive_error", drivePID.getError());
		SmartDashboard.putNumber("gyro_angle", navX.getYaw());
		SmartDashboard.putNumber("heading_error", gyroPID.getError());
	}

}
