
package org.usfirst.frc.team2374.robot;

import org.usfirst.frc.team2374.robot.commands.belt.CenterBelt;
import org.usfirst.frc.team2374.robot.commands.drivetrain.DriveToInchLong;
import org.usfirst.frc.team2374.robot.commands.drivetrain.DriveToInchShort;
import org.usfirst.frc.team2374.robot.commands.drivetrain.DriveToTarget;
import org.usfirst.frc.team2374.robot.commands.drivetrain.TurnToDegree;
import org.usfirst.frc.team2374.robot.subsystems.Belt;
import org.usfirst.frc.team2374.robot.subsystems.Climber;
import org.usfirst.frc.team2374.robot.subsystems.Drivetrain;
import org.usfirst.frc.team2374.robot.subsystems.Grabber;
import org.usfirst.frc.team2374.robot.subsystems.Vision;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static Drivetrain drivetrain;
	public static Belt belt;
	public static Grabber grabber;
	public static OI oi;
	public static Vision camera;
	public static Climber climber;

	Command autonomousCommand;
	SendableChooser<Command> chooser = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		drivetrain = new Drivetrain();
		belt = new Belt();
		grabber = new Grabber();
		oi = new OI();
		camera = new Vision();
		climber = new Climber();
		chooser.addDefault("Default Auto", new CenterBelt()); // this must
		chooser.addObject("TurnToAngle-60", new TurnToDegree(-60));
		chooser.addObject("DriveToTarget", new DriveToTarget(30));
		chooser.addObject("DriveToInch20", new DriveToInchShort(20));
		chooser.addObject("DriveToInch77", new DriveToInchLong(77));
		// be named
		// "Default
		// Auto"
		SmartDashboard.putData("Auto mode", chooser);
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		autonomousCommand = chooser.getSelected();

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		if (autonomousCommand != null)
			autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		oi.toSmartDashboard();
		drivetrain.toSmartDashboard();
		belt.toSmartDashboard();
		grabber.toSmartDashboard();
		camera.toSmartDashboard();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null)
			autonomousCommand.cancel();
		Robot.drivetrain.resetEncoders();
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		oi.toSmartDashboard();
		drivetrain.toSmartDashboard();
		belt.toSmartDashboard();
		grabber.toSmartDashboard();
		camera.toSmartDashboard();
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
}
