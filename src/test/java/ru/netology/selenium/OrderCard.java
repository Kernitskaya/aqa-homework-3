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
    @Test
    void testValidOrder() {
        open("http://localhost:7777");
        SelenideElement form = $("[id=root]");
        form.$(byAttribute("name", "name")).setValue("Иван Тестеров");
        form.$(byAttribute("name", "phone")).setValue("+79999999999");
        form.$("[data-test-id='agreement']").click();
        form.$("button").click();

        form.$("[data-test-id='order-success']").shouldBe(exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void testOrderWithInvalidName() {
        open("http://localhost:7777");
        SelenideElement form = $("[id=root]");
        form.$(byAttribute("name", "name")).setValue("Ivan");
        form.$(byAttribute("name", "phone")).setValue("879999999999");
        form.$("[data-test-id='agreement']").click();
        form.$("button").click();

        $(byText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.")).shouldBe(visible);
    }

    @Test
    void testOrderWithInvalidPhoneNumber() {
        open("http://localhost:7777");
        SelenideElement form = $("[id=root]");
        form.$(byAttribute("name", "name")).setValue("Иван Тестеров");
        form.$(byAttribute("name", "phone")).setValue("87999999999");
        form.$("[data-test-id='agreement']").click();
        form.$("button").click();

        $(byText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.")).shouldBe(visible);
    }

    @Test
    void testOrderWithUncheckedAttention() {
        open("http://localhost:7777");
        SelenideElement form = $("[id=root]");
        form.$(byAttribute("name", "name")).setValue("Иван Тестеров");
        form.$(byAttribute("name", "phone")).setValue("+79999999999");
        form.$("button").click();

        $(byText("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй"))
                .shouldBe(Condition.cssValue("color", "rgba(255, 92, 92, 1)"));
    }
}
