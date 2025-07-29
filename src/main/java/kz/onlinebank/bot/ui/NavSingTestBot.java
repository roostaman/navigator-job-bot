package kz.onlinebank.bot.ui;

import kz.onlinebank.helper.BaseHelper;

public class NavSingTestBot {

    public static void main(String[] args) {
        BaseHelper.setUpDriver();
        BaseHelper.logIn();
        try {
            Thread.sleep(15000);
        } catch (InterruptedException ignored) {}

        BaseHelper.tearDownDriver();
    }
}
