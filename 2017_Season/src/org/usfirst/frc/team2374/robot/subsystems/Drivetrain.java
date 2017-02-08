package org.usfirst.frc.team2374.robot.subsystems;

import org.usfirst.frc.team2374.robot.RobotMap;
import org.usfirst.frc.team2374.robot.commands.drivetrain.DriveWithJoystick;
import org.usfirst.frc.team2374.util.SimplePIDOutput;
import org.usfirst.frc.team2374.util.TwoEncoderPIDSource;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drivetrain extends Subsystem {

	private CANTalon masterLeft, masterRight, fLeft, fRight, bLeft, bRight;
	private RobotDrive robotDrive;
	private Encoder leftEncoder;
	private Encoder rightEncoder;
	private AHRS navX;

	private PIDController gyroPID;
	private SimplePIDOutput gyroOut;
	private static final double gyroP = 0;
	private static final double gyroI = 0;
	private static final double gyroD = 0;

	public PIDController drivePID;
	private TwoEncoderPIDSource driveIn;
	private SimplePIDOutput driveOut;
	private static final double driveP = 0.1;
	private static final double driveI = 0.1;
	private static final double driveD = 0.1;

	private PIDController cameraPID;
	// private CameraPIDSource cameraIn;
	private SimplePIDOutput cameraOut;
	private static final double cameraP = 0;
	private static final double cameraI = 0;
	private static final double cameraD = 0;

	public static final double MAX_AUTO_SPEED = 0.75;
	private static final double WHEEL_DIAMETER = 6; // inches
	private static final double EC_PER_REV_LEFT = 557.7238;
	private static final double EC_PER_REV_RIGHT = 803.493;

	public Drivetrain() {

		masterLeft = new CANTalon(RobotMap.talonDriveMasterLeft);
		masterRight = new CANTalon(RobotMap.talonDriveMasterRight);
		fLeft = new CANTalon(RobotMap.talonDriveFrontLeft);
		fRight = new CANTalon(RobotMap.talonDriveFrontRight);
		bLeft = new CANTalon(RobotMap.talonDriveBackLeft);
		bRight = new CANTalon(RobotMap.talonDriveBackRight);

		leftEncoder = new Encoder(RobotMap.encoderDriveLA, RobotMap.encoderDriveLB);
		rightEncoder = new Encoder(RobotMap.encoderDriveRA, RobotMap.encoderDriveRB);
		leftEncoder.setPIDSourceType(PIDSourceType.kDisplacement);
		rightEncoder.setPIDSourceType(PIDSourceType.kDisplacement);

		fLeft.changeControlMode(TalonControlMode.Follower);
		fRight.changeControlMode(TalonControlMode.Follower);
		bLeft.changeControlMode(TalonControlMode.Follower);
		bRight.changeControlMode(TalonControlMode.Follower);
		fLeft.set(RobotMap.talonDriveMasterLeft);
		fRight.set(RobotMap.talonDriveMasterRight);
		bLeft.set(RobotMap.talonDriveMasterLeft);
		bRight.set(RobotMap.talonDriveMasterRight);

		masterLeft.setInverted(true);
		masterRight.setInverted(true);

		robotDrive = new RobotDrive(masterLeft, masterRight);
		robotDrive.setSafetyEnabled(true);
		robotDrive.setExpiration(0.2);

		navX = new AHRS(SPI.Port.kMXP);
		navX.setPIDSourceType(PIDSourceType.kDisplacement);
		// gyroPID = new PIDController(gyroP, gyroI, gyroD, gyroSource,
		// gyroOut);
		// gyroPID.setContinuous();
		// gyroPID.setInputRange(-180, 180);

		driveIn = new TwoEncoderPIDSource(leftEncoder, rightEncoder);
		drivePID = new PIDController(driveP, driveI, driveD, driveIn, new PIDOutput() {
			@Override
			public void pidWrite(double arg0) {

			}
		});

		drivePID.setOutputRange(-1, 1);

		// cameraIn = new CameraPIDSource();
		// cameraPID = new PIDController(cameraP, cameraI, cameraD, cameraIn,
		// cameraOut);
		// cameraPID.setInputRange(-100, 100);

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

	public void setCameraPIDSpeed(double speed) {
		double output = Math.min(speed, MAX_AUTO_SPEED);
		cameraPID.setOutputRange(-output, output);
	}

	public void setDrivePIDSetPoint(double inches) {
		drivePID.setSetpoint(inches);
	}

	public void setGyroPIDSetPoint(double angle) {
		gyroPID.setSetpoint(angle);
	}

	public void setCameraPIDSetPoint(double error) {
		cameraPID.setSetpoint(error);
	}

	public double getDrivePIDOutput() {
		return drivePID.get();
	}

	public double getGyroPIDOutput() {
		return gyroOut.get();
	}

	public double getCameraPIDOutput() {
		return cameraOut.get();
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

	public void enableCameraPID(boolean enable) {
		if (enable)
			cameraPID.enable();
		else
			cameraPID.disable();
	}

	public void resetEncoders() {
		leftEncoder.reset();
		rightEncoder.reset();
	}

	public void resetGyro() {
		navX.reset();
	}

	public double getAngle() {
		return navX.getYaw();
	}

	public double getLeftDistanceInches() {
		return encoderCntsToInches(leftEncoder.getDistance(), EC_PER_REV_LEFT);
	}

	public double getRightDistanceInches() {
		return encoderCntsToInches(rightEncoder.getDistance(), EC_PER_REV_RIGHT);
	}

	public static double encoderCntsToInches(double counts, double countsPerRev) {
		return (counts / countsPerRev) * (WHEEL_DIAMETER * Math.PI);
	}

	public static double inchesToEncoderCnts(double inches, double countsPerRev) {
		return inches * countsPerRev / (WHEEL_DIAMETER * Math.PI);
	}

	public void toSmartDashboard() {
		SmartDashboard.putNumber("left_position", getLeftDistanceInches());
		SmartDashboard.putNumber("right_position", getRightDistanceInches());
		SmartDashboard.putNumber("left_raw", leftEncoder.getDistance());
		SmartDashboard.putNumber("right_raw", rightEncoder.getDistance());
		SmartDashboard.putNumber("left_velocity_raw", leftEncoder.getRate());
		SmartDashboard.putNumber("right_velocity_raw", rightEncoder.getRate());
		// SmartDashboard.putNumber("drive_error", drivePID.getError());
		SmartDashboard.putNumber("gyro_angle", navX.getYaw());
		// SmartDashboard.putNumber("heading_error", gyroPID.getError());
		SmartDashboard.putBoolean("drivePID_enable", drivePID.isEnabled());
		SmartDashboard.putNumber("drivePID_out", drivePID.get());
		SmartDashboard.putNumber("driveIn", driveIn.pidGet());
		// SmartDashboard.putBoolean("gyroPID_enable", gyroPID.isEnabled());
		// SmartDashboard.putBoolean("cameraPID_enable", cameraPID.isEnabled());
	}

}
