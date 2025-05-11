package pages;

import base.Base;
import org.apache.commons.lang3.RandomStringUtils;


import static org.openqa.selenium.By.xpath;

public class CreateLeadPage extends Base {

    public CreateLeadPage chooseSalutation() {
        //click Salutation
        click(xpath("//button[@aria-label='Salutation']"));
        click(xpath("//*[@data-value='Mr.']"));
        return this;
    }

    public CreateLeadPage enterFirstname() {
        sendKeys(xpath("//label[contains(., 'First Name')]/following::input[1]"), RandomStringUtils.randomAlphabetic(10));

        return this;
    }

    public CreateLeadPage enterlastname() {
        sendKeys(xpath("//label[contains(., 'Last Name')]/following::input[1]"), "K");

        return this;
    }

    public CreateLeadPage enterCompany() {
        sendKeys(xpath("//label[contains(., 'Company')]/following::input[1]"), "Microsoft");

        return this;
    }

    public CreateLeadPage enterEmail() {
        sendKeys(xpath("//label[contains(., 'Email')]/following::input[1]"), RandomStringUtils.randomAlphanumeric(6) + "@gmail.com");

        return this;
    }

    public CreateLeadPage enterPhone() {
        sendKeys(xpath("//input[preceding::label[1][contains(normalize-space(.), 'Phone')]]"), RandomStringUtils.randomNumeric(10));

        return this;
    }

    public CreateLeadPage clickSave() {
        click(xpath("//button[@name='SaveEdit']"));
        return this;
    }
}
