package l2p.gameserver.network.serverpackets;

/**
 * Открывает окно аугмента, название от фонаря.
 */
public class ExShowVariationMakeWindow extends L2GameServerPacket {
    public static final L2GameServerPacket STATIC = new ExShowVariationMakeWindow();

    @Override
    protected final void writeImpl() {
        writeEx449(0x51);
    }
}