package quests;

import l2p.commons.util.Rnd;
import l2p.gameserver.model.instances.NpcInstance;
import l2p.gameserver.model.quest.Quest;
import l2p.gameserver.model.quest.QuestState;
import l2p.gameserver.scripts.Functions;
import l2p.gameserver.scripts.ScriptFile;
import l2p.gameserver.utils.Location;

import java.util.ArrayList;
import java.util.List;

public class _022_TragedyInVonHellmannForest extends Quest implements ScriptFile {
    // Npc list
    public final int Well = 31527;
    public final int Tifaren = 31334;
    public final int Innocentin = 31328;
    public final int SoulOfWell = 27217;
    public final int GhostOfPriest = 31528;
    public final int GhostOfAdventurer = 31529;
    // ~~~~~~~~ Item list ~~~~~~~~
    public final int ReportBox = 7147;
    public final int LostSkullOfElf = 7142;
    public final int CrossOfEinhasad = 7141;
    public final int SealedReportBox = 7146;
    public final int LetterOfInnocentin = 7143;
    public final int JewelOfAdventurerRed = 7145;
    public final int JewelOfAdventurerGreen = 7144;
    // ~~~~ Monster list: ~~~~
    private static final List<Integer> Mobs = new ArrayList<Integer>();

    static {
        for (int i : new int[]{
                21547,
                21548,
                21549,
                21550,
                21551,
                21552,
                21553,
                21554,
                21555,
                21556,
                21557,
                21558,
                21559,
                21560,
                21561,
                21562,
                21563,
                21564,
                21565,
                21566,
                21567,
                21568,
                21569,
                21570,
                21571,
                21572,
                21573,
                21574,
                21575,
                21576,
                21577,
                21578})
            Mobs.add(i);
    }

    private static NpcInstance GhostOfPriestInstance = null;
    private static NpcInstance SoulOfWellInstance = null;

    @Override
    public void onLoad() {
    }

    @Override
    public void onReload() {
    }

    @Override
    public void onShutdown() {
    }

    public _022_TragedyInVonHellmannForest() {
        super(false);

        addStartNpc(Tifaren);

        addTalkId(Tifaren);
        addTalkId(GhostOfPriest);
        addTalkId(Innocentin);
        addTalkId(GhostOfAdventurer);
        addTalkId(Well);

        addKillId(SoulOfWell);

        for (int npcId = 21547; npcId <= 21578; npcId++)
            addKillId(npcId);

        addQuestItem(LostSkullOfElf);
    }

    private void spawnGhostOfPriest(QuestState st) {
        GhostOfPriestInstance = Functions.spawn(Location.findPointToStay(st.getPlayer(), 50, 100), GhostOfPriest);
    }

    private void spawnSoulOfWell(QuestState st) {
        SoulOfWellInstance = Functions.spawn(Location.findPointToStay(st.getPlayer(), 50, 100), SoulOfWell);
    }

    private void despawnGhostOfPriest() {
        if (GhostOfPriestInstance != null)
            GhostOfPriestInstance.deleteMe();
    }

    private void despawnSoulOfWell() {
        if (SoulOfWellInstance != null)
            SoulOfWellInstance.deleteMe();
    }

    @Override
    public String onEvent(String event, QuestState st, NpcInstance npc) {
        String htmltext = event;
        if (event.equalsIgnoreCase("31334-03.htm")) {
            st.setState(STARTED);
            st.setCond(3);
            st.takeItems(CrossOfEinhasad, -1);
        } else if (event.equalsIgnoreCase("31334-06.htm"))
            st.setCond(4);
        else if (event.equalsIgnoreCase("31334-09.htm")) {
            st.setCond(6);
            st.takeItems(LostSkullOfElf, 1);
            despawnGhostOfPriest();
            spawnGhostOfPriest(st);
        } else if (event.equalsIgnoreCase("31528-07.htm")) {
            despawnGhostOfPriest();
            st.setCond(7);
        } else if (event.equalsIgnoreCase("31328-06.htm")) {
            st.setCond(8);
            st.giveItems(LetterOfInnocentin, 1);
        } else if (event.equalsIgnoreCase("31529-09.htm")) {
            st.setCond(9);
            st.takeItems(LetterOfInnocentin, 1);
        } else if (event.equalsIgnoreCase("explore")) {
            despawnSoulOfWell();
            spawnSoulOfWell(st);
            st.setCond(10);
            st.giveItems(JewelOfAdventurerGreen, 1);
            htmltext = "<html><body>Attack Soul of Well but do not kill while stone will not change colour...</body></html>";
        } else if (event.equalsIgnoreCase("attack_timer")) {
            despawnSoulOfWell();
            st.giveItems(JewelOfAdventurerRed, 1);
            st.takeItems(JewelOfAdventurerGreen, -1);
            st.setCond(11);
        } else if (event.equalsIgnoreCase("31328-08.htm")) {
            st.startQuestTimer("wait_timer", 600000);
            st.setCond(15);
            st.takeItems(ReportBox, 1);
        } else if (event.equalsIgnoreCase("wait_timer")) {
            st.setCond(16);
            htmltext = "<html><body>Innocentin wants with you to speak...</body></html>";
        } else if (event.equalsIgnoreCase("31328-16.htm")) {
            st.startQuestTimer("next_wait_timer", 300000);
            st.setCond(17);
        } else if (event.equalsIgnoreCase("next_wait_timer"))
            st.setCond(18);
        return htmltext;
    }

    @Override
    public String onTalk(NpcInstance npc, QuestState st) {
        int npcId = npc.getNpcId();
        int cond = st.getCond();
        String htmltext = "noquest";
        if (npcId == Tifaren) {
            if (cond == 0) {
                QuestState hiddenTruth = st.getPlayer().getQuestState(_021_HiddenTruth.class);
                if (hiddenTruth != null) {
                    if (hiddenTruth.isCompleted())
                        htmltext = "31334-01.htm";
                    else
                        htmltext = "<html><head><body>You not complite quest Hidden Truth...</body></html>";
                } else
                    htmltext = "<html><head><body>You not complite quest Hidden Truth...</body></html>";
            } else if (cond == 3)
                return "31334-04.htm";
            else if (cond == 4)
                htmltext = "31334-06.htm";
            else if (cond == 5) {
                if (st.getQuestItemsCount(LostSkullOfElf) != 0)
                    htmltext = "31334-07.htm";
                else {
                    st.setCond(4);
                    htmltext = "31334-06.htm";
                }
            } else if (cond == 6) {
                despawnGhostOfPriest();
                spawnGhostOfPriest(st);
                htmltext = "31334-09.htm";
            }
        } else if (npcId == GhostOfPriest) {
            if (cond == 6)
                htmltext = "31528-00.htm";
            else if (cond == 7)
                htmltext = "31528-07.htm";
        } else if (npcId == Innocentin) {
            if (cond == 0)
                htmltext = "31328-17.htm";
            if (cond == 7)
                htmltext = "31328-00.htm";
            else if (cond == 8)
                htmltext = "31328-06.htm";
            else if (cond == 14) {
                if (st.getQuestItemsCount(ReportBox) != 0)
                    htmltext = "31328-07.htm";
                else {
                    st.setCond(13);
                    htmltext = "Go away!";
                }
            } else if (cond == 15) {
                if (!st.isRunningQuestTimer("wait_timer"))
                    st.setCond(16);
                htmltext = "31328-09.htm";
            } else if (cond == 16)
                htmltext = "31328-08a.htm";
            else if (cond == 17) {
                if (!st.isRunningQuestTimer("next_wait_timer"))
                    st.setCond(18);
                htmltext = "31328-16a.htm";
            } else if (cond == 18) {
                htmltext = "31328-17.htm";
                st.addExpAndSp(8249480, 7930280);
                st.exitCurrentQuest(false);
            }
        } else if (npcId == GhostOfAdventurer) {
            if (cond == 8) {
                if (st.getQuestItemsCount(LetterOfInnocentin) != 0)
                    htmltext = "31529-00.htm";
                else
                    htmltext = "You have no Letter of Innocentin! Are they Please returned to High Priest Innocentin...";
            } else if (cond == 9)
                htmltext = "31529-09.htm";
            else if (cond == 11) {
                if (st.getQuestItemsCount(JewelOfAdventurerRed) != 0) {
                    htmltext = "31529-10.htm";
                    st.takeItems(JewelOfAdventurerRed, 1);
                    st.setCond(12);
                } else {
                    st.setCond(9);
                    htmltext = "31529-09.htm";
                }
            } else if (cond == 13)
                if (st.getQuestItemsCount(SealedReportBox) != 0) {
                    htmltext = "31529-11.htm";
                    st.setCond(14);
                    st.takeItems(SealedReportBox, 1);
                    st.giveItems(ReportBox, 1);
                } else {
                    st.setCond(12);
                    htmltext = "31529-10.htm";
                }
        } else if (npcId == Well)
            if (cond == 9)
                htmltext = "31527-00.htm";
            else if (cond == 10) {
                despawnSoulOfWell();
                spawnSoulOfWell(st);
                st.setCond(10);
                st.startQuestTimer("attack_timer", 300000);
                st.takeItems(JewelOfAdventurerGreen, -1);
                st.takeItems(JewelOfAdventurerRed, -1);
                st.giveItems(JewelOfAdventurerGreen, 1);
                htmltext = "<html><body>Attack Soul of Well but do not kill while stone will not change colour...</body></html>";
            } else if (cond == 12) {
                htmltext = "31527-01.htm";
                st.setCond(13);
                st.giveItems(SealedReportBox, 1);
            }
        return htmltext;
    }

    @Override
    public String onKill(NpcInstance npc, QuestState st) {
        int npcId = npc.getNpcId();
        int cond = st.getCond();
        if (Mobs.contains(npcId))
            if (cond == 4 && Rnd.chance(99)) {
                st.giveItems(LostSkullOfElf, 1);
                st.playSound(SOUND_MIDDLE);
                st.setCond(5);
            }
        if (npcId == SoulOfWell)
            if (cond == 10) {
                st.setCond(9);
                st.takeItems(JewelOfAdventurerGreen, -1);
                st.takeItems(JewelOfAdventurerRed, -1);
                st.cancelQuestTimer("attack_timer");
            }
        return null;
    }
}