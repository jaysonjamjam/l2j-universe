package lineage2.gameserver.network.serverpackets;

public class ExNotifyBirthDay extends L2GameServerPacket
{
	public static final L2GameServerPacket STATIC = new ExNotifyBirthDay();

	@Override
	protected void writeImpl()
	{
		writeEx(0x90);
	}
}