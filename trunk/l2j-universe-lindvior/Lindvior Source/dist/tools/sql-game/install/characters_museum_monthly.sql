CREATE TABLE IF NOT EXISTS `characters_museum_monthly` (
  `objectID` int(10) unsigned NOT NULL DEFAULT '0',
  `acquiredXP` bigint(20) NOT NULL,
  `acquiredAdena` bigint(20) NOT NULL,
  `onlineTime` bigint(20) NOT NULL,
  `partyDuration` bigint(20) NOT NULL,
  `fullPartyDuration` bigint(20) NOT NULL,
  `privateStoreSales` bigint(20) NOT NULL,
  `numberOfResurrectCast` bigint(20) NOT NULL,
  `numberOfResurrectReceived` bigint(20) NOT NULL,
  `numberOfDeaths` bigint(20) NOT NULL,
  `numberOfMonsterKillings` bigint(20) NOT NULL,
  `monsterKillXp` bigint(20) NOT NULL,
  `pkVictoryCount` bigint(20) NOT NULL,
  `pvpVictoryCount` bigint(20) NOT NULL,
  `pkDefeatCount` bigint(20) NOT NULL,
  `pvpDefeatCount` bigint(20) NOT NULL,
  PRIMARY KEY (`objectID`),
  KEY `char_name` (`acquiredXP`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;