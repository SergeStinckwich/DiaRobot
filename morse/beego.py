from morse.builder import *

# Append the robot to the scene
robot = Robot('robot.blend')
robot.name = "beego"

# Append an actuator
motion = Actuator('v_omega')
motion.name = "velocity"
motion.translate(z = 0.3)
robot.append(motion)

# Append a IMU sensor
pose = Sensor('imu')
pose.name = "odometry"
pose.translate(x = -0.12, z = 0.46)
robot.append(pose)

# Append a scan laser
scan = Sensor('sick')
scan.name = "scan"
scan.translate(x = 0.03, z = 0.56)
scan.frequency(10)
scan.properties(laser_range = 10)
robot.append(scan)

# Append a camera
camera = Sensor('video_camera')
camera.name = "camera"
camera.translate(x = 0.15, z = 0.582)
robot.append(camera)
camera.properties(cam_width = 128, cam_height = 128, Vertical_Flip = True, capturing = False)

# Configure the middlewares
motion.configure_mw('ros')
pose.configure_mw('ros')
scan.configure_mw('ros')
camera.configure_mw('ros')

robot.translate(x = -1)

# Select the environement
env = Environment('maze.blend')
# Rotate the camera
env.aim_camera([1.0470, 0, 0.7854])
# for autostart
#env.set_auto_start()
# for virtual machine users:
#env.set_viewport(viewport_shade = 'WIREFRAME')

