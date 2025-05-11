package pages;



import base.Base;

import static org.openqa.selenium.By.xpath;

public class HomePage extends Base {


    public HomePage clickAppLauncher() {
        click(xpath("//button[div[span[text()='App Launcher']]]"));
        return this;

    }

    public CreateLeadPage createNewLead() {
        sendKeys(xpath("//input[@id=//label[contains(normalize-space(.),'Search apps')]/@for]"), "Leads");
        //click Leads suggestion link
        click(xpath("//a[@id='Lead']"));
        //click New
        click(xpath("//button[@name='New']"));
        return new CreateLeadPage();
    }
}
