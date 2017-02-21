package org.usfirst.frc.team2374.robot.subsystems;

import org.usfirst.frc.team2374.robot.RobotMap;
import org.usfirst.frc.team2374.robot.commands.drivetrain.DriveWithJoystick;
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

// TODO: (Code review) AmericanoArchive
public class Drivetrain extends Subsystem {

	private CANTalon masterLeft, masterRight, fLeft, fRight, bLeft, bRight;
	private RobotDrive robotDrive;
	private Encoder leftEncoder;
	private Encoder rightEncoder;
	private AHRS navX;

	private PIDController gyroPID;
	private static final double gyroPS = 0.07;
	private static final double gyroIS = 0.0002;
	private static final double gyroDS = 0.001;

	private static final double gyroPL = 0.07;
	private static final double gyroIL = 0.001;
	private static final double gyroDL = 0.001;

	private static final double gyroPT = 0.007;
	private static final double gyroIT = 0.00045;
	private static final double gyroDT = 0.003;

	// TODO: (Code review) Move non-constants either before or after constant declarations
	public PIDController drivePID;
	private TwoEncoderPIDSource driveIn;
	private static final double drivePS = 0.1;
	private static final double driveIS = 0.0001;
	private static final double driveDS = 0;

	private static final double drivePL = 0.017;
	private static final double driveIL = 0.0005;
	private static final double driveDL = 0;

	public static final double MAX_AUTO_SPEED = 0.75;
	private static final double WHEEL_DIAMETER = 6; // inches
	private static final double EC_PER_REV_LEFT = 352.25;
	private static final double EC_PER_REV_RIGHT = 358.98;

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
		// TODO: (Code review) Uhhhhhh... Seems unsafe
		robotDrive.setSafetyEnabled(false);
		// robotDrive.setExpiration(0.3);

		navX = new AHRS(SPI.Port.kMXP);
		navX.setPIDSourceType(PIDSourceType.kDisplacement);
		gyroPID = new PIDController(gyroPL, gyroIL, gyroDL, navX, new PIDOutput() {
			@Override
			public void pidWrite(double arg0) {
			}
		});
		gyroPID.setInputRange(-180.0, 180.0);
		gyroPID.setOutputRange(-MAX_AUTO_SPEED, MAX_AUTO_SPEED);
		gyroPID.setContinuous(true);

		driveIn = new TwoEncoderPIDSource(leftEncoder, rightEncoder);
		drivePID = new PIDController(drivePL, driveIL, driveDL, driveIn, new PIDOutput() {
			@Override
			public void pidWrite(double arg0) {
			}
		});
		drivePID.setOutputRange(-MAX_AUTO_SPEED, MAX_AUTO_SPEED);
		drivePID.setContinuous(false);

		/*
		 * cameraIn = new CameraPIDSource(); cameraPID = new
		 * PIDController(cameraP, cameraI, cameraD, cameraIn, new PIDOutput() {
		 * 
		 * @Override public void pidWrite(double arg0) { } });
		 * cameraPID.setInputRange(-100.0, 100.0);
		 * cameraPID.setOutputRange(-MAX_AUTO_SPEED, MAX_AUTO_SPEED);
		 */
	}

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new DriveWithJoystick());
		// setDefaultCommand(new DrivetrainPID());
	}

	public void setShortPID() {
		drivePID.setPID(drivePS, driveIS, driveDS);
		gyroPID.setPID(gyroPS, gyroIS, gyroDS);
	}

	public void setLongPID() {
		drivePID.setPID(drivePL, driveIL, driveDL);
		gyroPID.setPID(gyroPL, gyroIL, gyroDL);
	}

	public void setTurnPID() {
		gyroPID.setPID(gyroPT, gyroIT, gyroDT);
	}

	public void tankDrive(double left, double right) {
		robotDrive.tankDrive(left, right);
	}

	public void arcadeDrive(double move, double rotate) {
		robotDrive.arcadeDrive(-move, rotate);
	}

	public void setDrivePIDSetPoint(double inches) {
		drivePID.setSetpoint(inches);
	}

	public void setGyroPIDSetPoint(double angle) {
		gyroPID.setSetpoint(angle);
	}

	public double getDrivePIDOutput() {
		return drivePID.get();
	}

	public double getGyroPIDOutput() {
		return gyroPID.get();
	}

	public void enableDrivePID(boolean enable) {
		// TODO: (Code review) Can be made one line with:
		// enable ? drivePID.enable() : drivePID.reset();
		if (enable)
			drivePID.enable();
		else
			drivePID.reset();
	}

	public void enableGyroPID(boolean enable) {
		// TODO: (Code review) Same comment as enableDrivePID
		if (enable)
			gyroPID.enable();
		else
			gyroPID.reset();
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
		SmartDashboard.putNumber("gyro_angle", navX.getYaw());
		SmartDashboard.putNumber("heading_error", gyroPID.getError());
		SmartDashboard.putNumber("gyro_get", gyroPID.get());
		SmartDashboard.putBoolean("gyroPID_enable", gyroPID.isEnabled());
		SmartDashboard.putBoolean("drivePID_enable", drivePID.isEnabled());
		SmartDashboard.putNumber("drive_error", drivePID.getError());
		SmartDashboard.putNumber("drivePID_out", drivePID.get());
	}

}
