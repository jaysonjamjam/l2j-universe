package l2p.gameserver.network.serverpackets;

import l2p.gameserver.model.Creature;
import l2p.gameserver.model.GameObject;
import l2p.gameserver.model.Player;

/**
 * <p/>
 * sample  b9 73 5d 30 49 01 00 00 00 00 00
 * <p/>
 * format dhd	(objectid, color, unk)
 * <p/>
 * color 	-xx -> -9 	red<p>
 * -8  -> -6	light-red<p>
 * -5	-> -3	yellow<p>
 * -2	-> 2    white<p>
 * 3	-> 5	green<p>
 * 6	-> 8	light-blue<p>
 * 9	-> xx	blue<p>
 * <p/>
 * usually the color equals the level difference to the selected target
 */
public class MyTargetSelected extends L2GameServerPacket {
    private int _objectId;
    private int _color;

    /**
     * @param objectId objectId of the target
     * @param color    level difference to the target. name color is calculated from that
     */
    public MyTargetSelected(int objectId, int color) {
        _objectId = objectId;
        _color = color;
    }

    public MyTargetSelected(Player player, GameObject target) {
        _objectId = target.getObjectId();
        if (target.isCreature())
            _color = (player.getLevel() - ((Creature) target).getLevel());
        else
            _color = 0;
    }

    @Override
    protected final void writeImpl() {
        writeC(0xb9);
        writeD(_objectId);
        writeH(_color);
        writeD(0x00);
    }
}