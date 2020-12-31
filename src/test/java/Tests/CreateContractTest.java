package Tests;

import Pages.CellListPage;
import TestFixtures.BaseTestFixture;
import com.codeborne.selenide.Condition;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Feature("Форма создания контакта")
public class CreateContractTest extends BaseTestFixture {
    @ParameterizedTest
    @CsvSource(value = {
            "Alex:Heal:Family:February 5, 1997:12 Golf street",
            "Fist:Last:Friends:March 11, 1966:18 Now street",
            "Merry:Christmas:Coworkers:May 1, 1980:00018 Atlanta street",
            "Ant:Int:Businesses:November 13, 1992:11/22 NY street",
            "Samuel:Jackson:Contacts:December 6, 1979:182 Bass street",
            },
            delimiter = ':')
    @Description("Создание контакта")
    public void createContractTest(String firstName, String lastName, String category, String birthday, String address) {
        int defaultContactListCount = CellListPage.getContactsCount();
        String defaultContactListText = CellListPage.contactListInfoLabel.getText();

        CellListPage.setContactFields(firstName, lastName, category, birthday, address);
        CellListPage.createContactBtn.click();

        CellListPage.contactListInfoLabel.shouldNotHave(Condition.text(defaultContactListText));
        assertEquals(defaultContactListCount + 1, CellListPage.getContactsCount());

        CellListPage.contact.click();
        CellListPage.scrollToContact(firstName, lastName, address).click();

        assertEquals(firstName, CellListPage.firstNameEntry.getAttribute("value"));
        assertEquals(lastName, CellListPage.lastNameEntry.getAttribute("value"));
        assertEquals(category, CellListPage.categorySelector.getAttribute("value"));
        assertEquals(birthday, CellListPage.birthdayEntry.getAttribute("value"));
        assertEquals(address, CellListPage.addressEntry.getAttribute("value"));
    }
}
