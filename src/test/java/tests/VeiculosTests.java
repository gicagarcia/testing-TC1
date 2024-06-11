package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.HomePage;
import pages.VeiculosPage;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

public class VeiculosTests extends TesteBase {

    @Test
    @DisplayName("Should add a new vehicle")
    void shouldAddNewVehicle() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get("https://site-vercel-tc1.vercel.app/index.html");

        WebElement headerIframe = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//iframe[@src='header.html']")));
        driver.switchTo().frame(headerIframe);

        WebElement vehicleLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='veiculos/veiculos.html']")));
        vehicleLink.click();

        driver.switchTo().defaultContent();

        VeiculosPage veiculosPage = new VeiculosPage(driver);
        veiculosPage.getLicensePlateField().sendKeys(faker.letterify("??????"));
        veiculosPage.getCityField().sendKeys(faker.address().city());
        veiculosPage.getStateField().sendKeys(faker.address().state());

        Select selectType = new Select(veiculosPage.getTypeDropdown());
        selectType.selectByVisibleText("Urbano");

        veiculosPage.getBrandField().sendKeys(faker.company().name());
        veiculosPage.getModelField().sendKeys(faker.aviation().aircraft());
        veiculosPage.getYearField().sendKeys(faker.number().digits(4));

        Select selectFuel = new Select(veiculosPage.getFuelDropdown());
        selectFuel.selectByVisibleText("Gasolina");

        veiculosPage.getColorField().sendKeys(faker.color().name());
        veiculosPage.getMaxSpeedField().sendKeys(faker.number().digits(3));

        veiculosPage.getInsertButton().click();

        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        String alertText = alert.getText();
        String expectedText = "Cadastrado com sucesso!";
        assertThat(alertText).isEqualTo(expectedText);
        alert.accept();
    }

}
