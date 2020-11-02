package ru.netology.selenium;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byAttribute;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class OrderCard {
    private String startUrl = "http://localhost:9999";
    private String validName = "Иван Тестеров";
    private String validPhone = "+79999999999";

    private String notValidPhone = "879999999999";
    private String notValidName = "Ivan";

    private String successOrderText  = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
    private String nameInputAttention = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
    private String phoneInputAttention = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
    private String dataUsageAttention = "Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй";

    @Test
    void testValidOrder() {
        open(startUrl);
        SelenideElement form = $("[id=root]");
        form.$(byAttribute("name", "name")).setValue(validName);
        form.$(byAttribute("name", "phone")).setValue(validPhone);
        form.$("[data-test-id='agreement']").click();
        form.$("button").click();

        form.$("[data-test-id='order-success']").shouldBe(exactText(successOrderText));
    }

    @Test
    void testOrderWithInvalidName() {
        open(startUrl);
        SelenideElement form = $("[id=root]");
        form.$(byAttribute("name", "name")).setValue(notValidName);
        form.$(byAttribute("name", "phone")).setValue(notValidPhone);
        form.$("[data-test-id='agreement']").click();
        form.$("button").click();

        $(byText(nameInputAttention)).shouldBe(visible);
    }

    @Test
    void testOrderWithInvalidPhoneNumber() {
        open(startUrl);
        SelenideElement form = $("[id=root]");
        $(byAttribute("name", "name")).setValue(validName);
        form.$(byAttribute("name", "phone")).setValue(notValidPhone);
        form.$("[data-test-id='agreement']").click();
        form.$("button").click();

        $(byText(phoneInputAttention)).shouldBe(visible);
    }

    @Test
    void testOrderWithUncheckedAttention() {
        open(startUrl);
        SelenideElement form = $("[id=root]");
        form.$(byAttribute("name", "name")).setValue(validName);
        form.$(byAttribute("name", "phone")).setValue(validPhone);
        form.$("button").click();

        $(byText(dataUsageAttention))
                .shouldBe(Condition.cssValue("color", "rgba(255, 92, 92, 1)"));
    }
}
