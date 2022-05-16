package ru.netology.delivery.data.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryTest {

    @Test
    public void validUser(){
        var validUser = DataGenerator.Registration.generateUser("Ru");
        System.out.println(validUser);
    }


    @Test
    void shouldSuccessfulPlanAndReplanMeeting(){

        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        var validUser = DataGenerator.Registration.generateUser("ru");

        $("[data-test-id='city'] input").setValue(validUser.getCity());
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        String planningDate = DataGenerator.generateDate(4);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue(validUser.getName());
        $(byName("phone")).setValue(validUser.getPhone());
        $("[data-test-id='agreement']").click();
        $(byText("Запланировать")).click();
        $(withText("Успешно!")).should(visible, Duration.ofSeconds(15));
        $(".notification__content").shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15));

        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        String updateDate = DataGenerator.generateDate(10);
        $("[data-test-id='date'] input").setValue(updateDate);
        $(byText("Запланировать")).click();

        $("[data-test-id= replan-notification]")
                .shouldHave(Condition.text("Необходимо подтверждение " +
                        "У вас уже запланирована встреча на другую дату. Перепланировать?"), Duration.ofSeconds(15));
        $("[data-test-id=replan-notification] .button")
                .shouldHave(Condition.text("Перепланировать")).click();
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно запланирована на " + updateDate), Duration.ofSeconds(15));
    }
}
//
//    @BeforeEach
//    void setup() {
//        open("http://localhost:9999");
//    }

//    @Test
//    @DisplayName("Should successful plan and replan meeting")
//    void shouldSuccessfulPlanAndReplanMeeting() {
//        var validUser = DataGenerator.Registration.generateUser("ru");
//        var daysToAddForFirstMeeting = 4;
//        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
//        var daysToAddForSecondMeeting = 7;
//        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
//        // TODO: добавить логику теста в рамках которого будет выполнено планирование и перепланирование встречи.
//        // Для заполнения полей формы можно использовать пользователя validUser и строки с датами в переменных
//        // firstMeetingDate и secondMeetingDate. Можно также вызывать методы generateCity(locale),
//        // generateName(locale), generatePhone(locale) для генерации и получения в тесте соответственно города,
//        // имени и номера телефона без создания пользователя в методе generateUser(String locale) в датагенераторе
//    }
//}
//}
