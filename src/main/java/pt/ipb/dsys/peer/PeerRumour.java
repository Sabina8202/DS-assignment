package pt.ipb.dsys.peer;

import org.jgroups.JChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.ipb.dsys.peer.jgroups.DefaultProtocols;
import pt.ipb.dsys.peer.jgroups.PeerUtil;
import pt.ipb.dsys.peer.util.Sleeper;

public class PeerRumour {

    private static final Logger logger = LoggerFactory.getLogger(PeerRumour.class);

    public static final String CLUSTER_NAME = "PeerBox";

    public static final String GOSSIP_HOSTNAME = "localhost"; // see container_name on docker-compose.yml

    public static final int GOSSIP_PORT = 12001;


    public static void main(String[] args) {
        PeerUtil.localhostFix(GOSSIP_HOSTNAME);
        try (JChannel channel = new JChannel(DefaultProtocols.gossipRouter(GOSSIP_HOSTNAME, GOSSIP_PORT))) {
            RumourHandler rumourHandler = new RumourHandler(channel);
            channel
                    // ignore my own messages
                    .setDiscardOwnMessages(true)
                    // receiver must be set first for Receiver.viewAccepted(...) to work properly
                    .setReceiver(rumourHandler)
                    // finaly connect to the cluster
                    .connect(CLUSTER_NAME)
            ;

            while (true)
                Sleeper.sleep(1000);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        System.exit(0);
    }

}
