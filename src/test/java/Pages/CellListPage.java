package Pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static org.junit.jupiter.api.Assertions.fail;

public class CellListPage {
    public static SelenideElement firstNameEntry = $(byText("First Name:")).parent().find(By.className("gwt-TextBox"));
    public static SelenideElement lastNameEntry = $(byText("Last Name:")).parent().find(By.className("gwt-TextBox"));
    public static SelenideElement categorySelector = $(byText("Category:")).parent().find(By.className("gwt-ListBox"));
    public static SelenideElement birthdayEntry = $(byText("Birthday:")).parent().find(By.className("gwt-DateBox"));
    public static SelenideElement addressEntry = $(byText("Address:")).parent().find(By.className("gwt-TextArea"));
    public static SelenideElement createContactBtn = $(byText("Create Contact"));
    public static SelenideElement contactListInfoLabel = $(By.xpath("//td/div[@class='gwt-HTML']"));
    public static SelenideElement contactList = $(By.xpath("//div[@class='GNHGC04CJJ']"));
    public static SelenideElement contact = $(By.xpath("//div[@class='GNHGC04CEB']"));

    @Step("Заполнить поля контакта")
    public static void setContactFields(String firstName, String lastName, String category, String birthday, String address) {
        firstNameEntry.setValue(firstName);
        lastNameEntry.setValue(lastName);
        categorySelector.selectOptionContainingText(category);
        birthdayEntry.setValue(birthday);
        birthdayEntry.click();
        birthdayEntry.pressEnter();
        addressEntry.setValue(address);
    }

    @Step("Количество элементов в списке")
    public static int getContactsCount() {
        String contactListInfoLabelText = contactListInfoLabel.getText();
        return Integer.parseInt(contactListInfoLabelText.substring(contactListInfoLabelText.indexOf(":") + 1).replace(" ", ""));
    }

    @Step("Скролл к контакту")
    public static SelenideElement scrollToContact(String firstName, String lastName, String address) {
            String fullName = firstName + " " + lastName;

            for (int i = 0; i <= 300; i++) {
                contactList.sendKeys(Keys.ARROW_DOWN);

                if ($(byText(fullName)).isDisplayed()) {
                    SelenideElement currentContract = $(By.xpath("//tbody/tr/td[contains(text(),'" + fullName + "')]")); // check fullName
                    currentContract.find(By.xpath("//td[contains(text(),'" + address + "')]")); // check address

                    return currentContract;
                }
            }

            fail();
            return null;
    }
}
