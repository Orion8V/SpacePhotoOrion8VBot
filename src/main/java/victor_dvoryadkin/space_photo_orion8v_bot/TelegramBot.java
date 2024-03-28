package victor_dvoryadkin.space_photo_orion8v_bot;

import java.util.ArrayList;
import java.util.List;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

/**
 * SpacePhotoOrion8VBot
 * @version v1.0
 * @author Victor Dvoryadkin
 * 24.03.2024
 */
public class TelegramBot extends TelegramLongPollingBot{
    private final String BOT_NAME;
    private final String BOT_TOKEN;
    private final String URL = "https://api.nasa.gov/planetary/apod?api_key=X84b1b3rkLV2u9k3B6D2uRHyNqGcxdUddztXrDeq";

    public TelegramBot(String BOT_NAME, String BOT_TOKEN) throws TelegramApiException {
        this.BOT_NAME = BOT_NAME;
        this.BOT_TOKEN = BOT_TOKEN;

        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(this);
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            long chatId = update.getMessage().getChatId();
            String answer = update.getMessage().getText();
            String[] separatedAnswer = answer.split(" ");
            String action = separatedAnswer[0];

            switch (action) {
                case "/help":
                case "Помощь":
                    sendMessage("Я бот космоса. Смотри фото космоса сегодняшнего дня.\n" +
                            "Для получения фото космоса сегодняшнего дня введите /start или /photo.\n" +
                            "Для получения фото космоса на определенный день введите /date ГГГГ-ММ-ДД.", chatId);
                    break;
                case "/start":
                case "Старт":
                case "/photo":
                case "Фото":
                    String photo = Utils.getUrl(URL);
                    sendMessage(photo, chatId);
                    break;
                case "/date":
                    photo = Utils.getUrl(URL + "&date=" + separatedAnswer[1]);
                    sendMessage(photo, chatId);
                    break;
                default:
                    sendMessage("Я не знаю такой команды.", chatId);
                    break;
            }
        }
    }

    void sendMessage(String text, long chatId) {
        SendMessage msg = new SendMessage();

        msg.setText(text);
        msg.setChatId(chatId);

        //Клавиатура
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(); //разметка для клавиатуры
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        List<KeyboardRow> keyboard = new ArrayList<>(); //список рядов нашей клавиатуры
        KeyboardRow keyboardFirstRow = new KeyboardRow(); //создаем первый ряд
        keyboardFirstRow.add("Помощь"); //добавляем кнопку с описанием
        keyboard.add(keyboardFirstRow); //добавляем первый ряд в список рядов
        KeyboardRow keyboardSecondRow = new KeyboardRow(); //создаем второй ряд
        keyboardSecondRow.add("Старт"); //добавляем кнопку во втором ряду с описанием
        keyboard.add(keyboardSecondRow); //добавляем второй ряд в список рядов
        KeyboardRow keyboardThirdRow = new KeyboardRow(); //создаем третий ряд
        keyboardThirdRow.add("Фото"); //добавляем кнопку в третьем ряду с описанием
        keyboard.add(keyboardThirdRow);//добавляем третий ряд в список рядов
        replyKeyboardMarkup.setKeyboard(keyboard); //добавляем все ряды
        msg.setReplyMarkup(replyKeyboardMarkup); //привязываем клавиатуру к сообщению

        try {
            execute(msg);
        } catch (TelegramApiException ex) {
            System.out.println("Бот не отправил сообщение.");
        }
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

}
