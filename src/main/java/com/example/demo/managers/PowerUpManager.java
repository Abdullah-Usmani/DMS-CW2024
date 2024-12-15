//package com.example.demo.managers;
//
//public class PowerUpManager {
//
//    /**
//     * Applies the effect of a power-up to the user's plane.
//     *
//     * @param powerUp The power-up collected.
//     */
//    private void applyPowerUpEffect(PowerUp powerUp) {
//        switch (powerUp.getPowerUpType()) {
//            case HEALTH_PACK -> getUser().setHealth(getUser().getHealth() + Config.HEALTH_PACK_INCREMENT);
//            case FIRE_RATE_BOOST -> boostFireRate();
//        }
//    }
//
//    /**
//     * Temporarily increases the user's firing rate.
//     */
//    private void boostFireRate() {
//        getUser().setGunCooldown(Config.USER_GUN_COOLDOWN / 2);
//        PauseTransition resetFireRate = new PauseTransition(Duration.millis(Config.FIRE_RATE_BOOST_DURATION));
//        resetFireRate.setOnFinished(event -> getUser().setGunCooldown(Config.USER_GUN_COOLDOWN));
//        resetFireRate.play();
//    }
//}
