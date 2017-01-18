package org.usfirst.frc.team2374.robot.subsystems;

import org.usfirst.frc.team2374.robot.RobotMap;
import org.usfirst.frc.team2374.robot.commands.drivetrain.DriveWithJoystick;
import org.usfirst.frc.team2374.util.MultiCANTalonPIDSource;
import org.usfirst.frc.team2374.util.SimplePIDOutput;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drivetrain extends Subsystem {
	
	private CANTalon masterLeft, masterRight, fLeft, fRight, bLeft, bRight;
	private RobotDrive robotDrive;
	private AHRS navX;
	
	private PIDController gyroPID;
	private SimplePIDOutput gyroOut;
	private static final double gyroP = 0;
	private static final double gyroI = 0;
	private static final double gyroD = 0;
	
	private PIDController drivePID;
	private MultiCANTalonPIDSource driveIn;
	private SimplePIDOutput driveOut;
	private static final double driveP = 0;
	private static final double driveI = 0;
	private static final double driveD = 0;
	
	public static final double MAX_AUTO_SPEED = 0.75;
	private static final double WHEEL_DIAMETER = 6; //inches

	public Drivetrain() {
		masterLeft = new CANTalon(RobotMap.talonDriveMasterLeft);
		masterRight = new CANTalon(RobotMap.talonDriveMasterRight);
		fLeft = new CANTalon(RobotMap.talonDriveFrontLeft);
		fRight = new CANTalon(RobotMap.talonDriveFrontRight);
		bLeft = new CANTalon(RobotMap.talonDriveBackLeft);
		bRight = new CANTalon(RobotMap.talonDriveBackRight);		

		fLeft.changeControlMode(TalonControlMode.Follower);
		fRight.changeControlMode(TalonControlMode.Follower);
		bLeft.changeControlMode(TalonControlMode.Follower);
		bRight.changeControlMode(TalonControlMode.Follower);
		fLeft.set(RobotMap.talonDriveMasterLeft);
		fRight.set(RobotMap.talonDriveMasterRight);
		bLeft.set(RobotMap.talonDriveMasterLeft);
		bRight.set(RobotMap.talonDriveMasterRight);
		
		masterLeft.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		masterRight.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		
		robotDrive = new RobotDrive(masterLeft, masterRight);
		robotDrive.setSafetyEnabled(true);
		robotDrive.setExpiration(0.2);
		
		navX = new AHRS(SPI.Port.kMXP);
		navX.setPIDSourceType(PIDSourceType.kDisplacement);
		gyroPID = new PIDController(gyroP, gyroI, gyroD, navX, gyroOut);
		gyroPID.setContinuous();
		gyroPID.setInputRange(-180, 180);
		
		driveIn = new MultiCANTalonPIDSource(masterLeft, masterRight);
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
		masterLeft.setPosition(0);
		masterRight.setPosition(0);
	}
	
	public void resetGyro() {
		navX.reset();
	}
	
	public double getAngle() {
		return navX.getYaw();
	}
	
	public double getLeftDistanceInches() {
		return rotationsToInches(masterLeft.getPosition());
	}
	
	public double getRightDistanceInches() {
		return rotationsToInches(masterRight.getPosition());
	}
	
	public double getLeftVelocityInchesPerSecond() {
		return rpmToInchesPerSecond(masterLeft.getSpeed());
	}
	
	public double getRightVelocityInchesPerSecond() {
		return rpmToInchesPerSecond(masterRight.getSpeed());
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
