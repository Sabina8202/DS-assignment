package pt.ipb.dsys.peerbox;

import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ObjectMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.ipb.dsys.peerbox.jgroups.DefaultProtocols;
import pt.ipb.dsys.peerbox.jgroups.LoggingReceiver;
import pt.ipb.dsys.peerbox.util.Sleeper;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static final String CLUSTER_NAME = "PeerBox";

    public static void main(String[] args) {
        // Bind to 127.0.0.1 if you're having trouble (ex.: multiple NICs by VMs)
//        System.setProperty("jgroups.bind_addr", "127.0.0.1");

        try {
            try (JChannel channel = new JChannel(DefaultProtocols.gossipRouter())) {
                channel.connect(CLUSTER_NAME);
                channel.setReceiver(new LoggingReceiver());

                int cnt = 0;
                while (cnt++ < 10) {
                    Message message = new ObjectMessage(null, String.format("[%d] Hello from %s!", cnt, DnsHelper.getHostName()));
                    channel.send(message);
                    Sleeper.sleep(2000);
                }

                System.exit(0);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }


}
