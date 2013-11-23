/*
 * Copyright Mazaffaka Project (c) 2013.
 */

/*
 * Copyright Murzik Dev Team (c) 2013.
 */

package l2p.gameserver.network.serverpackets.company.MPCC;

import l2p.gameserver.model.Party;
import l2p.gameserver.model.Player;
import l2p.gameserver.network.serverpackets.L2GameServerPacket;

/**
 * ch Sddd
 */
public class ExMPCCPartyInfoUpdate extends L2GameServerPacket {
    private Party _party;
    Player _leader;
    private int _mode, _count;

    /**
     * @param party
     * @param mode  0 = Remove, 1 = Add
     */
    public ExMPCCPartyInfoUpdate(Party party, int mode) {
        _party = party;
        _mode = mode;
        _count = _party.getMemberCount();
        _leader = _party.getPartyLeader();
    }

    @Override
    protected void writeImpl() {
        writeEx(0x5C);
        writeS(_leader.getName());
        writeD(_leader.getObjectId());
        writeD(_count);
        writeD(_mode); // mode 0 = Remove Party, 1 = AddParty, maybe more...
    }
}