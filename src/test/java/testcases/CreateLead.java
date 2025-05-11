package testcases;

import base.Base;
import org.testng.annotations.Test;
import pages.CreateLeadPage;
import pages.HomePage;

public class CreateLead extends Base {
    @Test
    public void testCreateLead()  {

        HomePage homePage = new HomePage();
        CreateLeadPage lead = homePage.clickAppLauncher().createNewLead();

        lead.chooseSalutation().enterFirstname()
                .enterlastname().enterCompany().enterPhone().enterEmail()
                .clickSave();

    }



}
