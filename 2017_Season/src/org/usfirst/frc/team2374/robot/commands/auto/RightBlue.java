package org.usfirst.frc.team2374.robot.commands.auto;

import org.usfirst.frc.team2374.robot.Robot;
import org.usfirst.frc.team2374.robot.commands.drivetrain.DriveToInch;
import org.usfirst.frc.team2374.robot.commands.drivetrain.DriveToInch.DriveToType;
import org.usfirst.frc.team2374.robot.commands.drivetrain.DriveToTargetSide;
import org.usfirst.frc.team2374.robot.commands.drivetrain.TurnToDegree;
import org.usfirst.frc.team2374.robot.subsystems.Belt;
import org.usfirst.frc.team2374.robot.subsystems.Drivetrain;
import org.usfirst.frc.team2374.robot.subsystems.Grabber;
import org.usfirst.frc.team2374.robot.subsystems.Vision;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class RightBlue extends CommandGroup {

	private Drivetrain drive = Robot.drivetrain;
	private Belt belt = Robot.belt;
	private Vision camera = Robot.camera;
	private Grabber grabber = Robot.grabber;

	public RightBlue() {

		requires(drive);
		requires(belt);
		requires(camera);
		requires(grabber);

		addSequential(new DriveToInch(AutoConstants.RIGHT_FORWARD_BLUE, DriveToType.LONG));
		addSequential(new TurnToDegree(AutoConstants.RIGHT_TURN, AutoConstants.RIGHT_TURN_TIMEOUT));
		addSequential(new DriveToTargetSide(AutoConstants.AUTO_DRIVE_TO_TAR));
		addSequential(new LoadGearAuto());
	}
}
