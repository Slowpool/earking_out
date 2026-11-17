package org.swetlokognatsk.earking_out.core.domain.model.achievements;

public enum AchievementTypes {
    CATALOG, // just list of achievements as a concept, e.g. [finish_first_puzzle_achievement, reach_100_percents_accuracy_achievement]
    LOCKED, // the achievements the specific user have not got yet
    UNLOCKED, // the achievements the specific user already has unlocked
}
