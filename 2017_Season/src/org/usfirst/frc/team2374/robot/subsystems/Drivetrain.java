package org.usfirst.frc.team2374.robot.subsystems;

import org.usfirst.frc.team2374.robot.Robot;
import org.usfirst.frc.team2374.robot.RobotMap;
import org.usfirst.frc.team2374.robot.commands.drivetrain.DriveWithJoystick;
import org.usfirst.frc.team2374.util.TwoEncoderPIDSource;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drivetrain extends Subsystem {

	private CANTalon masterLeft, masterRight, fLeft, fRight, bLeft, bRight;
	private RobotDrive robotDrive;
	private Encoder leftEncoder;
	private Encoder rightEncoder;
	private AHRS navX;

	private TwoEncoderPIDSource driveIn;
	private PIDController drivePID;

	private PIDController gyroPID;

	private static double GYRO_PS;
	private static double GYRO_IS;
	private static double GYRO_DS;

	private static double GYRO_PL;
	private static double GYRO_IL;
	private static double GYRO_DL;

	private static double GYRO_PT;
	private static double GYRO_IT;
	private static double GYRO_DT;
	
	private static double DRIVE_PS;
	private static double DRIVE_IS;
	private static double DRIVE_DS;

	private static double DRIVE_PL;
	private static double DRIVE_IL;
	private static double DRIVE_DL;

	private static double DRIVE_PV;
	private static double DRIVE_IV;
	private static double DRIVE_DV;

	private static final double WHEEL_DIAMETER_INCHES = 6;
	private static final double EC_PER_REV_LEFT = 359.08;
	private static final double EC_PER_REV_RIGHT = 358.98;

	private static final double MAX_AUTO_SPEED = 0.75;

	public Drivetrain() {

		masterLeft = new CANTalon(RobotMap.TALON_DRIVE_MASTER_LEFT);
		masterRight = new CANTalon(RobotMap.TALON_DRIVE_MASTER_RIGHT);
		fLeft = new CANTalon(RobotMap.TALON_DRIVE_FRONT_LEFT);
		fRight = new CANTalon(RobotMap.TALON_DRIVE_FRONT_RIGHT);
		bLeft = new CANTalon(RobotMap.TALON_DRIVE_BACK_LEFT);
		bRight = new CANTalon(RobotMap.TALON_DRIVE_BACK_RIGHT);

		leftEncoder = new Encoder(RobotMap.ENCODER_DRIVE_LA, RobotMap.ENCODER_DRIVE_LB);
		rightEncoder = new Encoder(RobotMap.ENCODER_DRIVE_RA, RobotMap.ENCODER_DRIVE_RB);
		leftEncoder.setPIDSourceType(PIDSourceType.kDisplacement);
		rightEncoder.setPIDSourceType(PIDSourceType.kDisplacement);

		fLeft.changeControlMode(TalonControlMode.Follower);
		fRight.changeControlMode(TalonControlMode.Follower);
		bLeft.changeControlMode(TalonControlMode.Follower);
		bRight.changeControlMode(TalonControlMode.Follower);
		fLeft.set(RobotMap.TALON_DRIVE_MASTER_LEFT);
		fRight.set(RobotMap.TALON_DRIVE_MASTER_RIGHT);
		bLeft.set(RobotMap.TALON_DRIVE_MASTER_LEFT);
		bRight.set(RobotMap.TALON_DRIVE_MASTER_RIGHT);

		masterLeft.setInverted(true);
		masterRight.setInverted(true);

		robotDrive = new RobotDrive(masterLeft, masterRight);
		robotDrive.setSafetyEnabled(false);

		navX = new AHRS(SPI.Port.kMXP);
		navX.setPIDSourceType(PIDSourceType.kDisplacement);
		gyroPID = new PIDController(GYRO_PL, GYRO_IL, GYRO_DL, navX, new PIDOutput() {
			@Override
			public void pidWrite(double arg0) {
			}
		});
		gyroPID.setInputRange(-180.0, 180.0);
		gyroPID.setOutputRange(-MAX_AUTO_SPEED, MAX_AUTO_SPEED);
		gyroPID.setContinuous(true);

		driveIn = new TwoEncoderPIDSource(leftEncoder, rightEncoder);
		drivePID = new PIDController(DRIVE_PL, DRIVE_IL, DRIVE_DL, driveIn, new PIDOutput() {
			@Override
			public void pidWrite(double arg0) {
			}
		});
		drivePID.setOutputRange(-MAX_AUTO_SPEED, MAX_AUTO_SPEED);
		drivePID.setContinuous(false);
	}

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new DriveWithJoystick());
	}

	public void setShortPID() {
		drivePID.setPID(DRIVE_PS, DRIVE_IS, DRIVE_DS);
		gyroPID.setPID(GYRO_PS, GYRO_IS, GYRO_DS);
	}

	public void setLongPID() {
		drivePID.setPID(DRIVE_PL, DRIVE_IL, DRIVE_DL);
		gyroPID.setPID(GYRO_PL, GYRO_IL, GYRO_DL);
	}

	public void setTurnPID() {
		gyroPID.setPID(GYRO_PT, GYRO_IT, GYRO_DT);
	}

	public void setViolentPID() {
		drivePID.setPID(DRIVE_PV, DRIVE_IV, DRIVE_DV);
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
	
	public double getDrivePIDError() {
		return drivePID.getError();
	}

	public double getGyroPIDOutput() {
		return gyroPID.get();
	}
	
	public double getGyroPIDError() {
		return gyroPID.getError();
	}

	public void enableDrivePID(boolean enable) {
		if (enable)
			drivePID.enable();
		else
			drivePID.reset();
	}

	public void enableGyroPID(boolean enable) {
		if (enable)
			gyroPID.enable();
		else
			gyroPID.reset();
	}

	public void resetEncoders(boolean waitToReset) {
		leftEncoder.reset();
		rightEncoder.reset();
		double startTime = Timer.getFPGATimestamp();
		while (waitToReset
				&& (Math.abs(leftEncoder.getDistance()) > 500 || Math.abs(rightEncoder.getDistance()) > 500)) {
			if (Timer.getFPGATimestamp() - startTime > 0.250) {
				DriverStation.reportWarning("Encoder didn't reset", true);
				break;
			}
		}
	}

	public void resetGyro(boolean waitToReset) {
		navX.reset();
		double startTime = Timer.getFPGATimestamp();
		while (waitToReset && Math.abs(navX.getYaw()) > 5) {
			if (Timer.getFPGATimestamp() - startTime > 0.250) {
				DriverStation.reportWarning("Gyro didn't reset", true);
				break;
			}
		}
	}

	public void resetAllSenors(boolean waitToReset) {
		leftEncoder.reset();
		rightEncoder.reset();
		navX.reset();
		double startTime = Timer.getFPGATimestamp();
		while (waitToReset && (Math.abs(leftEncoder.getDistance()) > 500 || Math.abs(rightEncoder.getDistance()) > 500
				|| Math.abs(navX.getYaw()) > 5)) {
			if (Timer.getFPGATimestamp() - startTime > 0.250) {
				DriverStation.reportWarning("A senors didn't reset", true);
				break;
			}
		}
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
		return (counts / countsPerRev) * (WHEEL_DIAMETER_INCHES * Math.PI);
	}

	public static double inchesToEncoderCnts(double inches, double countsPerRev) {
		return inches * countsPerRev / (WHEEL_DIAMETER_INCHES * Math.PI);
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
	
	public void updatePreferences() {
		GYRO_PS = Robot.prefs.getDouble("GYRO_PS", 0.07);
		GYRO_IS = Robot.prefs.getDouble("GYRO_IS", 0.0002);
		GYRO_DS = Robot.prefs.getDouble("GYRO_DS", 0.001);
		GYRO_PL = Robot.prefs.getDouble("GYRO_PL", 0.07);
		GYRO_IL = Robot.prefs.getDouble("GYRO_IL", 0.001);
		GYRO_DL = Robot.prefs.getDouble("GYRO_DL", 0.001);
		GYRO_PT = Robot.prefs.getDouble("GYRO_PT", 0.008);
		GYRO_IT = Robot.prefs.getDouble("GYRO_IT", 0.00045);
		GYRO_DT = Robot.prefs.getDouble("GYRO_DT", 0);
		DRIVE_PS = Robot.prefs.getDouble("DRIVE_PS", 0.07);
		DRIVE_IS = Robot.prefs.getDouble("DRIVE_IS", 0.0001);
		DRIVE_DS = Robot.prefs.getDouble("DRIVE_DS", 0);
		DRIVE_PL = Robot.prefs.getDouble("DRIVE_PL", 0.003);
		DRIVE_IL = Robot.prefs.getDouble("DRIVE_IL", 0.001);
		DRIVE_DL = Robot.prefs.getDouble("DRIVE_DL", 0);
		DRIVE_PV = Robot.prefs.getDouble("DRIVE_PV", 1.0);
		DRIVE_IV = Robot.prefs.getDouble("DRIVE_IV", 0);
		DRIVE_DV = Robot.prefs.getDouble("DRIVE_DV", 0);
		
		Robot.prefs.putDouble("GYRO_PS", GYRO_PS);
		Robot.prefs.putDouble("GYRO_IS", GYRO_IS);
		Robot.prefs.putDouble("GYRO_DS", GYRO_DS);
		Robot.prefs.putDouble("GYRO_PL", GYRO_PL);
		Robot.prefs.putDouble("GYRO_IL", GYRO_IL);
		Robot.prefs.putDouble("GYRO_DL", GYRO_DL);
		Robot.prefs.putDouble("GYRO_PT", GYRO_PT);
		Robot.prefs.putDouble("GYRO_IT", GYRO_IT);
		Robot.prefs.putDouble("GYRO_DT", GYRO_DT);
		Robot.prefs.putDouble("DRIVE_PS", DRIVE_PS);
		Robot.prefs.putDouble("DRIVE_IS", DRIVE_IS);
		Robot.prefs.putDouble("DRIVE_DS", DRIVE_DS);
		Robot.prefs.putDouble("DRIVE_PL", DRIVE_PL);
		Robot.prefs.putDouble("DRIVE_IL", DRIVE_IL);
		Robot.prefs.putDouble("DRIVE_DL", DRIVE_DL);
		Robot.prefs.putDouble("DRIVE_PV", DRIVE_PV);
		Robot.prefs.putDouble("DRIVE_IV", DRIVE_IV);
		Robot.prefs.putDouble("DRIVE_DV", DRIVE_DV);
	}

}
