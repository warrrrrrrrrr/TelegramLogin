package it.ivirus.telegramlogin.telegram;

public abstract class Button {

    public abstract void onClickButton(String sender, String command, String[] args);

    public abstract void onClickButton(String sender, String command);
}
