/*
 * build as ../gradlew installApp
 * run as ./build/install/DiaRobot/bin/DiaRobot diarobot.Wander base_scan:=/beego/scan cmd_vel:=/beego/velocity
 */

package diarobot;

import org.apache.commons.logging.Log;
import org.ros.message.MessageListener;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.NodeMain;
import org.ros.node.topic.Publisher;
import org.ros.node.topic.Subscriber;

public class Wander extends AbstractNodeMain {

  @Override
  public GraphName getDefaultNodeName() {
    return new GraphName("diarobot/wander");
  }

  @Override
  public void onStart(final ConnectedNode connectedNode) {
    final Log log = connectedNode.getLog();

    final Publisher<geometry_msgs.Twist> publisher =
        connectedNode.newPublisher("cmd_vel", geometry_msgs.Twist._TYPE);

    Subscriber<sensor_msgs.LaserScan> subscriber = 
        connectedNode.newSubscriber("base_scan", sensor_msgs.LaserScan._TYPE);

    subscriber.addMessageListener(new MessageListener<sensor_msgs.LaserScan>() {
      @Override
      public void onNewMessage(sensor_msgs.LaserScan msg) {
        geometry_msgs.Twist cmd = publisher.newMessage();
        /* wander */
        int i, mid = msg.getRanges().length / 2;
        // halt if an object is less than 2m in a 30deg angle
        boolean halt = false;
        for (i = mid - 15; i <= mid + 15; i++) {
          if (msg.getRanges()[i] < 2.0) {
            halt = true;
            break;
          }
        }
        if (halt) {
          double midA = 0, midB = 0;
          // we go to the highest-range side scanned
          for (i = 0; i < mid; i++)
            midA += msg.getRanges()[i];
          for (i = mid; i < msg.getRanges().length; i++)
            midB += msg.getRanges()[i];
          log.info("A:" + midA + ", B:" + midB);
          if (midA > midB) {
            cmd.getAngular().setZ(-1);
          } else {
            cmd.getAngular().setZ(1);
          }
        } else {
          cmd.getLinear().setX(1);
        }
        publisher.publish(cmd);
        /* wander */
      }
    });
  }
}
