package org.usfirst.frc.team2363.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import static org.usfirst.frc.team2363.robot.Robot.drivetrain;

import org.usfirst.frc.team2363.robot.commands.AutoLowBarCommand;
import org.usfirst.frc.team2363.robot.commands.AutoMote;
import org.usfirst.frc.team2363.robot.commands.AutoRockWall;
import org.usfirst.frc.team2363.robot.commands.AutoRoughTerrain;
import org.usfirst.frc.team2363.robot.commands.ExampleCommand;
import org.usfirst.frc.team2363.robot.commands.ShooterCommand;
import org.usfirst.frc.team2363.robot.subsystems.Drivetrain;
import org.usfirst.frc.team2363.robot.subsystems.ExampleSubsystem;
import org.usfirst.frc.team2363.robot.subsystems.Intake;
import org.usfirst.frc.team2363.robot.subsystems.Shooter;

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

	public static final ExampleSubsystem exampleSubsystem = new ExampleSubsystem();
	public static OI oi;
	public static Drivetrain drivetrain;
	public static Shooter shooter;
	public static PowerDistributionPanel pdp;
	public static Intake intake;

    Command autonomousCommand;
    SendableChooser chooser;
    
    public Robot() {
    	drivetrain = new Drivetrain();
    	shooter = new Shooter();
    	intake = new Intake();
    	pdp = new PowerDistributionPanel();
    	chooser = new SendableChooser();
	}
	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
		oi = new OI();
		SmartDashboard.putData(Scheduler.getInstance());
		SmartDashboard.putData(drivetrain);
		SmartDashboard.putData(shooter);
		SmartDashboard.putData(intake);
		SmartDashboard.putData("autonomous", chooser);
		
		chooser.addObject("rough terrain autonomous", new AutoRoughTerrain());
		chooser.addObject("low bar autonomous", new AutoLowBarCommand());
		chooser.addObject("rock wall autonomous", new AutoRockWall());
		chooser.addObject("mote autonomous", new AutoMote());
    }
	
	/**
     * This function is called once each time the robot enters Disabled mode.
     * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
     */
    public void disabledInit(){

    }
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
	 * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
	 * Dashboard, remove all of the chooser code and uncomment the getString code to get the auto name from the text box
	 * below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the chooser code above (like the commented example)
	 * or additional comparisons to the switch structure below with additional strings & commands.
	 */
    public void autonomousInit() {
        autonomousCommand = (Command) chooser.getSelected();
        
		/* String autoSelected = SmartDashboard.getString("Auto Selector", "Default");
		switch(autoSelected) {
		case "My Auto":
			autonomousCommand = new MyAutoCommand();
			break;
		case "Default Auto":
		default:
			autonomousCommand = new ExampleCommand();
			break;
		} */
    	
    	// schedule the autonomous command (example)
        if (autonomousCommand != null) autonomousCommand.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
		// This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) autonomousCommand.cancel();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        SmartDashboard.putNumber("Shooter RPM", shooter.getRPM());
        SmartDashboard.putNumber("Front Left Drive Motor", pdp.getCurrent(0));
        SmartDashboard.putNumber("Front Right Drive Motor", pdp.getCurrent(1));
        SmartDashboard.putNumber("Rear Left Drive Motor", pdp.getCurrent(2));
        SmartDashboard.putNumber("Rear Right Drive Motor", pdp.getCurrent(3));
        
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
}
