package org.usfirst.frc.team2374.robot.commands.auto;

import org.usfirst.frc.team2374.robot.Robot;
import org.usfirst.frc.team2374.robot.commands.belt.CenterBeltOnTarget;
import org.usfirst.frc.team2374.robot.commands.belt.MoveBeltToOffset;
import org.usfirst.frc.team2374.robot.commands.belt.MoveBeltToPoint;
import org.usfirst.frc.team2374.robot.commands.drivetrain.DriveToInch;
import org.usfirst.frc.team2374.robot.commands.drivetrain.DriveToInch.DriveToType;
import org.usfirst.frc.team2374.robot.commands.drivetrain.DriveToTarget;
import org.usfirst.frc.team2374.robot.commands.drivetrain.TurnToDegree;
import org.usfirst.frc.team2374.robot.commands.grabber.CloseGrabber;
import org.usfirst.frc.team2374.robot.commands.grabber.OpenGrabber;
import org.usfirst.frc.team2374.robot.subsystems.Belt;
import org.usfirst.frc.team2374.robot.subsystems.Drivetrain;
import org.usfirst.frc.team2374.robot.subsystems.Grabber;
import org.usfirst.frc.team2374.robot.subsystems.Vision;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class Right extends CommandGroup {

	private Drivetrain drive = Robot.drivetrain;
	private Belt belt = Robot.belt;
	private Vision camera = Robot.camera;
	private Grabber grabber = Robot.grabber;

	public Right() {

		requires(drive);
		requires(belt);
		requires(camera);
		requires(grabber);

		addSequential(new DriveToInch(82 - 16, DriveToType.LONG, 3));
		addSequential(new TurnToDegree(-60, 2.0));
		addSequential(new DriveToTarget(30));
		addSequential(new CenterBeltOnTarget());
		addSequential(new DriveToInch(21.0, DriveToType.SHORT));
		addSequential(new OpenGrabber(0.8));
		addSequential(new MoveBeltToOffset());
		addSequential(new DriveToInch(6, DriveToType.VIOLENT));
		addSequential(new DriveToInch(-22, DriveToType.SHORT));
		addParallel(new CloseGrabber());
		addSequential(new MoveBeltToPoint(0));
	}
}
