package Network;

import Network.Messages.CellMessage;
import Network.Messages.EntityMessage;
import Network.Messages.GameStateMessage;
import Network.Messages.MapMessage;

public interface MessageListener {
    void onCellMessage(CellMessage message);
    void onEntityMessage(EntityMessage message);
    void onMapMessage(MapMessage message);
    void onGameStateMessage(GameStateMessage message);
}
